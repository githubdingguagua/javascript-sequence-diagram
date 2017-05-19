QUnit.test("showSelectedSearchResult delegates to screenshotSearchResultHighlighter if it handle it", function( assert ) {

    var screenshotSearchResultHighlighter = {
        handle: function (configuration){},
        showSelectedSearchResult: function (configuration){},
        deselectOldSelection: function (){}
    }
    var screenshotSearchResultHighlighterMock = sinon.mock(screenshotSearchResultHighlighter);

    var bodySearchResultHighlighter = {
      deselectOldSelection: function (){}
    }

    var aConfiguration = { someFunction: function (someObject){} }

    sinon.stub(screenshotSearchResultHighlighter, "handle").withArgs(aConfiguration).returns(true);

    screenshotSearchResultHighlighterMock.expects("showSelectedSearchResult").withArgs(aConfiguration)

    new SearchResultHighlighter(screenshotSearchResultHighlighter, bodySearchResultHighlighter).showSelectedSearchResult(aConfiguration)

    screenshotSearchResultHighlighterMock.verify()

});

QUnit.test("showSelectedSearchResult delegates to bodySearchResultHighlighter if it handle it", function( assert ) {

    var screenshotSearchResultHighlighter = {
            handle: function (configuration){},
            showSelectedSearchResult: function (configuration){},
        deselectOldSelection: function (){}
    }

    sinon.stub(screenshotSearchResultHighlighter, "handle").withArgs(aConfiguration).returns(false);

    var bodySearchResultHighlighter = {
        handle: function (configuration){},
        showSelectedSearchResult: function (configuration){}
    }
    var bodySearchResultHighlighterMock = sinon.mock(bodySearchResultHighlighter);

    var aConfiguration = { someFunction: function (someObject){} }

    sinon.stub(bodySearchResultHighlighter, "handle").withArgs(aConfiguration).returns(true);

    bodySearchResultHighlighterMock.expects("showSelectedSearchResult").withArgs(aConfiguration)

    new SearchResultHighlighter(screenshotSearchResultHighlighter, bodySearchResultHighlighter).showSelectedSearchResult(aConfiguration)

    bodySearchResultHighlighterMock.verify()

});
