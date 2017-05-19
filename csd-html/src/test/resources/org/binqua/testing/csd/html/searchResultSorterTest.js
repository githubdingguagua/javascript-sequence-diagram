QUnit.test("SearchResultSorter sorts buy ascending feature in case no scenario id", function( assert ) {

       var toBeSorted = [
           { featureId:"feature-1", scenarioId:"", stepId:"" },
           { featureId:"feature-0", scenarioId:"", stepId:"" },
           { featureId:"feature-2", scenarioId:"", stepId:"" },
           { featureId:"feature-4", scenarioId:"", stepId:"" },
           { featureId:"feature-3", scenarioId:"", stepId:"" },
           { featureId:"feature-5", scenarioId:"", stepId:"" },
           { featureId:"feature-7", scenarioId:"", stepId:"" },
           { featureId:"feature-9", scenarioId:"", stepId:"" },
           { featureId:"feature-8", scenarioId:"", stepId:"" },
           { featureId:"feature-10", scenarioId:"", stepId:"" },
           { featureId:"feature-6", scenarioId:"", stepId:"" },
           { featureId:"feature-11", scenarioId:"", stepId:"" }
       ]

       var actualSortedResult = new SearchResultSorter().sort(toBeSorted)

       assert.equal(actualSortedResult[0].featureId, "feature-0");
       assert.equal(actualSortedResult[1].featureId, "feature-1");
       assert.equal(actualSortedResult[2].featureId, "feature-2");
       assert.equal(actualSortedResult[3].featureId, "feature-3");
       assert.equal(actualSortedResult[4].featureId, "feature-4");
       assert.equal(actualSortedResult[5].featureId, "feature-5");
       assert.equal(actualSortedResult[6].featureId, "feature-6");
       assert.equal(actualSortedResult[7].featureId, "feature-7");
       assert.equal(actualSortedResult[8].featureId, "feature-8");
       assert.equal(actualSortedResult[9].featureId, "feature-9");
       assert.equal(actualSortedResult[10].featureId, "feature-10");
       assert.equal(actualSortedResult[11].featureId, "feature-11");

});

QUnit.test("SearchResultSorter sorts buy ascending feature in case 2 entries one with scenario id and one without", function( assert ) {

    var toBeSorted = [
        {
            featureId:"feature-1",
            scenarioId:"scenario-1",
            stepId:""
        },
        {
            featureId:"feature-0",
            scenarioId:"",
            stepId:""
        }
    ]

    var actualSortedResult = new SearchResultSorter().sort(toBeSorted)

    assert.equal(actualSortedResult[0].featureId, "feature-0");
    assert.equal(actualSortedResult[1].featureId, "feature-1");

});

QUnit.test("SearchResultSorter sorts buy ascending feature number id", function( assert ) {

    var toBeSorted = [
        {
            featureId:"feature-1",
            scenarioId:"scenario-0",
            stepId:"step-0"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-1",
            stepId:"step-1"
        }
    ]

    var sorted = new SearchResultSorter().sort(toBeSorted)

    assert.equal(sorted[0].featureId, "feature-0");
    assert.equal(sorted[1].featureId, "feature-1");

});


QUnit.test("SearchResultSorter sorts buy ascending scenario number id in case of same feature number id", function( assert ) {

    var toBeSorted = [
        {
            featureId:"feature-0",
            scenarioId:"scenario-1",
            stepId:"step-0"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-0"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-3",
            stepId:"step-0"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-2",
            stepId:"step-0"
        }
    ]

    var sorted = new SearchResultSorter().sort(toBeSorted)

    assert.equal(sorted[0].scenarioId, "scenario-0");
    assert.equal(sorted[1].scenarioId, "scenario-1");
    assert.equal(sorted[2].scenarioId, "scenario-2");
    assert.equal(sorted[3].scenarioId, "scenario-3");

});

