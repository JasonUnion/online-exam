'use strict';
/*
* 课程管理模块
* 列表
* 课程增删改查
* 为课程添加考试
* */

angular.
module('subjects').
component('subjects', {
  templateUrl: 'components/subjects/subjects.template.html',
  controller: function SubjectsController($modal, $scope, $http, $window, SubjectService, $injector, localStorageService, $timeout, $state, ErrorService) {
    /*加载中标志*/
    $scope.loading = true;

    /*模块请求后端的url*/
    $scope.url = '/exam/v1/subjects/';

    /*课程列表对象*/
    $scope.subjects = null;

    /*保存搜索框内容*/
    $scope.subjectName = "";

    /*错误信息*/
    $scope.error = "";

    /**
     * 刷新页面
     */
    var reloadState = function() {
      $state.reload();
    };

    /*$scope.fetchSubjectByIdOrName = function() {
      //alert($scope.subjectIdOrName);
      $http({
        method: 'GET',
        url: '/exam/api/subj/search/' + $scope.subjectIdOrName,
      }).success(function(data) {
        if (data.length == 0) {
          $scope.subjects = {};
        } else {
          $scope.subjects = data;
        }
      }).error(function(data, status, headers, config) {});
    };*/

    //查看课程信息
    var infoModal = $modal({
      scope: $scope,
      templateUrl: 'components/subjects/subjectInfo.template.html',
      show: false
    });


    //编辑课程信息
    var editModal = $modal({
      scope: $scope,
      templateUrl: 'components/subjects/editSubject.template.html',
      show: false
    });

    //新增课程
    var addModal = $modal({
      scope: $scope,
      templateUrl: 'components/subjects/addSubject.template.html',
      show: false
    });

    /*===============================================*/
    $scope.showInfoModal = function(id) {
      //根据id查询课程信息
      $http.get("/exam/v1/subjects/" + id).then(function(response) {
        //赋值
        $scope.subject = response.data;
        //显示查询信息
        infoModal.$promise.then(infoModal.show);
      }, function(error) {
        alert("error");
      });
    };

    /*显示编辑*/
    $scope.showEditModal = function(id) {
      $http.get("/exam/v1/subjects/" + id).then(function(response) {
        //赋值
        $scope.subject = response.data;
        //显示查询信息
        editModal.$promise.then(editModal.show);
      });
    };


    $scope.showAddModal = function () {
      $scope.subject = {};
      addModal.$promise.then(addModal.show);
    };

    /*保存*/
    $scope.saveSubject = function () {
      if($scope.subject.name == undefined || $scope.subject.name == "")
        return;
      $http.post('/exam/v1/subjects', $scope.subject).then(function (success) {
        $('.alert-success').removeClass('in').show();
        $('.alert-success').delay(200).addClass('in').fadeOut(2000);
        //刷新页面
        $timeout(function () {
          addModal.$promise.then(addModal.hide);
          reloadState();
        }, 2000);
      }, function (error) {
        $scope.error = error.data.message;
        $('.alert-warning').removeClass('in').show();
        $('.alert-warning').delay(200).addClass('in').fadeOut(2000);
      });
    };

    /*删除*/
    $scope.deleteSubject = function(id) {

      /*测试*/
      if (confirm('是否删除?')) {
        /*http delete 后台*/
        $http.delete("/exam/v1/subjects/" + id).then(function(success) {
          //刷新页面
          $timeout(function () {
            editModal.$promise.then(editModal.hide);
            reloadState();
          }, 500);
        }, function (error) {
          if(error.data.message) {
            alert(error.data.message);
          }
        });
      }
    }

    /*更新*/
    $scope.updateSubject = function() {
      /*先post后台，在get后台*/
      $http.put("/exam/v1/subjects", $scope.subject)
          .then(function(success) {
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
    }

    /* 以分页方式获取课程列表 */
    $scope.callServer = function callServer(tableState) {
      $scope.isLoading = true;

      var pagination = tableState.pagination;

      var start = pagination.start || 0;
      var number = pagination.number || 10;

      SubjectService.getPage(start, number, tableState).then(function(result) {
        if (result.data == null || result.data.length == 0) {
          $scope.subjects = null;
        } else {
          $scope.subjects = result.data;
        }
        tableState.pagination.numberOfPages = result.numberOfPages;
        $scope.isLoading = false;
      });
    }

    /* 搜索功能 */
    $scope.search = function() {
      if ($scope.studentName != "") {
        $http.get("/exam/v1/subjects/" + $scope.subjectName + "/name").then(function(reponse) {
          $scope.subjects = reponse.data;
        }, function (error) {
          ErrorService.error(error);
        });
      }else {
        reloadState();
      }
    }
  }
});

angular.module('subjects').factory('SubjectService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

  var subjects = []; //班级数组,用于保存查询结果
  function getPage(start, number, params) {
    var deferred = $q.defer();
    var filtered = params.search.predicateObject ? $filter('filter')(subjects, params.search.predicateObject) : subjects;
    if (params.sort.predicate) {
      filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
    }

    var result = null;
    var pages = null;

    $http.get('/exam/v1/subjects?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

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