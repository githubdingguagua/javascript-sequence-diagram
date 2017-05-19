package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScreenshotTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void featureIdIsCalculatedFromScenarioId() throws Exception {
        final Screenshot screenshot = aScreenshot("scenario name", "feature-name;scenario-name");
        assertThat(screenshot.featureId(), is("feature-name"));
    }

    @Test
    public void scenarioIdShouldNotContainWhitespaces() throws Exception {
        final String scenarioId = "feature name;scenario-name";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Scenario id cannot contain white spaces: " + scenarioId + " is wrong");

        aScreenshot("scenario name", scenarioId);
    }

    @Test
    public void scenarioIdShouldContainAtLeastOneSemicolon() throws Exception {
        final String scenarioId = "feature-namescenario-name";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Scenario id must contain a semicolon: " + scenarioId + " is wrong");

        aScreenshot("scenario name", scenarioId);
    }

    @Test
    public void givenTitleIsAboutBlanksThenScreenshotIsNotValid() throws Exception {

        final Screenshot notValidScreenshot = aScreenshotWithUrl("about:blank");

        assertThat(notValidScreenshot + " should be not valid", notValidScreenshot.isNotValid(), is(true));

        final Screenshot aValidScreenshot = aScreenshotWithUrl("someUrl");

        assertThat(aValidScreenshot + " should be valid", aValidScreenshot.isNotValid(), is(false));

    }

    private Screenshot aScreenshotWithUrl(String url) {
        return new Screenshot(SimpleBrowserEvent.afterNavigateTo("url"), "scenarioName", "f;s", null, null, url, null);
    }

    private Screenshot aScreenshot(String scenarioName, String scenarioId) {
        return new Screenshot(SimpleBrowserEvent.afterNavigateTo("someUrl"), scenarioName, scenarioId, null, null, null, null);
    }
}