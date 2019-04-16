'use strict'

angular.module('teachers')
    .component('teachers', {
        templateUrl: 'components/teachers/teachers.template.html',
        controller: function teachersController($modal, $scope, $http, $window, TeacherService, $injector, localStorageService, $timeout, $state, ErrorService) {

            //加载中标志
            $scope.loading = true;
            $scope.url = '/auth/api/users/';

            //教师列表
            $scope.teachers = null;

            //具体的某个学生
            $scope.teacher = null;

            //保存需要查询的学生的id
            $scope.id = null;

            //保存搜索框内容，用于模糊搜索
            $scope.teacherName = "";

            /**
             * 刷新页面
             */
            var reloadState = function() {
                $state.reload();
            };

            //查看教师信息
            var infoModal = $modal({
                scope: $scope,
                templateUrl: 'components/teachers/teacherInfo.template.html',
                show: false
            });


            //编辑教师信息
            var editModal = $modal({
                scope: $scope,
                templateUrl: 'components/teachers/editTeacher.template.html',
                show: false
            });

            //新增教师
            var addModal = $modal({
                scope: $scope,
                templateUrl: 'components/teachers/addTeacher.template.html',
                show: false
            });

            /*===============================================*/
            $scope.showInfoModal = function(id) {
                //根据id查询教师信息
                    $http.get("/auth/v1/teachers/" + id).then(function(response) {
                        //赋值
                        $scope.teacher = response.data;
                        //显示查询信息
                        infoModal.$promise.then(infoModal.show);
                    }, function(error) {
                        ErrorService.error(error);
                    });
            };

            $scope.showEditModal = function(id) {
                    $http.get("/auth/v1/teachers/" + id).then(function(response) {
                        //赋值
                        $scope.teacher = response.data;
                        //显示查询信息
                        editModal.$promise.then(editModal.show);
                    });
            };


            $scope.showAddModal = function () {
                    $scope.teacher = {};
                    addModal.$promise.then(addModal.show);
            };


            //获取教师列表
            var getTeachers = function() {
                $scope.loading = true;
                $http({
                    method: 'GET',
                    url: $scope.url
                }).success(function(data) {
                    $scope.teachers = [];
                    $scope.teachers = data;
                    $scope.loading = false;
                }).error(function(error) {
                    ErrorService.error(error);
                });
            };

            /*保存*/
            $scope.saveTeacher = function () {
                if($scope.teacher.teacherName == undefined || $scope.teacher.teacherName == "")
                    return;
                $http.post('/auth/v1/teachers', $scope.teacher).then(function (success) {
                    $('.alert-success').removeClass('in').show();
                    $('.alert-success').delay(200).addClass('in').fadeOut(2000);
                    //刷新页面
                    $timeout(function () {
                        addModal.$promise.then(addModal.hide);
                        reloadState();
                    }, 2000);
                }, function (error) {
                    $('.alert-warning').removeClass('in').show();
                    $('.alert-warning').delay(200).addClass('in').fadeOut(2000);
                    //刷新页面
                    $timeout(function () {
                        addModal.$promise.then(addModal.hide);
                        reloadState();
                    }, 2000);
                });
            };

            /*删除*/
            $scope.deleteTeacher = function(id) {

                /*测试*/
                if (confirm('是否删除?')) {
                    /*http delete 后台*/
                    $http.delete("/auth/v1/teachers/" + id).then(function(response) {
                        console.log(response);
                    });
                    reloadState();
                }
            }

            /*更新*/
            $scope.updateTeacher = function() {
                /*先post后台，在get后台*/
                $http.put("/auth/v1/teachers/" + $scope.teacher.id, $scope.teacher)
                    .then(function(response) {
                        console.log(response);
                    }, function(error) {
                        ErrorService.error(error);
                    });
               reloadState();
            }

            /* 以分页方式获取学生列表 */
            $scope.callServer = function callServer(tableState) {
                $scope.isLoading = true;

                var pagination = tableState.pagination;

                var start = pagination.start || 0;
                var number = pagination.number || 10;

                TeacherService.getPage(start, number, tableState).then(function(result) {
                    if (result.data == null || result.data.length == 0) {
                        $scope.teachers = null;
                    } else {
                        $scope.teachers = result.data;
                    }
                    tableState.pagination.numberOfPages = result.numberOfPages;
                    $scope.isLoading = false;
                });
            }

            /* 搜索功能 */
            $scope.search = function() {
                if ($scope.teacherName != "") {
                    $http.get("/auth/v1/teachers/" + $scope.teacherName + "/name").then(function(reponse) {
                        $scope.teachers = reponse.data;
                    });
                }else {
                    reloadState();
                }
            }
        }
    });

angular.module('teachers').factory('TeacherService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

    var teachers = []; //教师数组,用于保存查询结果
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(teachers, params.search.predicateObject) : teachers;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/auth/v1/teachers?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

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
