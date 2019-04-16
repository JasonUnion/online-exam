'use strict';

angular.
module('answerPapers').
component('answerPapers', {
    templateUrl: 'components/answerPapers/answerPapers.template.html',
    controller: function AnswerPapersController($scope, $http, AnswerPapersService, $timeout, $state, $injector, ErrorService) {

        /*加载标志*/
        $scope.isLoading = true;

        /*搜索标志*/
        $scope.isSearch = false;

        /*试卷列表*/
        $scope.answerPapers = null;

        /*保存搜索框内容*/
        $scope.paperName = "";

        /**
         * 刷新页面
         */
        var reloadState = function() {
            $state.reload();
        };


        /* 以分页方式获取试卷列表 */
        $scope.callServer = function callServer(tableState) {
            $scope.isLoading = true;

            var pagination = tableState.pagination;

            var start = pagination.start || 0;
            var number = pagination.number || 10;

            AnswerPapersService.getPage(start, number, tableState).then(function(result) {
                if (result.data == null || result.data.length == 0) {
                    $scope.answerPapers = null;
                } else {
                    $scope.answerPapers = result.data;
                }
                tableState.pagination.numberOfPages = result.numberOfPages;
                $scope.isLoading = false;
            });
        }

        /* 搜索功能 */
        $scope.search = function() {
            $scope.isSearch = true;
            if ($scope.paperName != "") {
                $http.get("/exam/v1/answer-papers/name/" + $scope.paperName).then(function(response) {
                    if(response.data.length == 0) {
                        $scope.answerPapers = null;
                    }else {
                        $scope.answerPapers = response.data;
                    }
                });
            }
        };
        
        $scope.markPaper = function (paper) {
            $injector.get('$state').go('markPaper', {
                paper: paper
            });
        }
    }
});

angular.module('answerPapers').factory('AnswerPapersService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

    var answerPapers = []; //试卷数组,用于保存查询结果
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(answerPapers, params.search.predicateObject) : answerPapers;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/exam/v1/answer-papers?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

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