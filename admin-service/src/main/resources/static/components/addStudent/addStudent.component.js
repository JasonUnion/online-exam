'use strict';

angular.
module('addStudent').
component('addStudent', {
	templateUrl: 'components/addStudent/addStudent.template.html',
	controller: function addStudentController($scope, $http, $modal, localStorageService, ErrorService, $timeout, $injector) {
		/*学生对象*/
		$scope.student = {};

		/*班级树对象*/
		var tree;
		$scope.grade_tree = tree = {};

		/*班级树信息*/
		$scope.tree_data = {};

		/*保存当前选中节点*/
		$scope.selected = {};

		//定义modal
		var gradeModal = $modal({
			scope: $scope,
			templateUrl: 'components/addStudent/addStudentModal.template.html',
			show: false,
			onHide: function() {
				var p = tree.get_selected_branch();//获取当前选中节点，对应是班
				if(tree.get_parent_branch(p) != undefined) {
					var grade = null;
					if (p != null) {
						grade = tree.get_parent_branch(p).label;//获取父节点，对应是年级
					}
					$scope.student.gradeClassName = grade + $scope.selected;
					$scope.student.grade = grade;
					$scope.student.className = $scope.selected;
				}else {
					$scope.student.gradeClassName = $scope.selected;
					$scope.student.grade = p.label;
					$scope.student.className = "";
				}

			}
		});

		/*显示modal*/
		$scope.showGradeModal = function () {
			$http.get("/auth/v1/grades/tree").then(function(reponse) {

				$scope.tree_data = reponse.data;
				//显示modal
				gradeModal.$promise.then(gradeModal.show);
			}, function (error) {
				ErrorService.error(error);
			});
		};

		$scope.tree_handler = function(branch) {
			$scope.selected = branch.label;
		};

		/*保存*/
		$scope.save = function() {
			//保存
			var student = {
				"examNumber": $scope.student.examNumber,
				"studentName": $scope.student.studentName,
				"grade": $scope.student.grade,
				"className": $scope.student.className
			};

			$http.post("/auth/v1/students/" + localStorageService.get("user").id + "/teacher", student).then(function (success) {
				//保存成功
				$(".alert-success").removeClass("in").show();
				$(".alert-success").delay(200).addClass("in").fadeOut(2000);
				$timeout(function () {
				 $injector.get('$state').go('students');
				 }, 2000);
			}, function (error) {
				//保存失败
				$scope.error = error.data.message;
				$(".alert-danger").removeClass("in").show();
				$(".alert-danger").delay(200).addClass("in").fadeOut(2000);
			});
		};

	}
});
/*

angular.module('addStudent').component('modalComponent', {
	templateUrl: 'components/addStudent/addStudentModal.template.html',
	bindings: {
		resolve: '<',
		close: '&',
		dismiss: '&'
	},
	controller: function($scope) {
		var $ctrl = this;
		var tree;
		$scope.grade_tree = tree = {};
		$ctrl.$onInit = function() {
			$ctrl.my_data = this.resolve.grades.data;
			/!*$ctrl.my_data = [{
				label: '一年级',
				children: [{
					label: '1班'
				}, {
					label: '2班'
				}, {
					label: '3班'
				}]
			}, {
				label: '二年级',
				children: [{
					label: '1班'
				}, {
					label: '2班'
				}, {
					label: '3班'
				}]
			}];*!/


		};

		$ctrl.ok = function() {
			var p = tree.get_selected_branch();//获取当前选中节点
			var grade = null;
			if (p != null) {
				grade = tree.get_parent_branch(p).label;//获取父节点
			}
			$ctrl.close({
				$value: grade + "*" + $ctrl.selected
			});
		};

		$ctrl.cancel = function() {
			$ctrl.dismiss({
				$value: 'cancel'
			});
		};


		$ctrl.tree_handler = function(branch) {
			$ctrl.selected = branch.label;
		};
	}
});*/
