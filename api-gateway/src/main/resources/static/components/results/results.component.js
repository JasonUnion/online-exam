'use strict';

angular.module('results')
    .component('results', {
        templateUrl: 'components/results/results.template.html',
        controller: function ResultsController($scope, $stateParams, $injector) {
            $scope.result = $stateParams.result;

            $scope.paper = $stateParams.paper;

            $scope.viewWrong = function () {
                $injector.get('$state').go('wrong', {
                    results: $scope.result.results
                });
            };


        }
    });