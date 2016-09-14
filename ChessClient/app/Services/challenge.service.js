angular.module('app.component1').factory('ChallengeService', ['$http', function ($http) {
  'use strict';

  var setId, currentDate, setSentChallenges, data;

  setId = function () {
      return books.length + 1;
  };

  currentDate = new Date();

  return {
    getSentChallenges: function(idPlayer) {
      var url = 'http://localhost:8090/chess/challenge/sentChallenges/' + idPlayer;
      return $http.get(url, {cache: true});
    },

    copyBook: function(id) {
      var copiedBook = {
        id: books[id-1].id,
        version: books[id-1].version,
        genre: books[id-1].genre,
        year: books[id-1].year,
        title: books[id-1].title,
        author: books[id-1].author
      };
      return copiedBook;
    },

    addBook: function(version, genre, year, title, author) {
      var bookToAdd = {
        id: setId(),
        version: version,
        genre: genre,
        year: year,
        title: title,
        author: author
      };
      books.push(bookToAdd);
      return $http({
        method: "POST",
        url: "app/index.html"
      }).then(function successCallback(response) {
        console.log("Success");
      }, function errorCallback(response) {
        console.log("Error");
        return response.data;
      });
    },

    updateBook: function(id, version, genre, year, title, author) {
      books[id-1].version = version;
      books[id-1].genre = genre;
      books[id-1].year = year;
      books[id-1].title = title;
      books[id-1].author = author;
      return $http({
        method: "PUT",
        url: "app/index.html"
      }).then(function successCallback(response) {
        console.log("Success");
      }, function errorCallback(response) {
        console.log("Error");
        return response.data;
      });
    },

    // new book id will be set just before adding to table books
    createBook: function() {
      var newBook = {
        version: 0,
        genre: '',
        year: currentDate.getFullYear(),
        title: '',
        author: ''
      }
      return newBook;
    },

    getGenres: function() {
      var genres, i;
      genres = [];
      for (i = 0; i < books.length; i++) {
        if (!genres.contains(books[i].genre)) {
          genres.push(books[i].genre);
        }
      };
      return genres;
    },

    getBooksByGenre: function(genre) {
      var booksByGenre, i;
      booksByGenre = [];
      if (!genre) {
        return books;
      };
      for (i = 0; i < books.length; i++) {
        if (books[i].genre === genre) {
          booksByGenre.push(books[i]);
        }
      };
      return booksByGenre;
    }
  }
}]);
