'use strict';

angular.
module('addQuestion').
component('addQuestion', {
    templateUrl: 'components/addQuestion/addQuestion.template.html',
    controller: function AddQuestionController($scope, $http, $stateParams, $timeout, $injector) {

        /*试卷*/
        $scope.paper = $stateParams.paper;

        /*题目*/
        $scope.question = {};

        /*题目类型*/
        $scope.type = '选择题';

        $scope.submit = function() {
        	if($scope.type == '选择题') {
                $scope.question.select.type = '选择题';
        	    //保存选择题
        		$http.post('/exam/v1/questions/' + $scope.paper.id, $scope.question.select).then(function (response) {
                    //保存成功
                    $(".save-success").removeClass("in").show();
                    $(".save-success").delay(200).addClass("in").fadeOut(2000);
                }, function (error) {
                    //保存失败
                    $(".save-fail").removeClass("in").show();
                    $(".save-fail").delay(200).addClass("in").fadeOut(2000);
                });
        	}else if($scope.type == '简答题') {
                $scope.question.simple.type = '简答题';
        	    //保存简答题
                $http.post('/exam/v1/questions/' + $scope.paper.id, $scope.question.simple).then(function (response) {
                    //保存成功
                    $(".save-success").removeClass("in").show();
                    $(".save-success").delay(200).addClass("in").fadeOut(2000);
                }, function (error) {
                    //保存失败
                    $(".save-fail").removeClass("in").show();
                    $(".save-fail").delay(200).addClass("in").fadeOut(2000);
                });
        	}
        };
        
    }
});