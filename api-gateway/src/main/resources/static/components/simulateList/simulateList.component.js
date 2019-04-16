'use strict';

angular.module('simulateList')
    .component('simulateList', {
        templateUrl: 'components/simulateList/simulateList.template.html',
        controller: function SimulateListController($scope, $http, $state, SimulateListPapersService, $injector, isLoginService) {

            /*加载中标志*/
            $scope.isSimulateLoading = true;
            $scope.isMyLoading = true;

            /*试卷列表*/
            $scope.simulatePapers = null;

            /**
             * 刷新页面
             */
            var reloadState = function() {
                $state.reload();
            };

            /* 以分页方式获取最近发布的模拟试卷列表 */
            $scope.callServerSimulate = function callServerSimulate(tableState) {
                //检测是否登录
                if(!isLoginService.isLogin()) {
                    return;
                }

                $scope.isSimulateLoading = true;
                var pagination = tableState.pagination;

                var start = pagination.start || 0;
                var number = pagination.number || 10;

                SimulateListPapersService.getPage(start, number, tableState).then(function(result) {
                    if (result.data == null || result.data.length == 0) {
                        $scope.simulatePapers = null;
                    } else {
                        $scope.simulatePapers = result.data;
                    }
                    tableState.pagination.numberOfPages = result.numberOfPages;
                    $scope.isSimulateLoading = false;
                });
            };

            /*参加模拟考试*/
            $scope.joinSimulate = function (paper) {
                //TODO
                $injector.get('$state').transitionTo('simulate', {
                    paper: paper
                });
            };
        }
    });

//定义服务
angular.module('simulateList')
    .factory('SimulateListPapersService',['$q','$timeout','$http', 'ErrorService', function ($q,$timeout,$http,ErrorService) {

        function getPage(start, number, params) {
            var deferred = $q.defer();
            var result = null;
            var pages = null;

            $http.get('/exam/v1/papers/type/simulate?pageIndex=' + (start / 10 + 1) + '&pageSize=' + number).then(function (success) {
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