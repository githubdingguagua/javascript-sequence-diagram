var screenshotNavigationRenderer = new ScreenshotNavigationRenderer(new HtmlIdManager())

QUnit.test('right html in case we have 5 screenshots', function( assert ) {

    var expectedHtml = "<div>"+
                       "<span id='first-screenshot_scenario1_step2' class='js-navigate-first btn-navigator previous' data-id='scenario1_step2'>" +
                       "<span class='icon-container'><span class='icon'>&#9664;</span></span> Click here to go the first. </span>" +
                       "<span id='last-screenshot_scenario1_step2' class='js-navigate-last btn-navigator next' data-id='scenario1_step2'>Click here to go the last. <span class='icon-container'><span class='icon'>&#9654;</span></span></span>" +
                       "<div class='clear-float padding-top'>Go to screenshot with title id: <input class='js-input-element' type='text' value=''/><span data-id='scenario1_step2' class='btn-navigator js-navigate-carousel icon-container'>GO</span></div>" +
                       "</div>"

    var stepConversation = {scenarioId: "scenario1", stepId: "step2", numberOfScreenshots: 5}

    assert.equal(screenshotNavigationRenderer.render(stepConversation), expectedHtml);

});

QUnit.test('empty html in case we have less than 5 screenshots', function( assert ) {

    var expectedHtml = ""

    assert.equal(screenshotNavigationRenderer.render({numberOfScreenshots: 1}), expectedHtml);
    assert.equal(screenshotNavigationRenderer.render({numberOfScreenshots: 2}), expectedHtml);
    assert.equal(screenshotNavigationRenderer.render({numberOfScreenshots: 3}), expectedHtml);
    assert.equal(screenshotNavigationRenderer.render({numberOfScreenshots: 4}), expectedHtml);

});
