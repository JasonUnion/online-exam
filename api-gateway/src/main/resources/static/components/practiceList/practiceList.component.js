'use strict';

angular.module('practiceList')
    .component('practiceList', {
        templateUrl: 'components/practiceList/practiceList.template.html',
        controller: function PracticeController($scope, $http, $injector, ErrorService, isLoginService) {

            $scope.practices = [];

            /**
             * 获取练习
             */
            var getPracticeList = function () {
                $http.get('/exam/v1/papers/type/practice' ).then(function (success) {
                    if(success.data) {
                        $scope.practices = success.data.list;
                    }
                }, function (error) {
                    $scope.practices = {};
                    ErrorService.error(error);
                });
            };

            /*参加练习*/
            $scope.joinPractice = function (practice) {
                $injector.get('$state').go('practice', {
                    practice: practice
                });
            };

            //检测是否登录
            if(!isLoginService.isLogin()) {
                return;
            }
            getPracticeList();
        }
    });