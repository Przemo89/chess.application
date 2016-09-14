var DialogBPage = require('./po/dialog.b.page.js');

describe('dialog b page e2e test suite', function() {
  'use strict';

  var dialogBPage;

  beforeEach(function() {
    dialogBPage = new DialogBPage();
  });

  it('should be selected option "Plaese select" by default', function() {
    // given
    var properString = 'Please select';

    // when
    dialogBPage.open();

    // then
    expect(dialogBPage.getSelectedOptionText()).toEqual(properString);
  });

  it('should table be invisible when opening dialog', function() {
    // given
    var properResult = false;

    // when
    dialogBPage.open();

    // then
    expect(dialogBPage.isTableVisible()).toBe(properResult);
  });

  it('should table be invisible if selected value from dropdown list is default one', function() {
    // given
    var properResult, defaultSelectValue, genre;
    properResult = false;
    defaultSelectValue = 'Please select';
    genre = 'fantasy';

    // when
    dialogBPage.open();

    // then
    expect(dialogBPage.isTableVisible()).toBe(properResult);
    dialogBPage.clickDropdownListBySpecificString(genre);
    dialogBPage.clickDropdownListBySpecificString(defaultSelectValue);
    expect(dialogBPage.isTableVisible()).toBe(properResult);
  });

  it('should table be visible after selecting any genre', function() {
    // given
    var properResult, genreOne, genreTwo, genreThree;
    properResult = true;
    genreOne = 'it';
    genreTwo = 'fantasy';
    genreThree = 'criminal';

    // when
    dialogBPage.open();

    // then
    dialogBPage.clickDropdownListBySpecificString(genreOne);
    expect(dialogBPage.isTableVisible()).toBe(true);
    dialogBPage.clickDropdownListBySpecificString(genreTwo);
    expect(dialogBPage.isTableVisible()).toBe(true);
    dialogBPage.clickDropdownListBySpecificString(genreThree);
    expect(dialogBPage.isTableVisible()).toBe(true);
  });

});
