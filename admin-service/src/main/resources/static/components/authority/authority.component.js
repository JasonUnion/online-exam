'use strict';

angular.
module('authority').
component('authority', {
	templateUrl: 'components/authority/authority.template.html',
	controller: function authorityController($scope, $http, $modal, userService, $timeout, $state, ErrorService) {
		//保存用户列表
		$scope.users = [];

		//保存当前用户
		$scope.userId = null;

		//保存权限列表
		$scope.authorities = null;

		/**
		 * 刷新页面
		 */
		var reloadState = function() {
			$state.reload();
		};

		//分配权限
		var allocateModal = $modal({
			scope: $scope,
			templateUrl: 'components/authority/allocateAuthority.template.html',
			show: false
		});

		//分配权限
		$scope.allocateAuthority = function (userId) {
			//绑定当前用户id
			$scope.userId = userId;

			$http.get("/auth/v1/authorities/tree/" + userId).then(function(reponse) {
				$scope.authorities = reponse.data;
				//显示查询信息
				allocateModal.$promise.then(allocateModal.show);
			});
		};

		//保存
		$scope.save = function () {
			var result = new Array();
			//从authorities中取出selected为true的元素
			for(var i = 0; i < $scope.authorities.length; i++) {
				if($scope.authorities[i].selected) {
					result.push($scope.authorities[i]);
				}
			}
			//http修改权限
			$http.post('/auth/v1/user-authorities/' + $scope.userId, result).then(function (response) {
				//保存成功
				$(".save-success").removeClass("in").show();
				$(".save-success").delay(200).addClass("in").fadeOut(2000);
				//隐藏弹窗
				$timeout(function () {
					allocateModal.$promise.then(allocateModal.hide);
				}, 2000);
			},function (error) {
				//保存失败
				$(".save-fail").removeClass("in").show();
				$(".save-fail").delay(200).addClass("in").fadeOut(2000);
				//隐藏弹窗
				$timeout(function () {
					allocateModal.$promise.then(allocateModal.hide);
				}, 2000);
			});
		}

		//分页查询
		$scope.callServer = function callServer(tableState) {
			$scope.isLoading = true;
			var pagination = tableState.pagination;
			var start = pagination.start || 0;
			var number = pagination.number || 10;
			userService.getPage(start, number, tableState).then(function (result) {
				if(result.data == null || result.data.length == 0) {
					$scope.users = null;
				}else {
					$scope.users = result.data;
				}
				tableState.pagination.numberOfPages = result.numberOfPages;
				$scope.isLoading = false;
			});
		};

		//搜索
		$scope.search = function () {
			if($scope.username != "") {
				$http.get("/auth/v1/users/name?name=" + $scope.username).then(function(reponse) {
					$scope.users = reponse.data;
				});
			}else {
				reloadState();
			}
		};

	}
});

angular.module('authority').factory('userService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function ($q, $filter, $timeout, $http, ErrorService) {
	var users = [];//用户数组,用于保存查询结果
	function getPage(start, number, params) {
		var deferred = $q.defer();
		var filtered = params.search.predicateObject ? $filter('filter')(users, params.search.predicateObject) : users;
		if (params.sort.predicate) {
			filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
		}
		var result = null;
		var pages = null;
		$http.get('/auth/v1/users?pageIndex=' + (start / 10 + 1) + '&pageSize=' + number).then(function(response) {
			result = response.data.list;
			pages = response.data.pages;
			$timeout(function () {
				deferred.resolve({
					data: result,
					numberOfPages: Math.ceil(pages)
				});
			}, 500);

		}, function (error) {
			ErrorService.error(error);
		});
		return deferred.promise;
	}
	return {
		getPage: getPage
	};
}]);
