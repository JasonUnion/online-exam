'use strict';

angular.
module('gradeList').
component('gradeList', {
  templateUrl: 'components/gradeList/gradeList.template.html',
  controller: function HeadersController($modal, $scope, $http, $window, GradeService, $injector, localStorageService, $timeout, $state, ErrorService) {
	  //加载中标志
	  $scope.loading = true;
	  $scope.url = '/auth/v1/grades';

	  //班级列表
	  $scope.grades = null;

	  //具体的某个班级
	  $scope.grade = null;

	  //保存需要查询的班级的id
	  $scope.id = null;

	  //保存搜索框内容，用于模糊搜索
	  $scope.gradeName = "";

	  /*错误信息*/
	  $scope.error = "";

	  /**
	   * 刷新页面
	   */
	  var reloadState = function() {
		  $state.reload();
	  };

	  //查看班级信息
	  var infoModal = $modal({
		  scope: $scope,
		  templateUrl: 'components/gradeList/gradeInfo.template.html',
		  show: false
	  });


	  //编辑班级信息
	  var editModal = $modal({
		  scope: $scope,
		  templateUrl: 'components/gradeList/editGrade.template.html',
		  show: false
	  });

	  //新增班级
	  var addModal = $modal({
		  scope: $scope,
		  templateUrl: 'components/gradeList/addGrade.template.html',
		  show: false
	  });

	  /*===============================================*/
	  $scope.showInfoModal = function(id) {
		  //根据id查询班级信息
		  $http.get("/auth/v1/grades/" + id).then(function(response) {
			  //赋值
			  $scope.grade = response.data;
			  //显示查询信息
			  infoModal.$promise.then(infoModal.show);
		  }, function(error) {
			  alert("error");
		  });
	  };

	  /*显示编辑*/
	  $scope.showEditModal = function(id) {
		  $http.get("/auth/v1/grades/" + id).then(function(response) {
			  //赋值
			  $scope.grade = response.data;
			  //显示查询信息
			  editModal.$promise.then(editModal.show);
		  }, function (error) {
			  ErrorService.error(error);
		  });
	  };


	  $scope.showAddModal = function () {
		  $scope.grade = {};
		  addModal.$promise.then(addModal.show);
	  };

	  /*保存*/
	  $scope.saveGrade = function () {
		  if($scope.grade.gradeName == undefined || $scope.grade.gradeName == "")
			  return;
		  $http.post('/auth/v1/grades', $scope.grade).then(function (success) {
			  $('.alert-success').removeClass('in').show();
			  $('.alert-success').delay(200).addClass('in').fadeOut(2000);
			  //刷新页面
			  $timeout(function () {
				  addModal.$promise.then(addModal.hide);
				  reloadState();
			  }, 2000);
		  }, function (error) {

			  $scope.error = error.data.message;
			  $('.alert-warning').removeClass('in').show();
			  $('.alert-warning').delay(200).addClass('in').fadeOut(2000);
		  });
	  };

	  /*删除*/
	  $scope.deleteGrade = function(id) {

		  /*测试*/
		  if (confirm('是否删除?')) {
			  /*http delete 后台*/
			  $http.delete("/auth/v1/grades/" + id).then(function(success) {
				  //刷新页面
				  $timeout(function () {
					  editModal.$promise.then(editModal.hide);
					  reloadState();
				  }, 500);
			  }, function (error) {
				  $scope.error = error.data.message;
				  alert($scope.error);
				  //刷新页面
				  $timeout(function () {
					  editModal.$promise.then(editModal.hide);
					  reloadState();
				  }, 500);
			  });
		  }
	  }

	  /*更新*/
	  $scope.updateGrade = function() {
		  /*先post后台，在get后台*/
		  $http.put("/auth/v1/grades/" + $scope.grade.id, $scope.grade)
			  .then(function(success) {
				  $('.alert-success').removeClass('in').show();
				  $('.alert-success').delay(200).addClass('in').fadeOut(2000);
				  //刷新页面
				  $timeout(function () {
					  editModal.$promise.then(editModal.hide);
					  reloadState();
				  }, 2000);
			  }, function(error) {
				  $scope.error = error.data.message;
				  $('.alert-warning').removeClass('in').show();
				  $('.alert-warning').delay(200).addClass('in').fadeOut(2000);
			  });
	  }

	  /* 以分页方式获取班级列表 */
	  $scope.callServer = function callServer(tableState) {
		  $scope.isLoading = true;

		  var pagination = tableState.pagination;

		  var start = pagination.start || 0;
		  var number = pagination.number || 10;

		  GradeService.getPage(start, number, tableState).then(function(result) {
			  if (result.data == null || result.data.length == 0) {
				  $scope.grades = null;
			  } else {
				  $scope.grades = result.data;
			  }
			  tableState.pagination.numberOfPages = result.numberOfPages;
			  $scope.isLoading = false;
		  });
	  }

	  /* 搜索功能 */
	  $scope.search = function() {
		  if ($scope.gradeName != "") {
			  $http.get("/auth/v1/grades/" + $scope.gradeName + "/name").then(function(reponse) {
				  $scope.grades = reponse.data;
			  }, function (error) {
				  ErrorService.error(error);
			  });
		  }else {
		  	reloadState();
		  }
	  }

	  /*添加班级名称*/
	  $scope.addClassName = function () {
		  var newClassName = {'className':''};
		  $scope.grade.classNames.push(newClassName);
	  }
  }
});


angular.module('gradeList').factory('GradeService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

	var grades = []; //班级数组,用于保存查询结果
	function getPage(start, number, params) {
		var deferred = $q.defer();
		var filtered = params.search.predicateObject ? $filter('filter')(grades, params.search.predicateObject) : grades;
		if (params.sort.predicate) {
			filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
		}

		var result = null;
		var pages = null;

		$http.get('/auth/v1/grades?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

			result = response.data.list;

			pages = response.data.pages;

			$timeout(function() {

				deferred.resolve({
					data: result,
					numberOfPages: Math.ceil(pages)
				});
			}, 500);

		}, function(error) {
			ErrorService.error(error);
		});
		return deferred.promise;
	}
	return {
		getPage: getPage
	};
}]);