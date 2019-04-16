'use strict';

angular.
module('addStudent').
component('addStudent', {
  templateUrl: 'components/addStudent/addStudent.template.html',
  controller: function addStudentController($scope, $http, $uibModal) {

    $scope.save = function() {
        alert("ddd");
    };

    var $ctrl = this;
  	$ctrl.items = ['item1', 'item2', 'item3'];
  	$ctrl.animationsEnabled = true;

  $scope.openComponentModal = function () {
    var modalInstance = $uibModal.open({
      animation: $ctrl.animationsEnabled,
      component: 'modalComponent',
      resolve: {
        items: function () {
          return $ctrl.items;
        }
      }
    });

    modalInstance.result.then(function (selectedItem) {
      $ctrl.selected = selectedItem;
    }, function () {
      $log.info('modal-component dismissed at: ' + new Date());
      //alert("ddddd");
    });
  };

  }
});

/*angular.module('addStudent')
.controller('addStudentModalCtrl', function ($uibModal, $log, $document) {

	var $ctrl = this;
  	$ctrl.items = ['item1', 'item2', 'item3'];
  	$ctrl.animationsEnabled = true;

  $ctrl.openComponentModal = function () {
    var modalInstance = $uibModal.open({
      animation: $ctrl.animationsEnabled,
      component: 'modalComponent',
      resolve: {
        items: function () {
          return $ctrl.items;
        }
      }
    });

    modalInstance.result.then(function (selectedItem) {
      $ctrl.selected = selectedItem;
    }, function () {
      $log.info('modal-component dismissed at: ' + new Date());
      //alert("ddddd");
    });
  };


});*/

/*angular.module('addStudent').controller('ModalInstanceCtrl', function ($uibModalInstance, items) {
  var $ctrl = this;
  $ctrl.items = items;
  $ctrl.selected = {
    item: $ctrl.items[0]
  };

  $ctrl.ok = function () {
    $uibModalInstance.close($ctrl.selected.item);
  };

  $ctrl.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});*/

angular.module('addStudent').component('modalComponent', {
  templateUrl: 'components/addStudent/addStudentModal.template.html',
  bindings: {
    resolve: '<',
    close: '&',
    dismiss: '&'
  },
  controller: function () {
    var $ctrl = this;

    $ctrl.$onInit = function () {
      $ctrl.items = $ctrl.resolve.items;
      $ctrl.selected = {
        item: $ctrl.items[0]
      };
    };

    $ctrl.ok = function () {
      $ctrl.close({$value: $ctrl.selected.item});
    };

    $ctrl.cancel = function () {
      $ctrl.dismiss({$value: 'cancel'});
    };
  }
});