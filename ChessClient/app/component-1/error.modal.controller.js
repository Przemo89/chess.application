angular.module('app.component1').controller('ErrorModalController', function($scope, $modalInstance, provideResponse) {
    'use strict';

    var isStatusText;

    $scope.close = function() {
      $modalInstance.close($scope.data);
    };

    $scope.data = {
        boldTextTitle: 'Error!',
        textAlert: 'Server temporairly unavailable. Please try later',
        response: provideResponse,
        mode: 'danger'
    };

    isStatusText = function() {
      if ($scope.data.response.data !== null) {
        $scope.data.textAlert = $scope.data.response.data.message;
        return;
      }
      $scope.data.textAlert = 'Server temporairly unavailable. Please try later';
    };

    isStatusText();
});
