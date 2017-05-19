QUnit.test( "given no feature-ids passed as arguments then menu is created properly", function( assert ) {

    var testsResults = {
         "features": 1,
         "scenarios": 2,
         "tests": 2,
         "passed": 2,
         "failed": 2,
         "skipped": 2,
         "details": [
           {"id":"feature-1","name":"feature name 1","tests":8,"passed":1,"failed":3,"skipped":4},
           {"id":"feature-2","name":"feature name 2","tests":6,"passed":1,"failed":2,"skipped":3}
         ]
    }

    var menuRenderer = new MenuRenderer(testsResults);

    var actualResult = menuRenderer.toMenuFormat()

    equal(actualResult[0].id, "feature-1");
    equal(actualResult[0].text, " <span class='testFailed'>Feature 1: feature name 1</span> - 8 tests : <span class='testPassed'>1 passed</span> <span class='testFailed'>3 failed</span> <span class='testSkipped'>4 skipped</span>");

    equal(actualResult[1].id, "feature-2");
    equal(actualResult[1].text, " <span class='testFailed'>Feature 2: feature name 2</span> - 6 tests : <span class='testPassed'>1 passed</span> <span class='testFailed'>2 failed</span> <span class='testSkipped'>3 skipped</span>");

});

QUnit.test( "0 passed is not show", function( assert ) {

    var testsResults = {
         "details": [
           {"id":"feature-1","name":"feature name 1","tests":8,"passed":0,"failed":1,"skipped":0}
         ]
    }

    var menuRenderer = new MenuRenderer(testsResults);

    var actualResult = menuRenderer.toMenuFormat()

    equal(actualResult[0].text, " <span class='testFailed'>Feature 1: feature name 1</span> - 8 tests : <span class='testFailed'>1 failed</span>");

});

QUnit.test( "0 failed is not show", function( assert ) {

    var testsResults = {
         "details": [
           {"id":"feature-1","name":"feature name 1","tests":8,"passed":1,"failed":0,"skipped":1}
         ]
    }

    var menuRenderer = new MenuRenderer(testsResults);

    var actualResult = menuRenderer.toMenuFormat()

    equal(actualResult[0].text, " <span class='testFailed'>Feature 1: feature name 1</span> - 8 tests : <span class='testPassed'>1 passed</span> <span class='testSkipped'>1 skipped</span>");

});

QUnit.test( "0 skipped is not show", function( assert ) {

    var testsResults = {
         "details": [
           {"id":"feature-1","name":"feature name 1","tests":8,"passed":1,"failed":1,"skipped":0}
         ]
    }

    var menuRenderer = new MenuRenderer(testsResults);

    var actualResult = menuRenderer.toMenuFormat()

    equal(actualResult[0].text, " <span class='testFailed'>Feature 1: feature name 1</span> - 8 tests : <span class='testPassed'>1 passed</span> <span class='testFailed'>1 failed</span>");

});

QUnit.test( "0 skipped is not show", function( assert ) {

    var testsResults = {
         "features": 1,
         "scenarios": 2,
         "tests": 2,
         "passed": 2,
         "failed": 2,
         "skipped": 2,
         "details": [
           {"id":"feature-1","name":"feature name 1","tests":8,"passed":1,"failed":1,"skipped":0}
         ]
    }

    var menuRenderer = new MenuRenderer(testsResults);

    var actualResult = menuRenderer.toMenuFormat()

    equal(actualResult[0].text, " <span class='testFailed'>Feature 1: feature name 1</span> - 8 tests : <span class='testPassed'>1 passed</span> <span class='testFailed'>1 failed</span>");

});

QUnit.test( "menu summary is correct", function( assert ) {

    var testsResults = {
         "summary": "some text",
         "features": 1,
         "scenarios": 2,
         "tests": 6,
         "passed": 1,
         "failed": 2,
         "skipped": 3,
    }

    var menuRenderer = new MenuRenderer(testsResults);

    var actualResult = menuRenderer.toMenuSummary()

    equal(actualResult, "some text - 1 Features - 2 Scenarios - 6 Tests : <span class='testPassed'>1 passed</span> <span class='testFailed'>2 failed</span> <span class='testSkipped'>3 skipped</span>");

});
