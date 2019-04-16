'use strict';

angular.
module('paperList').
component('paperList', {
    templateUrl: 'components/paperList/paperList.template.html',
    controller: function PaperListController($scope, $http, $routeParams, PaperService, $timeout, $state, $injector, ErrorService) {
        $scope.url = '/exam/v1/papers/';

        /*加载标志*/
        $scope.isLoading = true;

        /*搜索标志*/
        $scope.isSearch = false;

        /*试卷列表*/
        $scope.papers = null;

        /*保存搜索框内容*/
        $scope.paperName = "";

        /**
         * 刷新页面
         */
        var reloadState = function() {
            $state.reload();
        };


        /*发布试卷*/
        $scope.publish = function (paperId) {
            var paper = {
                'id': paperId,
                'publish': '是'
            }
            $http.put('/exam/v1/papers', paper).then(function (success) {
                //刷新页面
                $timeout(function () {
                    reloadState();
                }, 300);
            }, function (error) {
                //刷新页面
                $timeout(function () {
                    reloadState();
                }, 300);
            });
        };

        /*回收试卷*/
        $scope.unPublish = function (paperId) {
            var paper = {
                'id': paperId,
                'publish': '否'
            }
            $http.put('/exam/v1/papers', paper).then(function (success) {
                //刷新页面
                $timeout(function () {
                    reloadState();
                }, 300);
            }, function (error) {
                //刷新页面
                $timeout(function () {
                    reloadState();
                }, 300);
            });
        };

        /* 以分页方式获取试卷列表 */
        $scope.callServer = function callServer(tableState) {
            $scope.isLoading = true;

            var pagination = tableState.pagination;

            var start = pagination.start || 0;
            var number = pagination.number || 10;

            PaperService.getPage(start, number, tableState).then(function(result) {
                if (result.data == null || result.data.length == 0) {
                    $scope.papers = null;
                } else {
                    $scope.papers = result.data;
                }
                tableState.pagination.numberOfPages = result.numberOfPages;
                $scope.isLoading = false;
            });
        }

        /* 搜索功能 */
        $scope.search = function() {
            $scope.isSearch = true;
            if ($scope.paperName != "") {
                $http.get("/exam/v1/papers/name/" + $scope.paperName).then(function(response) {
                    if(response.data.length == 0) {
                        $scope.papers = null;
                    }else {
                        $scope.papers = response.data;
                    }
                });
            }else {
                reloadState();
            }
        }

        /*路由到添加试卷页面*/
        $scope.goAddQuestion = function (paper) {
            $injector.get('$state').go('add-question', {
                paper: paper
            });
        };

        /*路由到题目列表*/
        $scope.goPaperQuestion = function (id) {
            $injector.get('$state').go('paper-questions', {
                paperId: id
            });
        };

        /**
         * 路由到添加考试
         */
        $scope.add = function () {
            $injector.get('$state').go('add-paper');
        }
    }
});

angular.module('paperList').factory('PaperService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

    var papers = []; //学生数组,用于保存查询结果
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(papers, params.search.predicateObject) : papers;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/exam/v1/papers?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

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