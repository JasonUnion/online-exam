(function () {
    "use strict";
    angular.module('test', ['Tek.progressBar']).controller('mainController', ['progressBarManager',
        function (progressBarManager) {
            var main = this;

            main.random = function () {
                return Math.floor(Math.random() * 100);
            };

            main.bar = progressBarManager();
            main.valBar = 0;
            main.isBar = true;
            main.bar1ProgressVal = 0;
            main.bar1 = progressBarManager();

            main.bar2ProgressVal = 0;
            main.bar2 = progressBarManager();
            main.rightBar = progressBarManager();
            main.vertical = progressBarManager();
            main.verticalTop = progressBarManager();
        }
    ]);
}());