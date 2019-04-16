'use strict';

angular.
module('students').
component('students', {
    templateUrl: 'components/students/students.template.html',
    controller: function StudentsController($modal, $scope, $http, StudentService, $injector, localStorageService, $timeout, $state, ErrorService) {

        //加载中标志
        $scope.loading = true;
        $scope.url = '/auth/api/users/';

        //学生列表
        $scope.students = null;

        //具体的某个学生
        $scope.student = null;

        //保存需要查询的学生的id
        $scope.id = null;

        //保存搜索框内容，用于模糊搜索
        $scope.username = "";

        /**
         * 刷新页面
         */
        var reloadState = function() {
            $state.reload();
        };

        //查看学生信息
        var infoModal = $modal({
            scope: $scope,
            templateUrl: 'components/students/studentInfo.template.html',
            show: false
        });

        //编辑学生信息
        var editModal = $modal({
            scope: $scope,
            templateUrl: 'components/students/editStudent.template.html',
            show: false
        });

        /*===============================================*/
        $scope.showInfoModal = function(id) {
          //根据id查询学生信息
                $http.get("/auth/v1/students/" + id + "/grade").then(function(response) {
                    //赋值
                    $scope.student = response.data;
                    //显示查询信息
                    infoModal.$promise.then(infoModal.show);
                }, function(error) {
                    ErrorService.error(error);
                });
        };

        $scope.showEditModal = function(id) {
                $http.get("/auth/v1/students/" + id).then(function(response) {
                  //赋值
                  if(response.data.examNumber) {
                      response.data.examNumber = Number(response.data.examNumber);
                  }
                  $scope.student = response.data;
                  //显示查询信息
                  editModal.$promise.then(editModal.show);
                }, function (error) {
                    ErrorService.error(error);
                });
        };


        $scope.deleteStudent = function(id) {

            /*测试*/
            if (confirm('是否删除?')) {
                /*http delete 后台*/
                $http.delete("/auth/v1/students/" + id).then(function(response) {
                    reloadState();
                }, function (error) {
                    ErrorService.error(error);
                });
            }
        };

        /*更新*/
        $scope.updateStudent = function() {
            /*先post后台，在get后台*/
            $http.put("/auth/v1/students/" + $scope.student.id, $scope.student)
                .then(function(response) {
                    //console.log(response);
                    $('.alert-success').removeClass('in').show();
                    $('.alert-success').delay(200).addClass('in').fadeOut(2000);
                    //刷新页面
                    $timeout(function () {
                        editModal.$promise.then(editModal.hide);
                        reloadState();
                    }, 2000);
                }, function(error) {
                    $('.alert-warning').removeClass('in').show();
                    $('.alert-warning').delay(200).addClass('in').fadeOut(2000);
                    //刷新页面
                    $timeout(function () {
                        editModal.$promise.then(editModal.hide);
                        reloadState();
                    }, 2000);
                });
        };

        /* 以分页方式获取学生列表 */
        $scope.callServer = function callServer(tableState) {
            $scope.isLoading = true;

            var pagination = tableState.pagination;

            var start = pagination.start || 0;
            var number = pagination.number || 10;

            StudentService.getPage(start, number, tableState).then(function(result) {
                if (result.data == null || result.data.length == 0) {
                    $scope.students = null;
                } else {
                    $scope.students = result.data;
                }
                tableState.pagination.numberOfPages = result.numberOfPages;
                $scope.isLoading = false;
            });
        }

        /* 搜索功能 */
        $scope.search = function() {
            if ($scope.studentName != "") {
                $http.get("/auth/v1/students/" + $scope.studentName + "/teacher/" + localStorageService.get("user").username).then(function(reponse) {
                    $scope.students = reponse.data;
                });
            }else {
                reloadState();
            }
        };

        /**
         * 菜单工具栏调整
         */
        $scope.add = function () {
            $injector.get('$state').go('addStudent');
        };
    }
});


angular.module('students').factory('StudentService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', 'localStorageService', function($q, $filter, $timeout, $http, ErrorService, localStorageService) {

    var students = []; //学生数组,用于保存查询结果
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(students, params.search.predicateObject) : students;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/auth/v1/students/teacher/' + localStorageService.get("user").username + '?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

            result = response.data.list;

            pages = response.data.pages;

            $timeout(function() {
                deferred.resolve({
                    data: result,
                    numberOfPages: Math.ceil(pages)
                });
            }, 500);
        }, function(error) {
            ErrorService.error(error);
        });
        return deferred.promise;
    }
    return {
        getPage: getPage
    };
}]);
