QUnit.test("handle recognised when configuration is a screenshot configuration", function( assert ) {

    var configuration = {
      object: {
        searchResult: {
          thumbnailsContainerId:'scenario-2-thumbnail',
          screenshotImageId:'scenario-2-thumbnail-3'
        }
      }
    }

    assert.equal(new ScreenshotSearchResultHighlighter().handle(configuration), true);

});

QUnit.test("handle recognised when configuration is not a screenshot configuration", function( assert ) {

   assert.equal(new ScreenshotSearchResultHighlighter().handle({object:{searchResult:{}}}), false);

   assert.equal(new ScreenshotSearchResultHighlighter().handle({}), false);

});
