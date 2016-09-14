var config = {
  appURL: 'http://localhost:9000/#/component-2/dialog-b',
  selectClassName: '.form-control',
  tableClassName: '.table',
  ngRepeatSelect: 'item in data.books',
  ngRepeatTable: 'item in data.books'
};

function DialogBPage() {

};

DialogBPage.prototype.open = function() {
  browser.get(config.appURL);
};

DialogBPage.prototype.getSelectedOptionText = function() {
  return element(by.css(config.selectClassName)).$('option:checked').getText();
};

DialogBPage.prototype.isTableVisible = function() {
  return element(by.css(config.tableClassName)).isDisplayed();
};

DialogBPage.prototype.clickDropdownListBySpecificString = function(stringValue) {
  if (stringValue) {
    element(by.cssContainingText('option', stringValue)).click();
  }
};

module.exports = DialogBPage;
