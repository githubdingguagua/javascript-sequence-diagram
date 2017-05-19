function SearchResultFormatter(bodySearchResultFormatter, screenshotSearchResultFormatter) {

    function applySuitableFormatterTo (elementIndex, elementToBeFormatted){
        if (screenshotCase(elementToBeFormatted)){
            return screenshotSearchResultFormatter.format(elementIndex, elementToBeFormatted)
        } else if (stepCase(elementToBeFormatted)){
            return bodySearchResultFormatter.format(elementIndex, elementToBeFormatted)
        }
        throw new Error("One object to be formatted does not contain screenshotId or stepId field: " + JSON.stringify(elementToBeFormatted));
    }

    function screenshotCase (elementToBeFormatted){
        return elementToBeFormatted.screenshotId
    }

    function stepCase (elementToBeFormatted){
        return elementToBeFormatted.stepId || elementToBeFormatted.stepId === ""
    }

    this.format = function (elementsToBeFormatted){
        w2ui.layout.set('bottom', { size: 450 });
        var ulTag = "<ul>"
        $.each(elementsToBeFormatted, function (elementIndex, elementToBeFormatted){
           ulTag = ulTag + applySuitableFormatterTo(elementIndex, elementToBeFormatted)
        })
        return $("<div>" + ulTag + "</ul></div>").html()
    },

    this.makeIdClickable = function (someObject) {
        bodySearchResultFormatter.makeIdClickable(someObject)
        screenshotSearchResultFormatter.makeIdClickable(someObject)
    }

}

