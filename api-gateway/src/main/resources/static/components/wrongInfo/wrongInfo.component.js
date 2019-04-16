'use strict';

angular.module('wrongInfo')
    .component('wrongInfo', {
        templateUrl: 'components/wrongInfo/wrongInfo.template.html',
        controller: function WrongInfoController($scope, $stateParams, $http, ErrorService) {
            $scope.wrong = $stateParams.wrong;
        }
    });