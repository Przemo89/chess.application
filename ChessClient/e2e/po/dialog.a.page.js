var config = {
  appURL: 'http://localhost:9000/#/component-1/dialog-a',
  addBookButtonNameInDialog: 'addBookButton',
  editBookButtonNameInDialog: 'editBookButton',
  ngRepeatTable: 'item in data.books',
  addBookModelTitle: 'data.newBook.title',
  addBookModelAuthor: 'data.newBook.author',
  addBookModelGenre: 'data.newBook.genre',
  addBookButtonNameInModal: 'addBookButtonInModal',
  editBookButtonNameInModal: 'editBookButtonInModal',
  editBookModelTitle: 'data.selectedBook.title',
  editBookModelAuthor: 'data.selectedBook.author',
  editBookModelGenre: 'data.selectedBook.genre'
};

function DialogAPage() {

};

DialogAPage.prototype.open = function() {
  browser.get(config.appURL);
};

DialogAPage.prototype.clickAddBookButtonInDialog = function() {
  element(by.name(config.addBookButtonNameInDialog)).click();
};

DialogAPage.prototype.clickEditBookButtonInDialog = function() {
  element(by.name(config.editBookButtonNameInDialog)).click();
};

DialogAPage.prototype.clickRowInTable = function(rowNumber) {
  element.all(by.repeater(config.ngRepeatTable)).get(rowNumber).click();
};

DialogAPage.prototype.addBookInModal = function(title, author, genre) {
  element(by.model(config.addBookModelTitle)).sendKeys(title);
  element(by.model(config.addBookModelAuthor)).sendKeys(author);
  element(by.model(config.addBookModelGenre)).sendKeys(genre);
};

DialogAPage.prototype.clearTitleInputFieldInAddBookModal = function() {
  element(by.model(config.addBookModelTitle)).clear();
};

DialogAPage.prototype.clickAddBookButtonInModal = function() {
  element(by.name(config.addBookButtonNameInModal)).click();
};

DialogAPage.prototype.editBookInModal = function(title, author, genre) {
  element(by.model(config.editBookModelTitle)).clear().sendKeys(title);
  element(by.model(config.editBookModelAuthor)).clear().sendKeys(author);
  element(by.model(config.editBookModelGenre)).clear().sendKeys(genre);
};

DialogAPage.prototype.clearTitleInputFieldInEditBookModal = function() {
  element(by.model(config.editBookModelTitle)).clear();
};

DialogAPage.prototype.clearAllInputFieldsInEditBookModal = function() {
  element(by.model(config.editBookModelTitle)).clear();
  element(by.model(config.editBookModelAuthor)).clear();
  element(by.model(config.editBookModelGenre)).clear();
};

DialogAPage.prototype.clickEditBookButtonInModal = function() {
  element(by.name(config.editBookButtonNameInModal)).click();
};

DialogAPage.prototype.isAddBookButtonClickableInModal = function() {
  return element(by.name(config.addBookButtonNameInModal)).isEnabled();
};

DialogAPage.prototype.isEditBookButtonClickableInModal = function() {
  return element(by.name(config.editBookButtonNameInModal)).isEnabled();
};

module.exports = DialogAPage;
