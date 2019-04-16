'use strict';

angular.module('markPaper')
    .component('markPaper', {
        templateUrl: 'components/markPaper/markPaper.template.html',
        controller: function MarkPaperContrller($scope, $http, $timeout, $state, $injector, $stateParams, $modal) {

            /*答卷id*/
            $scope.paper = $stateParams.paper;

            /*题目列表*/
            $scope.questions = null;

            /*选择题*/
            $scope.select = null;

            /*简答题*/
            $scope.simple = null;

            /*试卷总分*/
            $scope.totalScore = 0;

            /*选择题总分*/
            $scope.selectTotalScore = 0;

            /*简答题总分*/
            $scope.simpleTotalScore = 0;

            /*当前题目*/
            $scope.currentQuestion = {};

            /*当前题目答案*/
            $scope.currentAnswer = {};

            /*当前题目编号*/
            $scope.currentQuestionNumber = 0;

            //保存搜索框内容，用于模糊搜索
            $scope.questionNumber = "";

            /*没有题目提示窗*/
            var endModal = $modal({
                scope: $scope,
                templateUrl: 'components/markPaper/endModal.template.html',
                show: false
            });

            /* 获取题目 */
            var getQuestion = function (questionId) {
                $scope.isLoading = true;
                $http.get('/exam/v1/answer-papers/papers/' + $scope.paper.id + "/questions/" + questionId).then(function (success) {
                    /* 返回的题目 */
                    var question = success.data;
                    /*返回空*/
                    if(question == "") {
                        //显示modal
                        endModal.$promise.then(endModal.show);
                        accomplish();
                        return;
                    }
                    /* 转换为数字类型*/
                    if(question.markScore) {
                        question.markScore = Number(question.markScore);
                    }
                    $scope.currentQuestion = question;
                    $scope.currentQuestionNumber = question.number;
                }, function (error) {
                    alert("error");
                });
            };

            /*获取答案*/
            var getAnswer = function(questionNumber) {
                $http.get('/exam/v1/questions/papers/' + $scope.paper.id + "/questions/" +
                    questionNumber + '?answerPaperId=' + $scope.paper.id)
                    .then(function (success) {
                        if(success.data != '') {
                            $scope.currentAnswer = success.data;
                        }
                        console.log(success);
                    }, function (error) {
                        console.log(error);
                    });
            };

            /**
             * 提交当前题目
             */
            var submitCurrent = function () {
                $http.put('/exam/v1/answer-questions', $scope.currentQuestion).then(function (success) {
                    console.log("success");
                }, function (error) {
                    console.log("fail");
                });
            };

            /*上一题*/
            $scope.before = function () {
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                if(currentQuestionNumber - 1 <= 0 || $scope.currentQuestion.markScore == undefined || $scope.currentQuestion.markScore == '') {
                    return;
                }
                /**
                 * 获取之前先保存
                 * @type {number}
                 */
                submitCurrent();
                getQuestion($scope.currentQuestionNumber - 1);
                getAnswer(currentQuestionNumber - 1);
            };

            /*下一题*/
            $scope.next = function () {
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                if($scope.currentQuestion.markScore === undefined || $scope.currentQuestion.markScore === '') {
                    return;
                }
                /**
                 * 获取之前先保存
                 * @type {number}
                 */
                submitCurrent();
                getQuestion(currentQuestionNumber + 1);
                getAnswer(currentQuestionNumber + 1);

            };

            /*全部批改完成*/
            var accomplish = function () {
                $scope.paper.checked = 'true';
                $http.put('/exam/v1/answer-papers/' + $scope.paper.id + "/calculate").then(function (success) {
                    console.log(success);
                    $injector.get('$state').go('answerPapers');
                },function (error) {
                    console.log(error);
                });
            };

            /* 搜索功能 */
            $scope.search = function() {
                if ($scope.questionNumber != "") {
                    getQuestion($scope.questionNumber);
                    getAnswer($scope.questionNumber);
                }
            };

            /*获取题目*/
            if($scope.currentQuestionNumber == 0) {
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                getQuestion(currentQuestionNumber + 1);
                getAnswer(currentQuestionNumber + 1);
            }

        }
    });