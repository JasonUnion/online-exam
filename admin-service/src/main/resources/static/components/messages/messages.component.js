'use strict';

angular.module('messages')
    .component('messages', {
        templateUrl: 'components/messages/messages.template.html',
        controller: function MessagesController($modal, $scope, $http, MessageService, $injector, $timeout, $state, ErrorService) {

            //加载中标志
            $scope.loading = true;
            $scope.url = '/auth/v1/contacts';

            //消息列表
            $scope.messages = null;

            //具体的某个消息
            $scope.message = null;


            //保存搜索框内容，用于模糊搜索
            $scope.messageName = "";

            /**
             * 刷新页面
             */
            var reloadState = function() {
                $state.reload();
            };

            //查看消息信息
            var infoModal = $modal({
                scope: $scope,
                templateUrl: 'components/messages/messageInfo.template.html',
                show: false
            });

            /*===============================================*/
            $scope.showInfoModal = function(id) {
                //根据id查询消息信息
                $http.get("/auth/v1/contacts/" + id).then(function(response) {
                    //赋值
                    $scope.message = response.data;
                    //显示查询信息
                    infoModal.$promise.then(infoModal.show);
                }, function(error) {
                    ErrorService.error(error);
                });
            };

            /*删除*/
            $scope.delete = function(id) {

                /*测试*/
                if (confirm('是否删除?')) {
                    /*http delete 后台*/
                    $http.delete("/auth/v1/contacts/" + id).then(function(success) {
                        //刷新页面
                        $timeout(function () {
                            reloadState();
                        }, 500);
                    }, function (error) {
                        //刷新页面
                        $timeout(function () {
                            reloadState();
                        }, 500);
                    });
                }
            }

            /* 以分页方式获取班级列表 */
            $scope.callServer = function callServer(tableState) {
                $scope.isLoading = true;

                var pagination = tableState.pagination;

                var start = pagination.start || 0;
                var number = pagination.number || 10;

                MessageService.getPage(start, number, tableState).then(function(result) {
                    if (result.data == null || result.data.length == 0) {
                        $scope.messages = null;
                    } else {
                        $scope.messages = result.data;
                    }
                    tableState.pagination.numberOfPages = result.numberOfPages;
                    $scope.isLoading = false;
                });
            }

            /* 搜索功能 */
            $scope.search = function() {
                if ($scope.messageName != "") {
                    $http.get("/auth/v1/contacts/users/" + $scope.messageName).then(function(reponse) {
                        $scope.messages = reponse.data;
                    });
                }else {
                    reloadState();
                }
            }
        }
    });

angular.module('messages').factory('MessageService', ['$q', '$filter', '$timeout', '$http', 'ErrorService', function($q, $filter, $timeout, $http, ErrorService) {

    var messages = [];
    function getPage(start, number, params) {
        var deferred = $q.defer();
        var filtered = params.search.predicateObject ? $filter('filter')(messages, params.search.predicateObject) : messages;
        if (params.sort.predicate) {
            filtered = $filter('orderBy')(filtered, params.sort.predicate, params.sort.reverse);
        }

        var result = null;
        var pages = null;

        $http.get('/auth/v1/contacts?pageIndex=' + (start / 10 + 1) + "&pageSize=" + number).then(function(response) {

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