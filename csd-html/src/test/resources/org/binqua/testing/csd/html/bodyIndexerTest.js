QUnit.test("given 3 features and given feature-0 has the word 'feature-2 title' in the feature title " +
           "then searchFeatureWith returns feature-2 " +
           "and searchFeatureWithout returns feature-0 and feature-1", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

    bodyIndexer.init()

    var resultWithWord = bodyIndexer.searchFeatureIdsWith("feature-2 title")

    assert.equal(resultWithWord.length, 1);
    assert.equal(resultWithWord[0], "feature-2");

    var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("feature-2 title")

    assert.equal(resultWithoutWord.length, 2);
    assert.equal(resultWithoutWord[0], "feature-0");
    assert.equal(resultWithoutWord[1], "feature-1");

});

QUnit.test("given 3 features and given feature-0 has the word 'claimant' in a scenario title " +
           "then searchFeatureWith returns feature-0 " +
           "and searchFeatureWithout returns feature-1 and feature-2", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"},
      {"id": "feature-2"}
    ]

    var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

    bodyIndexer.init()

    var resultWithWord = bodyIndexer.searchFeatureIdsWith("claimant")

    assert.equal(resultWithWord.length, 1);
    assert.equal(resultWithWord[0], "feature-0");

    var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("claimant")

    assert.equal(resultWithoutWord.length, 2);
    assert.equal(resultWithoutWord[0], "feature-1");
    assert.equal(resultWithoutWord[1], "feature-2");

});

QUnit.test("given feature-0 and feature-1 have the words 'blocked' in scenario title " +
           "then searchFeatureIdsWith returns feature-0 and feature-1 " +
           "and searchFeatureIdsWithout returns []", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"}
    ]

    var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

    bodyIndexer.init()

    var resultWithWord = bodyIndexer.searchFeatureIdsWith("blocked")

    assert.equal(resultWithWord.length, 2);

    assert.equal(resultWithWord[0], "feature-0");
    assert.equal(resultWithWord[1], "feature-1");

    var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("blocked")

    assert.equal(resultWithoutWord.length, 0);

});

QUnit.test("given feature-0 and feature-1 and given feature-1 has the words 'house ball' in scenario title " +
           "then searchFeatureIdsWith 'ball house' returns feature-1 " +
           "and searchFeatureIdsWithout returns feature-0", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"}
    ]

    var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

    bodyIndexer.init()

    var resultWithWord = bodyIndexer.searchFeatureIdsWith("ball house")

    assert.equal(resultWithWord.length, 1);
    assert.equal(resultWithWord[0], "feature-1");

    var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("ball house")

    assert.equal(resultWithoutWord.length, 1);
    assert.equal(resultWithoutWord[0], "feature-0");

})

QUnit.test("given feature-1 have the words 'house ball' in scenario title " +
           "then search for 'house ball' returns feature-1", function( assert ) {

    var featureList = [
      {"id": "feature-0"},
      {"id": "feature-1"}
    ]

    var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

    bodyIndexer.init()

    var resultWithWord = bodyIndexer.searchFeatureIdsWith("house ball")

    assert.equal(resultWithWord.length, 1);
    assert.equal(resultWithWord[0], "feature-1");

    var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("house ball")

    assert.equal(resultWithoutWord.length, 1);
    assert.equal(resultWithoutWord[0], "feature-0");

})

QUnit.test("given feature-0 and feature-1 and given feature-1 has the words 'we are happy' in a scenario step " +
              "then searchFeatureIdsWith 'we are happy' returns feature-1 " +
              "and searchFeatureIdsWithout returns feature-0", function( assert ) {

          var featureList = [
            {"id": "feature-0"},
            {"id": "feature-1"}
          ]

          var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

          bodyIndexer.init()

          var resultWithWord = bodyIndexer.searchFeatureIdsWith("we are happy")

          assert.equal(resultWithWord.length, 1);
          assert.equal(resultWithWord[0], "feature-0");

          var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("we are happy")

          assert.equal(resultWithoutWord.length, 1);
          assert.equal(resultWithoutWord[0], "feature-1");
});

QUnit.test("given feature-0 and feature-1 and given feature-1 has the words 'we are happy' in a scenario step " +
              "then searchFeatureIdsWith 'we are happy' returns feature-1 " +
              "and searchFeatureIdsWithout returns feature-0", function( assert ) {

          var featureList = [
            {"id": "feature-0"},
            {"id": "feature-1"}
          ]

          var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

          bodyIndexer.init()

          var resultWithWord = bodyIndexer.searchFeatureIdsWith("we are happy")

          assert.equal(resultWithWord.length, 1);
          assert.equal(resultWithWord[0], "feature-0");

          var resultWithoutWord = bodyIndexer.searchFeatureIdsWithout("we are happy")

          assert.equal(resultWithoutWord.length, 1);
          assert.equal(resultWithoutWord[0], "feature-1");
});

QUnit.test("featureNameById and scenarioNameById work", function( assert ) {

          var featureList = [
            {"id": "feature-0"},
            {"id": "feature-1"},
            {"id": "feature-2"},
          ]

          var bodyIndexer = new BodyIndexer(new NamingUtil(), featureList)

          bodyIndexer.init()

          assert.equal(bodyIndexer.featureNameById("feature-0"), "feature-0 title");
          assert.equal(bodyIndexer.featureNameById("feature-1"), "feature-1 title");
          assert.equal(bodyIndexer.featureNameById("feature-2"), "feature-2 title");

          assert.equal(bodyIndexer.scenarioNameById("scenario-0"), "a background 0");
          assert.equal(bodyIndexer.scenarioNameById("scenario-1"), "A claimant is blocked");
          assert.equal(bodyIndexer.scenarioNameById("scenario-2"), "house ball dog");
          assert.equal(bodyIndexer.scenarioNameById("scenario-3"), "blocked could be a nice word");
          assert.equal(bodyIndexer.scenarioNameById("scenario-4"), "lemon");
          assert.equal(bodyIndexer.scenarioNameById("scenario-5"), "apple");

});
