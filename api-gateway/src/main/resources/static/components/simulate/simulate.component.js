'use strict';

angular.module('simulate')
    .component('simulate', {
        templateUrl: 'components/simulate/simulate.template.html',
        controller: function SimulateController($scope, $http, $state, $injector, $stateParams, localStorageService, $modal, $timeout, ErrorService) {

            /*加载中标志*/
            $scope.loading = true;

            /*试卷*/
            $scope.paper = $stateParams.paper;

            /*答卷*/
            $scope.answerPapers = null;

            /*试卷id*/
            $scope.papaerId = $stateParams.paper.id;

            /*考试总时间*/
            $scope.totalTime = Math.round(((((new Date($scope.paper.endTime)) - (new Date($scope.paper.startTime))) % 86400000) % 3600000) / 60000);

            /*试卷总分*/
            $scope.totalScore = 0;

            /*选择题总分*/
            $scope.selectTotalScore = 0;

            /*简答题总分*/
            $scope.simpleTotalScore = 0;

            /*结束时间*/
            $scope.endTime = (new Date($stateParams.paper.endTime)).getTime();

            /*简答题*/
            $scope.simple = null;

            /*考试结束标志*/
            $scope.end = false;

            /*考试时间结束*/
            $scope.timeUp = false;

            /*当前题目*/
            $scope.currentQuestion = {};

            /*当前题目编号*/
            $scope.currentQuestionNumber = 0;

            /*题目数量*/
            $scope.count = 0;

            /*考试开始提示窗*/
            var startModal = $modal({
                scope: $scope,
                templateUrl: 'components/simulate/startModal.template.html',
                show: false
            });

            /*没有题目提示窗*/
            var endModal = $modal({
                scope: $scope,
                templateUrl: 'components/simulate/endModal.template.html',
                show: false
            });

            /*提交成功提示窗*/
            var successModal = $modal({
                scope: $scope,
                templateUrl: 'components/simulate/successModal.template.html',
                show: false
            });

            /*提交失败提示窗*/
            var failModal = $modal({
                scope: $scope,
                templateUrl: 'components/simulate/failModal.template.html',
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

                //todo 自动组卷

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
                    console.log("获取试卷失败！");
                    ErrorService.error(error);
                });
            };

            /* 获取题目 */
            var getQuestion = function (questionId) {
                $scope.isLoading = true;
                var url = null;
                if($scope.answerPapers != null) {
                    url = '/exam/v1/exam/questions/' + questionId + '?paperId=' + $scope.papaerId + '&username=' + localStorageService.get('user').username
                        + '&answerPaperId=' + $scope.answerPapers.id;
                }else {
                    url = '/exam/v1/exam/questions/' + questionId + '?paperId=' + $scope.papaerId + '&username=' + localStorageService.get('user').username;
                }

                $http.get(url).then(function (success) {
                    /* 返回的题目 */
                    var question = success.data;
                    /*返回空*/
                    if(question == "") {
                        //显示modal
                        endModal.$promise.then(endModal.show);
                        return;
                    }
                    $scope.currentQuestion = question;
                    $scope.currentQuestionNumber = question.number;
                }, function (error) {
                    console.log("获取题目失败！");
                    ErrorService.error(error);
                });
            };

            /**
             * 提交试卷
             */
            $scope.submit = function () {
                if($scope.timeUp || $scope.answerPapers == null || $scope.count == 0) {
                    return;
                }
                $http.post('/exam/v1/exam/submit/simulate/' + localStorageService.get("user").username + "?answerPaperId=" + $scope.answerPapers.id, $scope.paper).then(function (success) {
                    //显示modal
                    successModal.$promise.then(successModal.show);
                    //隐藏modal
                    $timeout(function () {
                        successModal.$promise.then(successModal.hide);
                        $injector.get('$state').go('simulate-list');
                    }, 1000);
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
             * 提交当前题目
             */
            var submitCurrent = function () {
                var submit = {
                    paper: $scope.paper,
                    question: $scope.currentQuestion
                }
                $http.post('/exam/v1/exam/submit/one/' + localStorageService.get("user").username, submit).then(function (success) {
                    //第一次提交时后端返回答卷信息
                    if(success.data != '') {
                        $scope.answerPapers = success.data;
                    }
                    console.log("提交当前题目成功！");
                }, function (error) {
                    console.log("提交当前题目失败！");
                    ErrorService.error(error);
                });
            };

            /**
             * 考试时间结束
             * @type {{}}
             */
            $scope.callbackTimer={};
            $scope.callbackTimer.finished=function(){
                $scope.timeUp = true;
                if($scope.paper.questions != null) {
                    $scope.submit();
                }
            };

            /*上一题*/
            $scope.before = function () {
                if( $scope.count == 0) {
                    return;
                }
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
                if( $scope.count == 0) {
                    return;
                }
                var currentQuestionNumber = Number($scope.currentQuestionNumber);
                /**
                 * 获取之前先保存
                 * @type {number}
                 */
                submitCurrent();
                //判断是否结束
                if(currentQuestionNumber == $scope.count) {
                    $scope.end = true;
                    return;
                }
                getQuestion(currentQuestionNumber + 1);

            };

            var getQuestionCount = function () {
                $http.get('/exam/v1/questions/papers/' + $scope.papaerId + '/count').then(function (success) {
                    /*没有题目*/
                    if(success.data && success.data == 0) {
                        $scope.count = 0;
                        endModal.$promise.then(endModal.show);
                        return;
                    }
                    $scope.count = success.data;
                }, function (error) {
                    console.log("获取题目数失败！");
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
