function SearchResultSorter() {

    this.sort = function (toBeSorted) {

        function extractNumberIdFrom(aStringWithANumber) {
            return parseInt(aStringWithANumber.replace(/^\D+/, ''))
        }

        function compareByFeatureId(first, second) {
            return compareByPropertyId(first, second, "featureId")
        }

        function compareByScenarioId(first, second) {
            return compareByPropertyId(first, second, "scenarioId")
         }

        function isAScreenshotSearchResult(first, second) {
            return first.screenshotId && second.screenshotId
        }

        function isAStepSearchResult(first, second) {
            return first.stepId && second.stepId
        }

        function hasOnlyScenarioId(first) {
            return !first.stepId && !first.screenshotId
        }

        function compareByStepOrScreenshotId(first, second) {

            if (isAStepSearchResult(first, second)){
                return compareByPropertyId(first, second, "stepId")
            }

            if (isAScreenshotSearchResult(first, second)){
                return compareByPropertyId(first, second, "screenshotId")
            }

            if (hasOnlyScenarioId(first) && second.screenshotId){
                return 1
            }

            if (first.screenshotId && hasOnlyScenarioId(second)){
                return -1
            }

            if (first.stepId && second.screenshotId){
                return 1
            }

            if (first.screenshotId && second.stepId){
               return -1
            }

            if (hasOnlyScenarioId(first) && second.stepId){
               return 1
            }

            if (first.stepId && hasOnlyScenarioId(second)){
               return -1
            }

        }

        function compareByPropertyId(first, second, property) {
            if (extractNumberIdFrom(first[property]) < extractNumberIdFrom(second[property])) return 1
            else if (extractNumberIdFrom(first[property]) > extractNumberIdFrom(second[property])) return -1
            else return 0
        }

        function compare(first, second) {

            var compareByFeatureIdResult = compareByFeatureId(first, second)

            if (compareByFeatureIdResult == 0){
                 var compareByScenarioIdResult = compareByScenarioId(first, second)

                 if (compareByScenarioIdResult == 0){
                    return compareByStepOrScreenshotId(first, second)
                 }

                 return compareByScenarioIdResult
            }

            return compareByFeatureIdResult
        }

        return bubbleSort(toBeSorted, compare);

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

