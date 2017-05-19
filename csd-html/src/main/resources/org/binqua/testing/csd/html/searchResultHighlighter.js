function SearchResultHighlighter(screenshotSearchResultHighlighter, bodySearchResultHighlighter) {

    this.showSelectedSearchResult = function (configuration) {

        if (screenshotSearchResultHighlighter.handle(configuration)) {

            screenshotSearchResultHighlighter.showSelectedSearchResult(configuration)

            bodySearchResultHighlighter.deselectOldSelection()

        } else if (bodySearchResultHighlighter.handle(configuration)){

            bodySearchResultHighlighter.showSelectedSearchResult(configuration)

            screenshotSearchResultHighlighter.deselectOldSelection()

        }

    },

    this.aSearchResultHasBeenSelected = function (configuration) {
        return configuration.object !== undefined &&
               configuration.object.searchResult !== undefined
    },

    this.deselectedSearchResult = function () {
        bodySearchResultHighlighter.deselectOldSelection()
        screenshotSearchResultHighlighter.deselectOldSelection()
    }

}

