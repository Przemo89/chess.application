angular.module('app.component1').controller('AddBookController', function($scope, $modalInstance, BookService){
  'use strict';

  $scope.books = BookService.getBooks();
  $scope.dt = new Date();
  $scope.currentYear = new Date();

  $scope.data = {
    newBook: BookService.createBook()
  };

  $scope.getSelectedDataYear = function() {
    return $scope.dt.getFullYear();
  };

  $scope.save = function () {
    BookService.addBook($scope.data.newBook.version, $scope.data.newBook.genre, $scope.dt, $scope.data.newBook.title, $scope.data.newBook.author);
    $modalInstance.close('ok');
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };

});
