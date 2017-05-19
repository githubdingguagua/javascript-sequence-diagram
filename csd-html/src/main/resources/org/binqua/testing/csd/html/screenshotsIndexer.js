function ScreenshotsIndexer(namingUtil, featureList, urlFormatter) {

    this.index = lunr(function () {
        this.field('title', {boost: 10})
        this.field('url' , {boost: 5})
        this.ref('id')
    })

    this.screenshotIdAndTitleList = []

    this.screenshotUrls = []
    this.sortedScreenshotUrls = []

    this.screenshotTitles = []
    this.sortedScreenshotTitles = []

    var that = this

    var screenshotIdSeparator = "@@"

    function indexSingleFeatureScreenshots(featureReportFile) {
        $.ajax({
            url: featureReportFile,
            async: false,
            dataType: 'json',
            success: function (featureReport) {
                $.each(featureReport.scenarios, function( i , scenario ) {
                    if (scenario.id == ""){
                        return
                    }
                    indexSingleScenarioScreenshots(featureReport.id, scenario.id, that.index)
                });
            }
        });
    }

    function screenshotIdGenerator(featureId, scenarioId, stepId, screenshotId, titleIndex) {
        return featureId + screenshotIdSeparator +
               scenarioId + screenshotIdSeparator +
               stepId + screenshotIdSeparator +
               screenshotId + screenshotIdSeparator +
               titleIndex
    }

    function screenshotLocator(screenshotId) {
        var idParts = screenshotId.split(screenshotIdSeparator);
        return {
                featureId:idParts[0],
                scenarioId:idParts[1],
                stepId:idParts[2],
                screenshotId:idParts[3],
                titleId:idParts[4]
               }
    }

    this.populateScreenshotUrls = function (screenshot) {
        var formattedUrl = urlFormatter.format(screenshot.url)
        if (this.screenshotUrls.indexOf(formattedUrl) == -1){
            this.screenshotUrls.push(formattedUrl)
        }
    }

    this.populateScreenshotTitles = function (screenshot) {
        if (this.screenshotTitles.indexOf(screenshot.title) == -1){
            this.screenshotTitles.push(screenshot.title)
        }
    }

    function indexSingleScenarioScreenshots(featureId, scenarioId , index ) {
            $.ajax({
                url: namingUtil.scenarioScreenshots(featureId, scenarioId),
                async: false,
                dataType: 'json',
                success: function (scenarioScreenshots) {

                    $.each(scenarioScreenshots, function( screenshotByStepsIndex , screenshotsOfASingleStep ) {

                        $.each(screenshotsOfASingleStep.data, function( titleIndex , aScreenshot ) {

                            that.screenshotIdAndTitleList.push({scenarioId: scenarioId, id:aScreenshot.id, title:aScreenshot.title})

                            that.populateScreenshotUrls(aScreenshot)

                            that.populateScreenshotTitles(aScreenshot)

                            index.add({
                                id: screenshotIdGenerator(featureId, scenarioId, screenshotsOfASingleStep.step, aScreenshot.id, titleIndex + 1),
                                title: aScreenshot.title,
                                url: aScreenshot.url,
                            })

                        });
                    });
                }
            });
    }

    this.init = function () {
        $.each(featureList, function( i , feature ) {
            indexSingleFeatureScreenshots(namingUtil.featureReport(feature.id))
        })
    }

    this.search = function (toBeFound) {
       return $.map(this.index.search(toBeFound), function (elementFound){ return screenshotLocator(elementFound.ref)})
    }

    this.screenshotPageTitleById = function (input) {
        return $.grep(this.screenshotIdAndTitleList, function( scenarioScreenshotIdAndTitlePair ) {
                                                            return scenarioScreenshotIdAndTitlePair.scenarioId == input.scenarioId &&
                                                                   scenarioScreenshotIdAndTitlePair.id == input.screenshotId
                                                      })[0].title
        throw "screenshotid " + screenshotId + " not found in " + JSON.stringify(this.screenshotIdAndTitleList);
    }

    this.listOfUrls = function () {
        function compare (first, second){
            return second.localeCompare(first)
        }

        if (this.sortedScreenshotUrls.length != 0 ){
            return this.sortedScreenshotUrls
        }

        this.sortedScreenshotUrls =  bubbleSort(this.screenshotUrls, compare)
        return this.sortedScreenshotUrls
    }

    this.listOfTitles = function () {
        function compare (first, second){
            return second.localeCompare(first)
        }
        if (this.sortedScreenshotTitles.length != 0 ){
            return this.sortedScreenshotTitles
        }
        this.sortedScreenshotTitles = bubbleSort(this.screenshotTitles, compare)
        return this.sortedScreenshotTitles
    }

    function bubbleSort(arr, compare){
       var len = arr.length;
       for (var i = len-1; i>=0; i--){
         for(var j = 1; j<=i; j++){
           if(compare(arr[j-1], arr[j]) < 0){
               var temp = arr[j-1];
               arr[j-1] = arr[j];
               arr[j] = temp;
            }
         }
       }
       return arr;
    }

}
