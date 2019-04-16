(function () {
    "use strict";
    angular.module('Tek.progressBar', []).run(['$templateCache', function ($templateCache) {
        $templateCache.put('Tek.progressBarDirective.html', "<div class='progress' ng-class='bar.containerClass'><div class='progress-bar' ng-class='bar.barClass' ng-transclude></div></div>");
    }]);
}());