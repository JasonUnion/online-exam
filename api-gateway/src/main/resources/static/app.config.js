angular.
module('contentApp').
config(['$locationProvider','$stateProvider',

  function config($locationProvider, $stateProvider) {

    $locationProvider.hashPrefix('!');

    $stateProvider.state('home',{
          url:'/home',
          template: '<home></home>'
        }).state('features', {
          url:'/features',
          template: '<features></features>'
        }).state('recently',{
            url:'/recently',
            template: '<recently></recently>'
        }).state('simulate-list',{
            url:'/simulate-list',
            template: '<simulate-list></simulate-list>'
        }).state('record-list',{
            url:'/record-list',
            template: '<record-list></record-list>'
        }).state('exam',{
            url: '/exam',
            template: '<exam></exam>',
            params: {
                paper: null
            }
        }).state('results', {
            url: '/results',
            template: '<results></results>',
            params: {
                result: null,
                paper: null
            }
        }).state('simulate', {
            url: '/simulate',
            template: '<simulate></simulate>',
            params: {
                paper: null
            }
        }).state('practiceList', {
            url: '/practice-list',
            template: '<practice-list></practice-list>'
        }).state('practice', {
            url: '/practice',
            template: '<practice></practice>',
            params: {
                practice: null
            }
        }).state('wrong', {
            url: '/wrong',
            template: '<wrong></wrong>',
            params: {
                results: null
            }
        }).state('wrongs', {
            url: '/wrongs',
            template: '<wrongs></wrongs>',
        }).state('wrongInfo', {
            url: '/wrong-info',
            template: '<wrong-info></wrong-info>',
            params: {
                wrong: null
            }
        }).state('404', {
        templateUrl: 'components/status/404.html'
    }).state('401', {
        templateUrl: 'components/status/401.html'
    })
        .state('500', {
            templateUrl: 'components/status/500.html'
        }).state('otherwise', {
          url:'',
          template: '<home></home>'
        });

  }
]);


/**
 * 错误统一处理服务和登录检测服务
 */
angular.module('contentApp')
    .factory('ErrorService', ['$injector', function ($injector) {

        function error(error) {
            if(error.status === 404) {
                $injector.get('$state').transitionTo('404');
            }else if(error.status == 401) {
                $injector.get('$state').transitionTo('401');
            }else {
                $injector.get('$state').transitionTo('500');
            }
        }
        return {
            error: error
        };
    }])
    .factory('isLoginService', ['$rootScope', '$injector', function ($rootScope, $injector) {

        function isLogin() {
            if($rootScope.authenticated == null || $rootScope.authenticated == false) {
                $injector.get('$state').transitionTo('401');
                return false;
            }else {
                return true;
            }
        }
        return {
            isLogin: isLogin
        }
    }]);