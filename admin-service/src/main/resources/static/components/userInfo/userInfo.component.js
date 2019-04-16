'use strict';

angular.
module('userInfo').
component('userInfo', {
  templateUrl: 'components/userInfo/userInfo.template.html',
  controller: function UserInfoController($scope, $http, $log, $routeParams, $modal) {
    $scope.loading = false;
    $scope.url = '/uaa/user/' + $routeParams.userId;
    $scope.user = {};
    var modalInstance = null;
    $scope.$on('userInfoEvents', function(event, msg) {
      fetchUser();
    });

    var fetchUser = function() {
      $scope.loading = true;
      $http({
        method: 'GET',
        url: $scope.url
      }).success(function(data) {
        $scope.user = {};
        $scope.user = data;
        $scope.loading = false;
      }).error(function(data, status, headers, config) {});
    };

    $scope.viewInfo = function(id) {
      if (id > 0) {
        $http.get("/uaa/user" + id).then(function(data) {
          modalInstance = $modal.open({
            animation: false,
            templateUrl: 'components/ViewUserInfo/ViewUserInfo.template.html',
            controller: 'ViewUserInfoController',
            resolve: {
              record: function() {
                return data;
              }
            }
          });
        });
      }
    };

    fetchUser();
  }
});