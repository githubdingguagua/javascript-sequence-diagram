function Searcher(smartBodyIndexer, screenshotIndexer, searchResultSorter) {

    this.init = function () {
        smartBodyIndexer.init()
        screenshotIndexer.init()
    }

    this.search = function (theSentenceToBeFound) {

        var smartBodySearchResult = smartBodyIndexer.search(theSentenceToBeFound)

        var screenshotIndexerSearchResult = screenshotIndexer.search(theSentenceToBeFound)

        return searchResultSorter.sort(smartBodySearchResult.concat(screenshotIndexerSearchResult))

    }
}

