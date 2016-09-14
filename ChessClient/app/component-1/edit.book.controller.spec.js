describe('EditBookController initialization', function () {
  'use strict'

  var $scope, bookServiceMock, modalInstanceMock, editBookController, idSelectedBookMock;

  idSelectedBookMock = 1;

  bookServiceMock = {
    copyBook: function() {
      var book = {
              "id": 1,
              "version": 0,
              "genre": "it",
              "year": 1999,
              "title": "Code Complete",
              "author": "Steve McConnell"
          };
      return book;
    },
    updateBook: function () {
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

    editBookController = $controller('EditBookController', {$scope: $scope, $modalInstance: modalInstanceMock, BookService: bookServiceMock, idSelectedBook: idSelectedBookMock});
  }));

  describe('EditBookController tests', function() {

    it('should instantiate the controller properly', function () {
         expect(editBookController).not.toBeUndefined();
     });

    it('should close modal with status "ok"', function(){
         // given
        var properResponseStatus = 'ok';

         // when
         $scope.update();

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
