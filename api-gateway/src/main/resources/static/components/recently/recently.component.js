'use strict';

angular.module('recently')
    .component('recently', {
        templateUrl: 'components/recently/recently.template.html',
        controller: function RecentlyController($scope, $http, $state, RecentlyPapersService, $injector, isLoginService) {

            /*加载中标志*/
            $scope.isRecentlyLoading = true;
            $scope.isMyLoading = true;

            /*试卷列表*/
            $scope.recentlyPapers = null;

            /**
             * 刷新页面
             */
            var reloadState = function() {
                $state.reload();
            };

            /* 以分页方式获取最近发布试卷列表 */
            $scope.callServerRecently = function callServerRecently(tableState) {
                //检测是否登录
                if(!isLoginService.isLogin()) {
                    return;
                }
                $scope.isRecentlyLoading = true;
                var pagination = tableState.pagination;

                var start = pagination.start || 0;
                var number = pagination.number || 10;

                RecentlyPapersService.getPage(start, number, tableState).then(function(result) {
                    if (result.data == null || result.data.length == 0) {
                        $scope.recentlyPapers = null;
                    } else {
                        $scope.recentlyPapers = result.data;
                    }
                    tableState.pagination.numberOfPages = result.numberOfPages;
                    $scope.isRecentlyLoading = false;
                });
            };

            /*参加正式考试*/
            $scope.join = function (paper) {
                //TODO
                $injector.get('$state').transitionTo('exam', {
                    paper: paper
                });
            };
        }
    });

//定义服务
angular.module('recently')
    .factory('RecentlyPapersService',['$q','$timeout','$http', 'ErrorService', function ($q,$timeout,$http, ErrorService) {

        function getPage(start, number, params) {
            var deferred = $q.defer();
            var result = null;
            var pages = null;

            $http.get('/exam/v1/papers/published/official?pageIndex=' + (start / 10 + 1) + '&pageSize=' + number).then(function (success) {
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