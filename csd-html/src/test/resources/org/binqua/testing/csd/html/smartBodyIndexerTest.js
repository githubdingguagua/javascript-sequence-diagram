QUnit.test("search result return the right step id in case of 1 feature", function( assert ) {

    var featureList = [
      {"id": "feature-0-smart-indexer"}
    ]

    var indexer = new SmartBodyIndexer(new NamingUtil(), featureList)

    indexer.init()

    var resultWithWord = indexer.search("step0")

    assert.equal(resultWithWord.length, 1);
    assert.equal(resultWithWord[0].featureId, "feature-0-smart-indexer");
    assert.equal(resultWithWord[0].scenarioId, "scenario-0");
    assert.equal(resultWithWord[0].stepId, "step-0");

});

QUnit.test("search result return the right scenario id in case of 1 feature", function( assert ) {

    var featureList = [
      {"id": "feature-0-smart-indexer"}
    ]

    var indexer = new SmartBodyIndexer(new NamingUtil(), featureList)

    indexer.init()

    var resultWithWord = indexer.search("this is scenario 1")

    assert.equal(resultWithWord.length, 1);
    assert.equal(resultWithWord[0].featureId, "feature-0-smart-indexer");
    assert.equal(resultWithWord[0].scenarioId, "scenario-1");
    assert.equal(resultWithWord[0].stepId, "");

});

QUnit.test("search result return the right feature id in case of 1 feature", function( assert ) {

    var featureList = [
      {"id": "feature-0-smart-indexer"}
    ]

    var indexer = new SmartBodyIndexer(new NamingUtil(), featureList)

    indexer.init()

    var actualSearchResult = indexer.search("feature-0-smart-indexer-title")

    assert.equal(actualSearchResult.length, 1);

    assert.equal(actualSearchResult[0].featureId, "feature-0-smart-indexer");
    assert.equal(actualSearchResult[0].scenarioId, "");
    assert.equal(actualSearchResult[0].stepId, "");

});

QUnit.test("search result return the right step ids in case of 2 features", function( assert ) {

    var featureList = [
        {"id": "feature-0-smart-indexer"},
        {"id": "feature-1-smart-indexer"},
    ]

    var indexer = new SmartBodyIndexer(new NamingUtil(), featureList)

    indexer.init()

    var actualSearchResult = new SearchResultSorter().sort(indexer.search("an happy user"))

    assert.equal(actualSearchResult.length, 2);

    assert.equal(actualSearchResult[0].featureId, "feature-0-smart-indexer");
    assert.equal(actualSearchResult[0].scenarioId, "scenario-1");
    assert.equal(actualSearchResult[0].stepId, "step-2");

    assert.equal(actualSearchResult[1].featureId, "feature-1-smart-indexer");
    assert.equal(actualSearchResult[1].scenarioId, "scenario-3");
    assert.equal(actualSearchResult[1].stepId, "step-6");

});

QUnit.test("featureNameById and scenarioNameById work", function( assert ) {

    var featureList = [
        {"id": "feature-0-smart-indexer"}
    ]

    var indexer = new SmartBodyIndexer(new NamingUtil(), featureList)

    indexer.init()

    assert.equal(indexer.featureNameById("feature-0-smart-indexer"), "feature-0-smart-indexer-title");

    assert.equal(indexer.scenarioNameById("scenario-0"), "a background 0");
    assert.equal(indexer.scenarioNameById("scenario-1"), "this is scenario 1");

    assert.equal(indexer.stepNameById("step-0"), "Given - we are happy step0");
    assert.equal(indexer.stepNameById("step-1"), "When - we looks very happy step1");
    assert.equal(indexer.stepNameById("step-2"), "Given - an happy user step 2");
    assert.equal(indexer.stepNameById("step-3"), "When - I call the status page scenario 0 step 3");

});