QUnit.test("SearchResultSorter sorts buy ascending step number id in case of same feature and scenario number id", function( assert ) {

    var toBeSorted = [
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-1"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-0"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-3"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-2"
        }
    ]

    var sorted = new SearchResultSorter().sort(toBeSorted)

    assert.equal(sorted[0].stepId, "step-0");
    assert.equal(sorted[1].stepId, "step-1");
    assert.equal(sorted[2].stepId, "step-2");
    assert.equal(sorted[3].stepId, "step-3");

});

QUnit.test("SearchResultSorter sorts buy ascending screenshots in case of same feature and scenario number id", function( assert ) {

    var toBeSorted = [
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            screenshotId:"1"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            screenshotId:"0"
        },
        {
             featureId:"feature-0",
             scenarioId:"scenario-0",
             screenshotId:"3"
        },
         {
              featureId:"feature-0",
              scenarioId:"scenario-0",
              screenshotId:"2"
         }
    ]

    var sorted = new SearchResultSorter().sort(toBeSorted)

    assert.equal(sorted[0].screenshotId, "0");
    assert.equal(sorted[1].screenshotId, "1");
    assert.equal(sorted[2].screenshotId, "2");
    assert.equal(sorted[3].screenshotId, "3");

});

QUnit.test("given a screenshot before a step then searchResultSorter sorts steps before screenshots in case of same feature and scenario number id", function( assert ) {

    var toBeSorted = [
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            screenshotId:"1"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-1"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            screenshotId:"0"
        },
        {
            featureId:"feature-0",
            scenarioId:"scenario-0",
            stepId:"step-0"
        }
    ]

    var sorted = new SearchResultSorter().sort(toBeSorted)

    assert.equal(sorted[0].stepId, "step-0");
    assert.equal(sorted[1].stepId, "step-1");
    assert.equal(sorted[2].screenshotId, "0");
    assert.equal(sorted[3].screenshotId, "1");

});

QUnit.test("given a step before a screenshot then searchResultSorter sorts steps before screenshots in case of same feature and scenario number id", function( assert ) {

    var toBeSorted = [
        {featureId:"feature-0", scenarioId:"scenario-0", stepId:"step-0"},
        {featureId:"feature-0", scenarioId:"scenario-0", screenshotId:"1"}
    ]

    var sorted = new SearchResultSorter().sort(toBeSorted)

    assert.equal(sorted[0].stepId, "step-0");
    assert.equal(sorted[1].screenshotId, "1");
});

QUnit.test("given 2 results with only feature ids then sort works fine", function( assert ) {

       var toBeSorted = [
           {featureId:"feature-1", scenarioId:"", stepId:""},
           {featureId:"feature-0", scenarioId:"", screenshotId:""}
       ]

       var sorted = new SearchResultSorter().sort(toBeSorted)

       assert.equal(sorted[0].featureId, "feature-0");
       assert.equal(sorted[1].featureId, "feature-1");
});

QUnit.test("given no stepId and a screenshot then step element comes first", function( assert ) {

    var toBeSorted = [ { featureId:"feature-0", scenarioId:"scenario-0", screenshotId:"1" },
                       { featureId:"feature-0", scenarioId:"scenario-0", stepId:"" } ]

    var actualSortedResult = new SearchResultSorter().sort(toBeSorted)

    assert.equal(actualSortedResult[0].stepId, "");

    assert.equal(actualSortedResult[1].screenshotId, "1");

});

QUnit.test("given stepId and a no screenshot then step element comes first", function( assert ) {

    var toBeSorted = [ { featureId:"feature-0", scenarioId:"scenario-0", screenshotId:"" },
                       { featureId:"feature-0", scenarioId:"scenario-0", stepId:"step-0" } ]

    var actualSortedResult = new SearchResultSorter().sort(toBeSorted)

    assert.equal(actualSortedResult[0].screenshotId, "");

    assert.equal(actualSortedResult[1].stepId, "step-0");

});

