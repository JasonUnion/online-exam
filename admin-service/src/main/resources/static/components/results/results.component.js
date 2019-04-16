'use strict';

angular.
module('results').
component('results', {
  templateUrl: 'components/results/results.template.html',
  controller: function ResultsController($scope, $http, $state, ResultsService, ErrorService) {
      $scope.data = [];
      $scope.datas = null;
      $scope.isLoading = true;

      /**
       * 刷新页面
       */
      var reloadState = function() {
          $state.reload();
      };

      /*获取分析数据*/
      var analysisPaper = function () {
        $http.get('/exam/v1/answer-papers/analysis/paper').then(function (success) {
            if(success.data) {
                var papers = success.data;
                var data = new Array();
                var obj = {};
                for(var i = 0; i< papers.length; i++) {
                    obj = new Object();
                    obj.y = papers[i].paperName;
                    obj.a = papers[i].maxScore;
                    obj.b = papers[i].minScore;
                    obj.c = papers[i].averageScore;
                    data.push(obj);
                }

                $scope.data = data;
            }
        }, function (error) {
            ErrorService.error(error);
        });
      };

      /* 以分页方式获取试卷列表 */
      $scope.callServer = function callServer(tableState) {
          $scope.isLoading = true;

          var pagination = tableState.pagination;

          var start = pagination.start || 0;
          var number = pagination.number || 10;

          ResultsService.getPage(start, number, tableState).then(function(result) {
              if (result.data == null || result.data.length == 0) {
                  $scope.datas = null;
              } else {
                  $scope.datas = result.data;
              }
              tableState.pagination.numberOfPages = result.numberOfPages;
              $scope.isLoading = false;
          });
      };

      /* 搜索功能 */
      $scope.search = function() {
          $scope.isSearch = true;
          if ($scope.paperName != "") {
              $http.get("/exam/v1/answer-papers/name/" + $scope.paperName).then(function(response) {
                  if(response.data.length == 0) {
                      $scope.datas = null;
                  }else {
                      $scope.datas = response.data;
                  }
              }, function (error) {
                  ErrorService.error(error);
              });
          }else {
              reloadState();
          }
      };
      analysisPaper();
  }
});


angular.module('results').factory('ResultsService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

    var datas = [];
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(datas, params.search.predicateObject) : datas;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/exam/v1/answer-papers?pageIndex=' + (start / 10 + 1) + '&pageSize=' + number).then(function(response) {

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