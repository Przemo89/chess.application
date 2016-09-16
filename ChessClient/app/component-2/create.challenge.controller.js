angular.module('app.component2').controller('CreateChallengeController', function ($scope, ChallengeService, SharedPlayerProfileService) {
  'use strict';

  var setPlayerProfileAndStatistics;

  $scope.data = {
      playerProfileAndStatistics: []
  };

  setPlayerProfileAndStatistics = function() {
    $scope.data.playerProfileAndStatistics = SharedPlayerProfileService.getPlayerProfileAndStatistics();
  }();

  // $scope.selectedRow = null;
  // $scope.isSelected = false;
  //
  // $scope.data = {
  //   form: {},
  //   books: [],
  //   genres: [],
  //   selectedGenre: ''
  // };
  //
  //  $scope.data.books = BookService.getBooks();
  //  $scope.data.genres = BookService.getGenres();
  //
  //  $scope.isSelectedGenre = function() {
  //    if (!$scope.data.selectedGenre) {
  //      $scope.isSelected = false;
  //      return;
  //    }
  //    $scope.isSelected = true;
  //    $scope.filter();
  //  };
  //
  //  $scope.filter = function() {
  //    $scope.data.books = BookService.getBooksByGenre($scope.data.selectedGenre);
  //  };

});
