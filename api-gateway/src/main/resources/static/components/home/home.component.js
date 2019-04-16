'use strict';

angular.module('home')
	.component('home', {
		templateUrl: 'components/home/home.template.html',
		controller: function HomeController($scope, $http, $state, ErrorService) {


			/**
			 * 刷新页面
			 */
			var reloadState = function() {
				$state.reload();
			};


			/**
			 * 发送消息
			 */
			$scope.send = function () {
				if($scope.name == null || $scope.email == null) {
					return;
				}

				var contact = {
					username: $scope.name,
					email: $scope.email,
					remark: $scope.remark
				}

				$http.post('/auth/v1/contacts', contact).then(function (success) {
					console.log('Thank you!');
					reloadState();
				}, function (error) {
					ErrorService.error(error);
				});
			}
		}
	})