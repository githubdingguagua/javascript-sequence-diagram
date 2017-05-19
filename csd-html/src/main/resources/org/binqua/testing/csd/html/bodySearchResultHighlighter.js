function BodySearchResultHighlighter(colorToBeHighlightedWith) {

    var oldHighlightedElement;
    var screenshotContainerOriginalColor;

    this.deselectOldSelection = function () {
        if (oldHighlightedElement !== undefined) {
            oldHighlightedElement.css( { "border-color": screenshotContainerOriginalColor } );
        }
        oldHighlightedElement = undefined
    },

    this.showSelectedSearchResult = function (configuration) {
        var elementToBeHighlighted = $("#" + configuration.object.searchResult.idFound)

        this.deselectOldSelection()

        oldHighlightedElement = elementToBeHighlighted
        screenshotContainerOriginalColor = elementToBeHighlighted.css("border-color")

        elementToBeHighlighted.css( { "border-color": colorToBeHighlightedWith} );

    },

    this.handle = function (configuration) {
        return  configuration.object !== undefined && 
                configuration.object.searchResult !== undefined && 
                configuration.object.searchResult.idFound !== undefined 
    }

}

