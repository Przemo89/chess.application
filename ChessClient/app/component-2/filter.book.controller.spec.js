describe('FilterBookController initialization', function () {
  'use strict'

  var $scope, bookServiceMock, filterBookController, books;

  books = [{
          "id": 1,
          "version": 0,
          "genre": "it",
          "year": 1999,
          "title": "Code Complete",
          "author": "Steve McConnell"
      }];

  bookServiceMock = {
    getBooks: function() {
      return books;
    },
    getGenres: function() {
      var genres = ['it'];
      return genres;
    },
    getBooksByGenre: function () {
      return books;
    }
  };

  beforeEach(function() {
    module('app.component2');
    module(function($provide) {
      $provide.value('BookService', bookServiceMock);
    });
  });

  beforeEach(inject(function($controller, $rootScope) {
    $scope = $rootScope.$new();
    filterBookController = $controller('FilterBookController', {$scope: $scope, BookService: bookServiceMock});
  }));

  describe('FilterBookController tests', function() {

    it('should instantiate the controller properly', function () {
      // given when then
      expect(filterBookController).not.toBeUndefined();
     });

    it('should $scope.isSelected be false', function() {
      // given
      var properResult = false;
      $scope.data.selectedGenre = '';

      // when
      $scope.isSelectedGenre();

      // then
      expect($scope.isSelected).toBe(properResult);
    });

    it('should be used BookService.getBookByGenre() function', function() {
      // given
      var genreExisting = 'it';
      spyOn(bookServiceMock, "getBooksByGenre");
      $scope.data.selectedGenre = genreExisting;

      // when
      $scope.isSelectedGenre();

      // then
      expect(bookServiceMock.getBooksByGenre).toHaveBeenCalledWith(genreExisting);
    })
  });
});
