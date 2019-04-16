'use strict';

angular.module('changePassword')
    .component('changePassword',{
    templateUrl: 'components/changePassword/changePassword.template.html',
        controller: function ChangePasswordController($scope, $http, $timeout, $injector, localStorageService) {

            /*旧密码*/
            $scope.oldPwd = null;

            /*新密码*/
            $scope.newPwd = null;

            /*重复输入的新密码*/
            $scope.newPwdTwice = null;

            /*修改密码*/
            $scope.changePassword = function() {

                if($scope.oldPwd == null || $scope.newPwd == null || $scope.newPwdTwice == null || $scope.newPwd != $scope.newPwdTwice) {
                    return;
                }

                var user = {
                    "username": localStorageService.get('user').username,
                    "oldPwd": $scope.oldPwd,
                    "newPwd": $scope.newPwd
                };

                $http.post("/auth/v1/users/password", user).then(function (success) {
                    //修改成功
                    $(".save-success").removeClass("in").show();
                    $(".save-success").delay(200).addClass("in").fadeOut(2000);
                    /*$timeout(function () {
                        $injector.get('$state').go('papers');
                    }, 2000);*/
                }, function (error) {
                    //修改失败
                    $(".save-fail").removeClass("in").show();
                    $(".save-fail").delay(200).addClass("in").fadeOut(2000);
                });
            };
    }
});