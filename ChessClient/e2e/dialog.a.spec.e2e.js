var DialogAPage = require('./po/dialog.a.page.js');

describe('dialog a page tests', function () {
  'use strict';

  var dialogAPage;

  beforeEach(function() {
    dialogAPage = new DialogAPage();
  });

  it('should edit book button be hidden while no row is selected in table and visible if some row is selected', function() {
      // given
      var numberOfRow, editBookButtonName;
      numberOfRow = 1;
      editBookButtonName = 'editBookButton';

      // when
      dialogAPage.open();

      // then
      expect(element(by.name(editBookButtonName)).isDisplayed()).toBe(false);
      dialogAPage.clickRowInTable(numberOfRow);
      expect(element(by.name(editBookButtonName)).isDisplayed()).toBe(true);
  });

  it('should add book button in modal not being clickable if all inputs are empty', function() {
    // given when
    dialogAPage.open();
    dialogAPage.clickAddBookButtonInDialog();

    // then
    expect(dialogAPage.isAddBookButtonClickableInModal()).toBe(false);
  });

  it('should add book button in modal not being clickable if at least one input is empty', function() {
    // given
    var title, author, genre;
    title = 'Whatever Title';
    author = 'Dont care';
    genre = 'Thriller';

    // when
    dialogAPage.open();
    dialogAPage.clickAddBookButtonInDialog();
    dialogAPage.addBookInModal(title, author, genre);
    dialogAPage.clearTitleInputFieldInAddBookModal();

    // then
    expect(dialogAPage.isAddBookButtonClickableInModal()).toBe(false);
  });

  it('should update book button in modal not being clickable if all inputs are empty', function() {
    // given
    var numberOfRow = 2;

    // when
    dialogAPage.open();
    dialogAPage.clickRowInTable(numberOfRow);
    dialogAPage.clickEditBookButtonInDialog();
    dialogAPage.clearAllInputFieldsInEditBookModal();

    // then
    expect(dialogAPage.isEditBookButtonClickableInModal()).toBe(false);
  });

  it('should update book button in modal not being clickable if at least one input is empty', function() {
    // given
    var numberOfRow = 6;

    // when
    dialogAPage.open();
    dialogAPage.clickRowInTable(numberOfRow);
    dialogAPage.clickEditBookButtonInDialog();
    dialogAPage.clearTitleInputFieldInEditBookModal();

    // then
    expect(dialogAPage.isEditBookButtonClickableInModal()).toBe(false);
  });

  it('should add book', function() {
      // given
      var title, author, genre, countBeforeBookAddition, countAfterBookAddition, list;
      title = 'Whatever Title';
      author = 'Dont care';
      genre = 'Thriller';

      // when
      dialogAPage.open();
      list = element.all(by.repeater('item in data.books'));
      list.count().then(function(amount) {
        dialogAPage.clickAddBookButtonInDialog();
        dialogAPage.addBookInModal(title, author, genre);
        dialogAPage.clickAddBookButtonInModal();

      // then
        countAfterBookAddition = element.all(by.repeater('item in data.books')).count();
        expect(countAfterBookAddition).toEqual(amount + 1);

        element.all(by.repeater('item in data.books')).get(amount).all(by.tagName('td')).getText().then(function(titleAndAuthorStringTable) {
          expect(titleAndAuthorStringTable[0]).toEqual(title);
          expect(titleAndAuthorStringTable[1]).toEqual(author);
        });
      });
  });

  it('should edit book', function() {
    // given
    var titleNew, authorNew, genreNew, numberOfModifiedRow, titleAuthorGenreTableOriginal, titleAuthorGenreTableModified;
    titleNew = 'Completely random title';
    authorNew = 'Random guy';
    genreNew = 'who cares';
    numberOfModifiedRow = 5;
    // when
    dialogAPage.open();
    dialogAPage.clickRowInTable(numberOfModifiedRow);
    element.all(by.repeater('item in data.books')).get(numberOfModifiedRow).all(by.tagName('td')).getText().then(function(titleAndAuthorStringTable) {
      titleAuthorGenreTableOriginal = titleAndAuthorStringTable;
    });
    dialogAPage.clickEditBookButtonInDialog();
    dialogAPage.editBookInModal(titleNew, authorNew, genreNew);
    dialogAPage.clickEditBookButtonInModal();

    // then
    element.all(by.repeater('item in data.books')).get(numberOfModifiedRow).all(by.tagName('td')).getText().then(function(titleAndAuthorStringTable) {
      titleAuthorGenreTableModified = titleAndAuthorStringTable;
      expect(titleAuthorGenreTableModified[0]).toEqual(titleNew);
      expect(titleAuthorGenreTableModified[1]).toEqual(authorNew);
    });
  });

});
