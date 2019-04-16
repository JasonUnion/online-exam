'use strict';

angular.
module('headerAndSidebar').
component('headerAndSidebar', {
  templateUrl: 'components/headerAndSidebar/headerAndSidebar.template.html',
  controller: function HeaderAndSidebarController($scope, $http, $rootScope, localStorageService, $injector, ErrorService) {

    $scope.authUrl = '/auth/v1/users/me';
    $scope.isAdmin = false;

    $scope.isTeacher = false;

    $scope.unChecked = 0;

    $scope.checked = 0;

    $scope.progress = {
      'width': "20%"
    };

    var date = new Date();
    $scope.date = date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate();

    /**
     * 权限判断
     * @param authorities
     */
    var authorities = function (authorities) {
      for(var i = 0; i < authorities.length; i++) {
        if(authorities[i].authority == 'ROLE:ADMIN') {
          $scope.isAdmin = true;
        }else if(authorities[i].authority == 'ROLE:TEACHER') {
          $scope.isTeacher = true;
        }
      }
    };

    /**
     * 获取用户信息
     */
    var getUser = function() {
      $http.get($scope.authUrl).then(function success(response) {
        var data = response.data;
        if(data.avatar == "" || data.avatar == null) {
          //默认的用户头像
          data.avatar = "http://localhost:8871/static/img/default.jpg";
        }
        $scope.user = data;
        //判断是否是管理员
        authorities(data.authorities);
        localStorageService.set("user",$scope.user);
      }, function error(error) {
        $scope.username = "";
      });
    };
    


    //从localStorage获取user
    var user = localStorageService.get("user");
    if(user == null) {
      getUser();
    }else {
      $scope.user = user;
      authorities(user.authorities);
    }

    /**
     * 登出
     */
    $scope.logout = function() {
      localStorageService.remove("user");
      $scope.authenticated = false;
    };

    /*统计未批改试卷数量和未读消息数量*/
    var countCheck = function () {
      $http.get('/exam/v1/answer-papers/check').then(function (success) {
        $scope.unChecked = success.data.unChecked;
        $scope.checked = success.data.checked;
        $scope.progress.width = Math.round($scope.checked/($scope.checked + $scope.unChecked) * 100) + '%';
      }, function (error) {
        $scope.unCheck = 0;
      });

      $http.get('/auth/v1/contacts/status?status=false').then(function (success) {
        $scope.messages = success.data;
      }, function (error) {
        //ErrorService.error(error);
      });
    };

    /*查看所有答卷*/
    $scope.allAnswerPaper = function () {
      $injector.get('$state').go('answerPapers');
    };

    /**
     * 清理缓存
     */
    $scope.cleanCache = function () {

      $http.post('/auth/v1/caches').then(function (success) {

      }, function (error) {
        ErrorService.error(error);
      });
    };

    countCheck();

  }
});