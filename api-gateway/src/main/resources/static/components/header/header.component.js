'use strict';

angular.
module('header').
component('header', {
    templateUrl: 'components/header/header.template.html',
    controller: function HeaderController($scope, $http, $injector, localStorageService, $rootScope, ErrorService) {

        $scope.ueser = localStorageService.get("user");

        /*路由*/
        $scope.goHome = function() {
            $injector.get('$state').transitionTo('home');
        };

        /*退出*/
        $scope.logout = function () {
            localStorageService.remove("user");
            localStorageService.remove("authenticated");
            $scope.ueser = null;
            $scope.authenticated = false;

            $http.post('/logout').then(function (success) {
                
            }, function (error) {
                ErrorService.error(error);
            });
        };




        var authorization = function () {
            $http.get('/auth/v1/users/me').then(function (success) {
                //认证成功
                if(success.data.username) {
                    $scope.user = success.data;
                    localStorageService.set("user", $scope.user);
                    localStorageService.set("authenticated", true);
                    $scope.authenticated = true;
                    $rootScope.authenticated = true;
                }
            }, function (error) {
                $rootScope.authenticated = false;
                localStorageService.remove("user");
                $scope.authenticated = false;
            });
        };

        /*请求认证*/
        authorization();

    }
});
