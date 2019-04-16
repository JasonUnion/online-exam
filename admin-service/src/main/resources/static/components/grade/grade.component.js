'use strict';

angular.
module('grade').
component('grade', {
  templateUrl: 'components/grade/grade.template.html',
  controller: function GradeController($scope, $http) {
  	$scope.name = "";
  	$scope.save = function() {
  		var req = {
                method: 'POST',
                url: '/auth/api/grades',
                headers: {
                    'Content-Type': "application/json"
                },
                data: {
                    "name": $scope.name
                }
            };

            $http(req).then(function() {
                //$scope.qty = 0;
                function showAlert() {
                    $("#addGradeAlert").addClass("in");
                }

                function hideAlert() {
                    $("#addGradeAlert").removeClass("in");
                }

                window.setTimeout(function() {
                    showAlert();
                    window.setTimeout(function() {
                        hideAlert();
                    }, 2000);
                }, 20);
                window.location.href = "#!/";
            });
  	}
  }
});