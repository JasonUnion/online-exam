'use strict';

angular.
module('viewUserInfo').
component('viewUserInfo', {
  templateUrl: 'components/viewUserInfo/viewUserInfo.template.html',
  controller: function ViewUserInfoController($scope, $http, record) {
    function init() {
      $scope.user = record;
    }
    init();
  }
});
