'use strict';

angular.module('practice')
    .component('practice', {
        templateUrl: 'components/practice/practice.template.html',
        controller: function ExamController($scope, $http, $state, $injector, $stateParams, localStorageService, $modal, $timeout, progressBarManager, ErrorService) {

            /*加载中标志*/
            $scope.loading = true;

            /*试卷*/
            $scope.practice = $stateParams.practice;

            /*答卷*/
            $scope.answerPapers = null;

            /*试卷id*/
            $scope.practiceId = $stateParams.practice.id;

            /*试卷总分*/
            $scope.totalScore = 0;

            /*选择题总分*/
            $scope.selectTotalScore = 0;

            /*简答题总分*/
            $scope.simpleTotalScore = 0;

            /*选择题*/
            $scope.select = null;

            /*简答题*/
            $scope.simple = null;

            /*考试结束标志*/
            $scope.end = false;

            /*当前题目*/
            $scope.currentQuestion = {};

            /*当前题目编号*/
            $scope.currentQuestionNumber = 0;

            /*进度条*/
            $scope.bar = progressBarManager();
            $scope.bar2ProgressVal = 0;

            /*题目数量*/
            $scope.count = 0;

            /*考试开始提示窗*/
            var startModal = $modal({
                scope: $scope,
                templateUrl: 'components/practice/startModal.template.html',
                show: false
            });

            /*没有题目提示窗*/
            var endModal = $modal({
                scope: $scope,
                templateUrl: 'components/practice/endModal.template.html',
                show: false
            });

            /*提交成功提示窗*/
            var successModal = $modal({
                scope: $scope,
                templateUrl: 'components/practice/successModal.template.html',
                show: false
            });

            /*提交失败提示窗*/
            var failModal = $modal({
                scope: $scope,
                templateUrl: 'components/practice/failModal.template.html',
                show: false
            });

            /**
             * 刷新页面
             */
            var reloadState = function() {
                $state.reload();
            };

            /* 获取试卷 */
            var getPaper = function (paperId) {
                $scope.isLoading = true;

                $http.get('/exam/v1/questions/papers/' + paperId + "/ignore").then(function (success) {
                    var paper = success.data;
                    var select = new Array();
                    var simple = new Array();
                    for(var i = 0; i < paper.length; i++) {
                        if(paper[i].type && paper[i].type == '选择题') {
                            select.push(paper[i]);
                            //计算试卷总分
                            $scope.totalScore += Number(paper[i].score);
                            //计算选择题总分
                            $scope.selectTotalScore += Number(paper[i].score);
                            $scope.selectScore = paper[i].score;
                        } else if(paper[i].type && paper[i].type == '简答题') {
                            simple.push(paper[i]);
                            //计算试卷总分
                            $scope.totalScore += Number(paper[i].score);
                            //计算简答题总分
                            $scope.simpleTotalScore += Number(paper[i].score);
                        }
                    }
                    $scope.select = select;
                    $scope.simple = simple;

                    //显示考试开始提示框
                    startModal.$promise.then(startModal.show);
                }, function (error) {
                    ErrorService.error(error);
                });
            };

            /* 获取题目 */
            var getQuestion = function (questionId) {
                $scope.isLoading = true;

                var url = null;
                if($scope.answerPapers != null) {
                    url = '/exam/v1/exam/questions/' + questionId + '?paperId=' + $scope.practiceId + '&username=' + localStorageService.get('user').username
                        + '&answerPaperId=' + $scope.answerPapers.id;
                }else {
                    url = '/exam/v1/exam/questions/' + questionId + '?paperId=' + $scope.practiceId + '&username=' + localStorageService.get('user').username;
                }
                $http.get(url).then(function (success) {
                    /* 返回的题目 */
                    var question = success.data;
                    /*返回空*/
                    if(question == "" && $scope.count != 0) {
                        //计算成绩
                        //endModal.$promise.then(endModal.show);
                        submit();
                        return;
                    }
                    $scope.currentQuestion = question;
                    $scope.currentQuestionNumber = question.number;
                }, function (error) {
                    ErrorService.error(error);
                });
            };

            /**
             * 提交试卷
             */
            var submit = function () {
                $http.post('/exam/v1/exam/submit/practice/' + localStorageService.get("user").username, $scope.practice).then(function (success) {
                    $injector.get('$state').go('results', {
                        result: success.data
                    });
                }, function (error) {
                    //显示modal
                    failModal.$promise.then(failModal.show);
                    //隐藏modal
                    $timeout(function () {
                        failModal.$promise.then(failModal.hide);
                    }, 1000);
                });
            };

            /**
             * 提交试卷
             */
            $scope.submit = function () {
                submit();
            };

            /**
             * 提交当前题目
             */
            var submitCurrent = function () {
                var submit = {
                    paper: $scope.practice,
                    question: $scope.currentQuestion
                }
                $http.post('/exam/v1/exam/submit/one/' + localStorageService.get("user").username, submit).then(function (success) {
                    //第一次提交时后端返回答卷信息
                    if(success.data != '') {
                        $scope.answerPapers = success.data;
                    }
                    console.log("success");
                }, function (error) {
                    console.log("fail");
                    ErrorService.error(error);
                });
            };


            /*上一题*/
            $scope.before = function () {
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                if(currentQuestionNumber - 1 <= 0) {
                    return;
                }
                /**
                 * 获取之前先保存
                 * @type {number}
                 */
                submitCurrent();
                getQuestion($scope.currentQuestionNumber - 1);
            };

            /*下一题*/
            $scope.next = function () {
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                /**
                 * 获取之前先保存
                 * @type {number}
                 */
                submitCurrent();
                $scope.bar2ProgressVal += (100/$scope.count);
                //判断是否结束
                if(currentQuestionNumber == $scope.count) {
                    $scope.end = true;
                    return;
                }
                getQuestion(currentQuestionNumber + 1);
            };

            var getQuestionCount = function () {
                $http.get('/exam/v1/questions/papers/' + $scope.practiceId + '/count').then(function (success) {
                    /*没有题目*/
                    if(success.data && success.data == 0) {
                        endModal.$promise.then(endModal.show);
                        return;
                    }
                    $scope.count = success.data;
                }, function (error) {
                    console.log("fail");
                    ErrorService.error(error);
                });
            };

            /*获取题目*/
            if($scope.currentQuestionNumber == 0) {
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                getQuestionCount();
                getQuestion(currentQuestionNumber + 1);
            }
        }
    });
