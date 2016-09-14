describe('AddBookController initialization', function () {
  'use strict'

  var $scope, bookServiceMock, modalInstanceMock, addBookController;

  bookServiceMock = {
    getBooks: function() {
      var books = [{
              "id": 1,
              "version": 0,
              "genre": "it",
              "year": 1999,
              "title": "Code Complete",
              "author": "Steve McConnell"
          }];
      return books;
    },
    createBook: function() {
      var newBook, currentDate;
      currentDate = new Date();
      newBook = {
        version: 0,
        genre: '',
        year: currentDate.getFullYear(),
        title: '',
        author: ''
      }
      return newBook;
    },
    addBook: function () {
      // empty mock function
    }
  };

  beforeEach(function() {
    module('app.component1');
    module(function($provide) {
      $provide.value('BookService', bookServiceMock);
    });
  });

  beforeEach(inject(function($controller, $rootScope) {
    $scope = $rootScope.$new();
    modalInstanceMock = {
      close: jasmine.createSpy('modalInstance.close'),
      dismiss: jasmine.createSpy('modalInstance.dismiss')
    };

    addBookController = $controller('AddBookController', {$scope: $scope, $modalInstance: modalInstanceMock, BookService: bookServiceMock});
  }));

  describe('AddBookController tests', function() {

    it('should instantiate the controller properly', function () {
         expect(addBookController).not.toBeUndefined();
     });

    it('should close modal with status "ok"', function(){
         // given
        var properResponseStatus = 'ok';

         // when
         $scope.save();

         // then
         expect(modalInstanceMock.close).toHaveBeenCalledWith(properResponseStatus);
    });

    it('should dismiss modal with status "cancel"', function(){
         // given
        var properResponseStatus = 'cancel';

         // when
         $scope.cancel();

         // then
         expect(modalInstanceMock.dismiss).toHaveBeenCalledWith(properResponseStatus);
    });
  });
});
