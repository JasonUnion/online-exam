'use strict';

angular.
module('profile').
component('profile', {
    templateUrl: 'components/profile/profile.template.html',
    controller: function ProfileController($scope, $http, $rootScope, FileUploader, $state, $timeout, localStorageService, ErrorService) {
		$scope.userId = '';
		$scope.username = '';
		$scope.password = '';
		$scope.birthday = '';
		$scope.gender = '男';
		$scope.marry = '已婚';
		$scope.address = '';
		$scope.mobilePhone = '';
		$scope.email = '';
		$scope.identityCard = '';
		$scope.introduction = '';
        $scope.avatar = null;

        /**
         * 刷新页面
         */
        var reloadState = function() {
            $state.reload();
        };

    	var uploader = $scope.uploader = new FileUploader({
            url: '/auth/v1/users/avatar'
        });

        uploader.filters.push({
            name: 'imageFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function(fileItem) {
            //添加文件后调用
            $scope.avatar = null;
        };
        uploader.onAfterAddingAll = function(addedFileItems) {
            //添加全部文件后调用
        };
        uploader.onBeforeUploadItem = function(item) {
            //上传文件前调用
        };
        uploader.onProgressItem = function(fileItem, progress) {
            //进度条
        };
        uploader.onProgressAll = function(progress) {
            //进度条
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            //上传成功
            $(".upload-seccess").removeClass("in").show();
            $(".upload-seccess").delay(200).addClass("in").fadeOut(2000);
            $timeout(function () {
                reloadRoute();
            }, 2000);

        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            //上传失败
            $(".upload-fail").removeClass("in").show();
            $(".upload-fail").delay(200).addClass("in").fadeOut(2000);
        };
        uploader.onCancelItem = function(fileItem, response, status, headers) {
            //取消上传调用
        };
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
            //上传文件完成调用
        };
        uploader.onCompleteAll = function() {
            //上传全部文件完成调用
        };


        /**
         * 获取用户信息
         */
    	var getInfo = function() {
    		$http.get('/auth/v1/users/me').then(function(response) {
    			$scope.userId = response.data.id;
    			$scope.username = response.data.username;
				$scope.password = response.data.password;
				$scope.email = response.data.email;
				$scope.gender = response.data.gender;
                $scope.avatar = response.data.avatar;
                $scope.mobilePhone = response.data.mobilePhone;
                $scope.address = response.data.address;
                $scope.introduction = response.data.introduction;
                $scope.identityCard = response.data.identityCard;
                $scope.birthday = response.data.birthday;
    		}, function (error) {
                ErrorService.error(error);
            });
    	};

        /**
         * 保存信息
         */
        $scope.save = function() {
        	var user = {
        		'id': $scope.userId,
				'username': $scope.username,
				'password': $scope.password,
				'birthday': $scope.birthday,
				'gender': $scope.gender,
				'marry': $scope.marry,
				'address': $scope.address,
				'mobilePhone': $scope.mobilePhone,
				'email': $scope.email,
				'identityCard': $scope.identityCard,
				'introduction': $scope.introduction
			};
			//执行保存
        	$http.put('/auth/v1/users/id', JSON.stringify(user)).then(function (response) {
				$(".save-success").removeClass("in").show();
				$(".save-success").delay(200).addClass("in").fadeOut(2000);
                localStorageService.remove('user');
                //刷新页面
                $timeout(function () {
                    reloadRoute();
                }, 2000);
			}, function (error) {
                ErrorService.error(error);
            });
        };

        /**
         * 刷新页面
         */
        var reloadRoute = function() {
            reloadState();
        }

        /*清除数据*/
        $scope.clear = function () {
            $scope.username = '';
            $scope.email = '';
            $scope.gender = '';
            $scope.mobilePhone = '';
            $scope.address = '';
            $scope.introduction = '';
            $scope.identityCard = '';
        }

        getInfo();
    }
});

angular.module('profile')
	.directive('ngThumb',['$window', function($window) {
		  var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function(item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function(file) {
                var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        };

        return {
            restrict: 'A',
            template: '<canvas/>',
            link: function(scope, element, attributes) {
                if (!helper.support) return;

                var params = scope.$eval(attributes.ngThumb);

                if (!helper.isFile(params.file)) return;
                if (!helper.isImage(params.file)) return;

                var canvas = element.find('canvas');
                var reader = new FileReader();

                reader.onload = onLoadFile;
                reader.readAsDataURL(params.file);

                function onLoadFile(event) {
                    var img = new Image();
                    img.onload = onLoadImage;
                    img.src = event.target.result;
                }

                function onLoadImage() {
                    var width = params.width || this.width / this.height * params.height;
                    var height = params.height || this.height / this.width * params.width;
                    canvas.attr({ width: width, height: height });
                    canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
                }
            }
        };
	}]);
