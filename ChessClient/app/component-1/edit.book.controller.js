angular.module('app.component1').controller('EditBookController', function($scope, $modalInstance, BookService, idSelectedBook){
    'use strict';

    // $scope.selectedBook = selectedBook;
    $scope.maxDate = new Date();
    $scope.data = {
      selectedBook: BookService.copyBook(idSelectedBook)
    };

    $scope.update = function () {
      BookService.updateBook($scope.data.selectedBook.id, $scope.data.selectedBook.version, $scope.data.selectedBook.genre,
        $scope.getSelectedDataYear(), $scope.data.selectedBook.title, $scope.data.selectedBook.author);
      $modalInstance.close('ok');
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.dt = new Date($scope.data.selectedBook.year + '-01-01');

    $scope.getSelectedDataYear = function() {
      return $scope.dt.getFullYear();
    };

});
