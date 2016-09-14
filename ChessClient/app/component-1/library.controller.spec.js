describe('LibraryController initialization', function () {
  'use strict'

  var $scope, BookServiceMock, modal, libraryController;

  BookServiceMock = {
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
    }
  };

  beforeEach(function() {
    module('app.component1');
    module(function($provide) {
      $provide.value('BookService', BookServiceMock);
    });
  });

  beforeEach(inject(function($controller, $rootScope) {
    $scope = $rootScope.$new();
    modal = {
      open: jasmine.createSpy('modal.open')
    };
    libraryController = $controller('LibraryController', {$scope: $scope, $modal: modal, BookService: BookServiceMock});
  }));

  describe('DialogAController tests', function() {

    it('should instantiate the controller properly', function () {
         expect(libraryController).not.toBeUndefined();
     });

    it('should open edit modal with provided options', function(){
         // given
        var modalOptions = {
          templateUrl: "/component-1/modal-dialog/edit-book-modal-dialog.tpl.html",
          controller: "EditBookController",
          resolve: {
            idSelectedBook: jasmine.any(Function)
            }
          };

         // when
         $scope.edit();

         // then
         expect(modal.open).toHaveBeenCalledWith(modalOptions);
    });

    it('should open add modal with provided options', function(){
         // given
        var modalOptions = {
          templateUrl: "/component-1/modal-dialog/add-book-modal-dialog.tpl.html",
          controller: "AddBookController",
          resolve: {
            books: jasmine.any(Function)
            }
          };

         // when
         $scope.add();

         // then
         expect(modal.open).toHaveBeenCalledWith(modalOptions);
    });

  });
});
