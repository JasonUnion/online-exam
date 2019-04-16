'use strict';

angular.module('recordList')
    .component('recordList', {
        templateUrl: 'components/recordList/recordList.template.html',
        controller: function SimulateListController($scope, $http, $state, RecordListService, $injector, isLoginService) {

            /*加载中标志*/
            $scope.isMyLoading = true;

            /*试卷列表*/
            $scope.myPapers = null;

            /**
             * 刷新页面
             */
            var reloadState = function() {
                $state.reload();
            };

            /* 以分页方式获取我的试卷列表 */
            $scope.callServerMy = function callServerMy(tableState) {
                //检测是否登录
                if(!isLoginService.isLogin()) {
                    return;
                }

                $scope.isMyLoading = true;
                var pagination = tableState.pagination;

                var start = pagination.start || 0;
                var number = pagination.number || 10;

                RecordListService.getPage(start, number, tableState).then(function(result) {
                    if (result.data == null || result.data.length == 0) {
                        $scope.myPapers = null;
                    } else {
                        $scope.myPapers = result.data;
                    }
                    tableState.pagination.numberOfPages = result.numberOfPages;
                    $scope.isMyLoading = false;
                });
            };

            /*查看我的考试*/
            $scope.info = function (paper) {
                $injector.get('$state').transitionTo('results', {
                    paper: paper
                })
            };
        }
    });

//定义服务
angular.module('recordList')
    .factory('RecordListService',['$q','$timeout','$http','localStorageService', 'ErrorService', function ($q,$timeout,$http,localStorageService, ErrorService) {

        function getPage(start, number, params) {
            var deferred = $q.defer();
            var result = null;
            var pages = null;

            $http.get('/exam/v1/answer-papers/users/' + localStorageService.get("user").username + "/" + "?pageIndex=" + (start / 10 + 1) + "&pageSize=" + number).then(function (success) {
                result = success.data.list;
                pages = success.data.pages;

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