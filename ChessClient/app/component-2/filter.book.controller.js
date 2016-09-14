angular.module('app.component2').controller('FilterBookController', function ($scope, BookService) {
  'use strict';

  $scope.selectedRow = null;
  $scope.isSelected = false;

  $scope.data = {
    form: {},
    books: [],
    genres: [],
    selectedGenre: ''
  };

   $scope.data.books = BookService.getBooks();
   $scope.data.genres = BookService.getGenres();

   $scope.isSelectedGenre = function() {
     if (!$scope.data.selectedGenre) {
       $scope.isSelected = false;
       return;
     }
     $scope.isSelected = true;
     $scope.filter();
   };

   $scope.filter = function() {
     $scope.data.books = BookService.getBooksByGenre($scope.data.selectedGenre);
   };

});
