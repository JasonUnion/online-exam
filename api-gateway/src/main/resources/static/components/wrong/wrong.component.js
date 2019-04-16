'use strict';

angular.module('wrong')
    .component('wrong', {
        templateUrl: 'components/wrong/wrong.template.html',
        controller: function WrongController($scope, $stateParams) {

            if($stateParams.results.length == 0) {
                $scope.results = null;
            }else {
                $scope.results = $stateParams.results;
            }
        }
    });