angular.
module('contentApp').
config(['$locationProvider', '$routeProvider', 'momentPickerProvider', '$stateProvider',

  function config($locationProvider, $routeProvider, $momentPickerProvider, $stateProvider) {

    $locationProvider.hashPrefix('!');

    $stateProvider.state('/', {
          url:'',
          template: '<students></students>'
        }).state('students',{
          url:'/students',
          template: '<students></students>'
        }).state('addStudent', {
          url:'/add-student',
          template: '<add-student></add-student>'
        }).state('add-subject', {
          url:'/add-subject',
          template: '<subjects></subjects>'
        }).state('add-paper', {
          url:'/add-paper',
          template: '<add-paper></add-paper>'
        }).state('papers', {
          url:'/papers',
          template: '<paper-list></paper-list>'
        }).state('grade', {
          url:'/grade',
          template: '<grade></grade>'
        }).state('grades', {
          url:'/grades',
          template: '<grade-list></grade-list>'
        }).state('profile', {
          url:'/profile',
          template: '<profile></profile>'
        }).state('authority', {
          url:'/authority',
          template: '<authority></authority>'
        }).state('add-question', {
          url: '/add-question',
          template: '<add-question></add-question>',
          params: {
              paper: null
          }
        }).state('question-list', {
            url:'/question-list',
            template: '<question-list></question-list>'
        }).state('paper-questions', {
            url: '/paper-questions',
            template: '<paper-questions></paper-questions>',
            params: {
                paperId: null
            }
        }).state('teachers', {
            url: '/teachers',
            template: '<teachers></teachers>'
        }).state('answerPapers', {
            url: '/answer-papers',
            template: '<answer-papers></answer-papers>'
        }).state('markPaper', {
            url: '/mark-paper',
            template: '<mark-paper></mark-paper>',
            params: {
                paper: null
            }
        }).state('results', {
            url: '/results',
            template: '<results></results>'
        }).state('change-password', {
            url: '/change-password',
            template: '<change-password></change-password>'
        }).state('messages', {
            url: '/messages',
            template: '<messages></messages>'
        }).state('401', {
            templateUrl: 'components/status/401.html'
        }).state('403', {
            templateUrl: 'components/status/403.html'
        }).state('404', {
          templateUrl: 'components/status/404.html'
        })
        .state('500', {
          templateUrl: 'components/status/500.html'
        });

    $momentPickerProvider.options({
      locale: 'zh-cn'
    });

  }
]);



/**
 * 错误统一处理
 */
angular.module('contentApp')
    .factory('ErrorService', ['$injector', function ($injector) {
        function error(error) {
            if(error.status === 404) {
                $injector.get('$state').transitionTo('404');
            }else if(error.status == 401) {
                $injector.get('$state').transitionTo('401');
            }else if(error.status == 403) {
                $injector.get('$state').transitionTo('403');
            }else {
                $injector.get('$state').transitionTo('500');
            }
        }
        return {
            error: error
        };
}]);