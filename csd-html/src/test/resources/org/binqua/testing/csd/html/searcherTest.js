QUnit.test("search delegates to all the indexers and returns sorted results", function( assert ) {

    var smartBodyIndexer = {
        search: function (wordToBeFound){}
    }

    var screenshotIndexer = {
        search: function (wordToBeFound){}
    }

    var searchResultSorter = {
        sort: function (toBeSorted){}
    }

    var theSentenceToBeFound = "the sentence To Be Found"

    var resultFromBodySearch = [{id:1}]

    sinon.stub(smartBodyIndexer, "search").withArgs(theSentenceToBeFound).returns(resultFromBodySearch);

    var resultFromScreenshotSearch = [{id:2}]

    sinon.stub(screenshotIndexer, "search").withArgs(theSentenceToBeFound).returns(resultFromScreenshotSearch);

    var resultFromSorting = [{id:3}]

    sinon.stub(searchResultSorter, "sort").withArgs([{id:1}, {id:2}]).returns(resultFromSorting);

    var underTest = new Searcher(smartBodyIndexer, screenshotIndexer, searchResultSorter);

    assert.strictEqual(underTest.search(theSentenceToBeFound), resultFromSorting)

});

QUnit.test("init initialised all the indexers", function( assert ) {

    var smartBodyIndexer = {
       init: function (){}
    }
    var smartBodyIndexerMock = sinon.mock(smartBodyIndexer);

    var screenshotIndexer = {
       init: function (){}
    }
    var screenshotIndexerMock = sinon.mock(screenshotIndexer);

    var searchResultSorter = {
        sort: function (toBeSorted){}
    }

    var smartBodyIndexerExpectation = smartBodyIndexerMock.expects("init");

    var screenshotIndexerMockExpectation = screenshotIndexerMock.expects("init");

    var underTest = new Searcher(smartBodyIndexer, screenshotIndexer, searchResultSorter);

    underTest.init()

    smartBodyIndexerMock.verify()

    screenshotIndexerMock.verify()

});

