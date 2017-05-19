function MenuRenderer(testsResultSummary) {

    function text(testResultSummary){
        return spanTitle(testResultSummary) + " - " +
               testResultSummary.tests +
               " tests :" + testPassed(testResultSummary) + testFailed(testResultSummary) + testSkipped(testResultSummary)
    }

    function isAFailedTest(testsResultSummary){
        return testsResultSummary.failed != 0 || testsResultSummary.skipped != 0
    }

    function testPassed(testResultSummary){
        if (testResultSummary.passed == 0){
            return ""
        }
        return span("testPassed", testResultSummary.passed + " passed")
    }

    function testFailed(testResultSummary){
        if (testResultSummary.failed == 0){
            return ""
        }
        return span("testFailed", testResultSummary.failed + " failed")
    }

    function testSkipped(testResultSummary){
         if (testResultSummary.skipped == 0){
            return ""
        }
        return span("testSkipped", testResultSummary.skipped + " skipped")
    }

    function spanTitle(singleFeatureResultSummary){
        var theClass = "testPassed"
        if (isAFailedTest(singleFeatureResultSummary)){
            theClass = "testFailed"
        }
        return featureSpan(theClass, singleFeatureResultSummary)
    }

    function span(theClass, text){
        return " <span class='" + theClass + "'>" + text + "</span>"
    }

    function featureSpan(theClass, singleFeatureResultSummary){
        return " <span class='" + theClass + "'>Feature " + singleFeatureResultSummary.id.replace("feature-","") + ": " + singleFeatureResultSummary.name + "</span>"
    }

    function mapToIdAndText(details){
         return $.map(details, function(singleFeatureResultSummary ) {
                return {
                    "id": singleFeatureResultSummary.id,
                    "text": text(singleFeatureResultSummary)
                }
         })
    }

    this.toMenuFormat = function () {
        var testsMenu = mapToIdAndText(testsResultSummary.details);

        if (arguments.length == 0) {
            return testsMenu
        }

        var idsAsArray = arguments[0];

        return $.grep(testsMenu, function( element ) {
                return  $.inArray( element.id, idsAsArray ) != -1 ;
        });
    },

    this.toMenuSummary = function () {
         return testsResultSummary.summary + " - " +
                testsResultSummary.features + " Features - " +
                testsResultSummary.scenarios + " Scenarios - " +
                testsResultSummary.tests + " Tests :" +
                testPassed(testsResultSummary) + testFailed(testsResultSummary) + testSkipped(testsResultSummary)
    }
}

