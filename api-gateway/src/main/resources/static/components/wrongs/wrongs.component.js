'use strict'

angular.module('wrongs', [])
    .component('wrongs', {
        templateUrl: 'components/wrongs/wrongs.template.html',
        controller: function ($scope, $http, $timeout, WrongsService, $injector, isLoginService) {
            $scope.url = '/exam/v1/wrong-questions';

            /*加载标志*/
            $scope.isLoading = true;

            /*搜索标志*/
            $scope.isSearch = false;

            /*试卷列表*/
            $scope.wrongs = null;

            /*保存搜索框内容*/
            $scope.wrongsName = "";

            /* 以分页方式获取试卷列表 */
            $scope.callServer = function callServer(tableState) {
                //检测是否登录
                if(!isLoginService.isLogin()) {
                    return;
                }

                $scope.isLoading = true;
                var pagination = tableState.pagination;

                var start = pagination.start || 0;
                var number = pagination.number || 10;

                WrongsService.getPage(start, number, tableState).then(function(result) {
                    if (result.data == null || result.data.length == 0) {
                        $scope.wrongs = null;
                    } else {
                        $scope.wrongs = result.data;
                    }
                    tableState.pagination.numberOfPages = result.numberOfPages;
                    $scope.isLoading = false;
                });
            }

            /* 查看详细信息 */
            $scope.info = function(wrong) {
                $injector.get('$state').go('wrongInfo', {
                    wrong: wrong
                })
            }
        }
    });

angular.module('wrongs').factory('WrongsService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', 'localStorageService', function($q, $filter, $timeout, $http, ErrorService, localStorageService) {

    var wrongs = [];
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(wrongs, params.search.predicateObject) : wrongs;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/exam/v1/wrong-questions/' +localStorageService.get('user').username + "?pageIndex="+ (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

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