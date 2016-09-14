describe('BookService initialization', function () {
  'use strict';

  var bookService;

  beforeEach(module('app.component1'));
  beforeEach(inject(function($http, BookService) {
    // bookService = $service('BookService', {$http: $http});
    // $http = $http;
    bookService = BookService;
  }));

  describe('BookService tests', function() {
    // given when then
    it('should instantiate BookService properly', function () {
      expect(bookService).not.toBeUndefined();
    });

    it('should return true because array contains item', function() {
      // given
      var someArray, numberInArrayExisting, result;
      someArray = [1, 2];
      numberInArrayExisting = 1;

      // when
      result = someArray.contains(numberInArrayExisting);

      // then
      expect(result).toBe(true);
    });

    it('should return false because array does not contain item', function() {
      // given
      var someArray, numberInArrayNotExisting, result;
      someArray = [1, 2];
      numberInArrayNotExisting = 5;

      // when
      result = someArray.contains(numberInArrayNotExisting);

      // then
      expect(result).toBe(false);
    });

    it('should return array with 10 elements', function() {
      // given
      var properResult, result;
      properResult = 10;

      // when
      result = bookService.getBooks().length;

      // then
      expect(result).toBe(properResult);
    });

    it('should return exact copy of book', function() {
      // given
      var idBook, allBooks, result;
      idBook = 1;
      allBooks = bookService.getBooks();

      // when
      result = bookService.copyBook(idBook);

      // then
      expect(result.id).toBe(allBooks[idBook-1].id);
      expect(result.version).toBe(allBooks[idBook-1].version);
      expect(result.title).toBe(allBooks[idBook-1].title);
      expect(result.author).toBe(allBooks[idBook-1].author);
      expect(result.year).toBe(allBooks[idBook-1].year);
      expect(result.genre).toBe(allBooks[idBook-1].genre);
    });

    it('should add new book', function() {
      // given
      var idBookNew, version, genre, year, title, author, allBooksAfterBookAddition, addedBook, properArrayLength;
      idBookNew = 11;
      version = 0;
      genre = 'it';
      year = '2004';
      title = 'Dont care';
      author = 'Whateva';
      properArrayLength = 11;

      // when
      bookService.addBook(version, genre, year, title, author);
      allBooksAfterBookAddition = bookService.getBooks();
      addedBook = allBooksAfterBookAddition[idBookNew-1];

      // then
      expect(addedBook.id).toBe(idBookNew);
      expect(addedBook.version).toBe(version);
      expect(addedBook.title).toBe(title);
      expect(addedBook.author).toBe(author);
      expect(addedBook.year).toBe(year);
      expect(addedBook.genre).toBe(genre);
      expect(allBooksAfterBookAddition.length).toBe(properArrayLength);
    });

    it('should update existing book', function() {
      // given
      var idBookExisting, version, genre, year, title, author, allBooks, updatedBook;
      idBookExisting = 1;
      version = 5;
      genre = 'thriller';
      year = '1980';
      title = 'Dont care';
      author = 'Whateva';

      // when
      bookService.updateBook(idBookExisting, version, genre, year, title, author);
      allBooks = bookService.getBooks();
      updatedBook = allBooks[idBookExisting-1];

      // then
      expect(updatedBook.id).toBe(idBookExisting);
      expect(updatedBook.version).toBe(version);
      expect(updatedBook.title).toBe(title);
      expect(updatedBook.author).toBe(author);
      expect(updatedBook.year).toBe(year);
      expect(updatedBook.genre).toBe(genre);
    });

    it('should create new empty book template', function() {
      // given
      var version, genre, year, title, author, currentDate, newBook;
      currentDate = new Date();
      version = 0;
      genre = '';
      year = currentDate.getFullYear();
      title = '';
      author = '';

      // when
      newBook = bookService.createBook();

      // then
      expect(newBook.version).toBe(version);
      expect(newBook.title).toBe(title);
      expect(newBook.author).toBe(author);
      expect(newBook.year).toBe(year);
      expect(newBook.genre).toBe(genre);
    });

    it('should return array with three genres', function() {
      // given
      var properGenres, resultGenres;
      properGenres = ['it', 'fantasy', 'criminal'];

      // when
      resultGenres = bookService.getGenres();

      // then
      expect(resultGenres.contains(properGenres[0])).toBe(true);
      expect(resultGenres.contains(properGenres[1])).toBe(true);
      expect(resultGenres.contains(properGenres[2])).toBe(true);
      expect(resultGenres.length).toBe(properGenres.length);
    });

    it('should return array containing books with specified genre', function() {
      // given
      var genre, properBooks, resultBooks;
      genre = 'fantasy';
      properBooks = [{
          "id": 6,
          "version": 0,
          "genre": "fantasy",
          "year": 2002,
          "title": "Wezmisz czarna kure",
          "author": "Andrzej Pilipiuk"
      },
      {
          "id": 7,
          "version": 0,
          "genre": "fantasy",
          "year": 2003,
          "title": "Kroniki Jakuba Wedrowycza",
          "author": "Andrzej Pilipiuk"
      }];

      // when
      resultBooks = bookService.getBooksByGenre(genre);

      // then
      expect(resultBooks.length).toBe(properBooks.length);
    });

  });
});
