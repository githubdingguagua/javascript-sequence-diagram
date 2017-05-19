var htmlIdManager = new HtmlIdManager()

QUnit.test("thumbnails container id is correct", function( assert ) {

    assert.equal(htmlIdManager.generateId("prefix", "a", "b"), "prefix_a_b");

});

QUnit.test("screenshot image id is calculated correctly", function( assert ) {

    assert.equal(htmlIdManager.screenshotImageId("100", "scenario-1", "step-2"), "scenario-1_step-2_title-100");
    assert.equal(htmlIdManager.screenshotImageId("200", "scenario-11", "step-22"), "scenario-11_step-22_title-200");

    assert.equal(htmlIdManager.screenshotImageId("200", "some-thing_scenario-1_step-2_something-else"), "scenario-1_step-2_title-200");

});

QUnit.test( "id has to contains regex (scenario-\d+).*(step-\d+)", function( assert ) {
    assert.throws(
        function() {
              htmlIdManager.screenshotImageId("100", "sdsdd")
         },
         new Error("id has to contains regex (scenario-\d+).*(step-\d+)")
     );
});

QUnit.test("scenario id is calculated correctly", function( assert ) {

    assert.equal(htmlIdManager.scenarioId("scenario-1_step-2_screenshot-3_title-100"), "scenario-1");

});

QUnit.test("screenshot id is calculated correctly", function( assert ) {

    assert.equal(htmlIdManager.screenshotId("scenario-1_step-2_screenshot-3_title-100"), "3");

});

QUnit.test("screenshots label id is correct", function( assert ) {

    assert.equal(htmlIdManager.screenshotsLabelId({scenarioId: "scenario-1", stepId: "step-2"}), "screenshots-label_scenario-1_step-2");

});

QUnit.test("screenshots body id is correct", function( assert ) {

    assert.equal(htmlIdManager.screenshotsBodyId({scenarioId: "scenario-1", stepId: "step-2"}), "screenshots-body_scenario-1_step-2");

});

QUnit.test("thumbnails container id is correct", function( assert ) {

    assert.equal(htmlIdManager.thumbnailsContainerId({scenarioId: "scenario-1", stepId: "step-2"}), "thumbnail_scenario-1_step-2");

    assert.equal(htmlIdManager.thumbnailsContainerId("some-thing_scenario-1_step-2_something-else"), "thumbnail_scenario-1_step-2");
    assert.equal(htmlIdManager.thumbnailsContainerId("some-thing_scenario-1_bla_step-2_something-else"), "thumbnail_scenario-1_step-2");
    assert.equal(htmlIdManager.thumbnailsContainerId("some-thing_scenario-1_step-2"), "thumbnail_scenario-1_step-2");
    assert.equal(htmlIdManager.thumbnailsContainerId("scenario-1_step-2_somethingElse"), "thumbnail_scenario-1_step-2");
    assert.equal(htmlIdManager.thumbnailsContainerId("scenario-1_step-2"), "thumbnail_scenario-1_step-2");

});