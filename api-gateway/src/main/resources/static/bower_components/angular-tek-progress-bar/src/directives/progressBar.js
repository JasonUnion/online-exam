(function () {
    "use strict";
    angular.module('Tek.progressBar').directive('tekProgressBar', function () {
        return {
            scope: {
                manager: "=",
                containerClass: "@class",
                barClass: "@",
                ngModel: "=",
                mode: '@'
            },
            restrict: "E",
            transclude: true,
            controllerAs: "bar",
            templateUrl: "Tek.progressBarDirective.html",
            bindToController: true,
            controller: ['$scope', '$element', function ($scope, $element) {
                var bar = this;

                var settings = {
                    fullClass: 'full-bar',
                    emptyClass: 'empty-bar',
                    verticalClass: 'vertical',
                    horizontalClass: ''
                };

                function ProgressObj(element, mode) {
                    var divElements = element.find('div');
                    this.mode = mode;
                    this.containerElement = angular.element(divElements[0]);
                    this.containerElement.addClass(settings[mode + 'Class']);
                    this.barContainer = angular.element(divElements[1]);
                    this.value = 0;
                }

                ProgressObj.prototype.get = function () {
                    return this.value;
                };

                ProgressObj.prototype.set = function (val) {
                    this.value = val;
                    if(this.mode === 'horizontal'){
                        this.barContainer.css('width', val + '%');
                    }
                    if(this.mode === 'vertical'){
                        this.barContainer.css('height', val + '%');
                    }
                    this.updateClasses();
                };

                ProgressObj.prototype.updateClasses = function () {
                    if (this.value <= 0) {
                        this.containerElement.removeClass(settings.fullClass);
                        return this.containerElement.addClass(settings.emptyClass);
                    }

                    if (this.value >= 100) {
                        this.containerElement.removeClass(settings.emptyClass);
                        return this.containerElement.addClass(settings.fullClass);
                    }

                    this.containerElement.removeClass(settings.fullClass);
                    this.containerElement.removeClass(settings.emptyClass);
                };

                ProgressObj.prototype.setAnimation = function (val) {
                    if(val === true){
                        this.barContainer.css('transition', '');
                    }else{
                        this.barContainer.css('transition', 'none');
                    }
                };

                bar.init = function () {
                    bar.mode = (bar.mode === 'vertical') ? bar.mode : 'horizontal';
                    bar.progressObj = new ProgressObj($element, bar.mode);
                    var facade = {
                        get: function () {
                            return bar.progressObj.get();
                        },
                        set: function (newVal) {
                            if (bar.ngModel !== undefined) { // todo setInterval problem
                                $scope.$evalAsync(function () {
                                    bar.ngModel = newVal;
                                });
                            } else {
                                bar.progressObj.set(newVal);
                            }
                        },
                        setAnimation: function (val) {
                            bar.progressObj.setAnimation(val);
                        }
                    };

                    if (bar.manager) {
                        bar.manager._getDefer().resolve(facade);
                        $scope.$on('$destroy', function () {
                            bar.manager._updateDefer();
                        });
                    }

                    if (bar.ngModel !== undefined) {
                        $scope.$watch('bar.ngModel', function (newVal) {
                            if(typeof newVal !== 'number' || newVal < 0 || newVal !== newVal){
                                newVal = 0;
                            }

                            if(newVal > 100){
                                newVal = 100;
                            }

                            if (bar.manager) {
                                bar.manager._updateValue(newVal);
                            }
                            bar.progressObj.set(newVal);
                        });
                    }
                };
                bar.init();
            }]
        }
    });
}());