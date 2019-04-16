'use strict';

angular.
module('addPaper').
component('addPaper', {
    templateUrl: 'components/addPaper/addPaper.template.html',
    controller: function AddPaperController($scope, $http, $timeout, $injector, FileUploader, ErrorService) {

        /*访问后端的url*/
        $scope.url = '/exam/ques/paper/';

        /*考试对象*/
        $scope.paper = {}

        /*考试名称*/
        $scope.paperName = "";

        /*开始时间*/
        $scope.startTime = "";

        /*结束时间*/
        $scope.endTime = "";

        /*备注说明*/
        $scope.remark = "";

        /*课程对象*/
        $scope.subject = {};

        /*课程数组*/
        $scope.subjects = null;

        /*考核类型：默认为正式考试*/
        $scope.type = 'official';

        /*考试图片*/
        $scope.avatar = '';

        var uploader = $scope.uploader = new FileUploader({
            url: '/exam/v1/papers/avatar'
        });

        uploader.filters.push({
            name: 'imageFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            //上传成功
            $scope.avatar = response;
            $(".upload-success").removeClass("in").show();
            $(".upload-success").delay(200).addClass("in").fadeOut(2000);

        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            //上传失败
            $(".upload-fail").removeClass("in").show();
            $(".upload-fail").delay(200).addClass("in").fadeOut(2000);
        };

        /*添加考试*/
        $scope.addPaper = function() {
             var date = new Date();
             var created = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() + " "
             + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
             var paper = {
             "name": $scope.paperName,
             "startTime": $scope.startTime._i,
             "endTime": $scope.endTime._i,
             "created": created,
             "type": $scope.type,
             "remark": $scope.remark,
             "avatar": $scope.avatar
             };
             if($scope.subject.name === undefined || $scope.subject.name === null ) {
                 return;
             }
             $http.post("/exam/v1/papers/" + $scope.subject.id, paper).then(function (response) {
             //上传成功
             $(".save-success").removeClass("in").show();
             $(".save-success").delay(200).addClass("in").fadeOut(2000);
             $timeout(function () {
             $injector.get('$state').go('papers');
             }, 2000);
             }, function (error) {
                 ErrorService.error(error);
             });
        };

        var getSubjects = function () {
            $http.get('/exam/v1/subjects').then(function (success) {
                $scope.subjects = success.data.list;
            }, function (error) {
                ErrorService.error(error);
            });
        };

        getSubjects();
    }
});