package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Test;
import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.bridge.external.StepId;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.binqua.testing.csd.JsonTestUtil.asJsonAfterReplaceDoubleQuotes;
import static org.binqua.testing.csd.bridge.external.StepContext.stepContext;

public class ScenarioScreenshotsTest {

    private final ScenarioScreenshots scenarioScreenshots = new ScenarioScreenshots();

    @Test
    public void asJsonProduceTheRightJsonAndTheScreenshotIdSpreadMultipleSteps() throws Exception {

        final String expectedJson = "[" +
                "{'step':'step-1','data':[{'id':1,'title':'title0','url':'url0','browserEvent':'after navigating to someUrl-for-screenshot-0'},{'id':2,'title':'title1','url':'url1','browserEvent':'before clicking link: someUrl-for-screenshot-1'}]}," +
                "{'step':'step-2','data':[{'id':3,'title':'title2','url':'url2','browserEvent':'before submit via someUrl-for-screenshot-2'},{'id':4,'title':'title3','url':'url3','browserEvent':'just before test finished'}]}" +
                "]";


        final StepContext firstStepContext = stepContext("context1", StepId.stepId("step-1"));
        scenarioScreenshots.notifyStepContextStart(firstStepContext);

        final Screenshot firstScreenshotFirstStep = createScreenshot(0, SimpleBrowserEvent.afterNavigateTo("someUrl-for-screenshot-0"));
        scenarioScreenshots.add(firstScreenshotFirstStep);

        final Screenshot secondScreenshotFirstStep = createScreenshot(1, SimpleBrowserEvent.beforeClickingOnLink("someUrl-for-screenshot-1"));
        scenarioScreenshots.add(secondScreenshotFirstStep);

        scenarioScreenshots.notifyStepContextEnd(firstStepContext);


        final StepContext secondStepContext = stepContext("context2", StepId.stepId("step-2"));
        scenarioScreenshots.notifyStepContextStart(secondStepContext);

        final Screenshot thirdScreenshotSecondStep = createScreenshot(2, SimpleBrowserEvent.beforeSubmitVia("someUrl-for-screenshot-2"));
        scenarioScreenshots.add(thirdScreenshotSecondStep);

        final Screenshot fourthScreenshotSecondStep = createScreenshot(3, SimpleBrowserEvent.justBeforeTestFinished());
        scenarioScreenshots.add(fourthScreenshotSecondStep);

        scenarioScreenshots.notifyStepContextEnd(secondStepContext);

        asJsonAfterReplaceDoubleQuotes(scenarioScreenshots, is(expectedJson));

    }

    @Test
    public void addReturnRightIdentifiableScreenshot() throws Exception {
        assertThat(scenarioScreenshots.add(createScreenshot(0, SimpleBrowserEvent.afterNavigateTo("someUrl-for-screenshot-" + 0))), is(new IdentifiableScreenshot(createScreenshot(0, SimpleBrowserEvent.afterNavigateTo("someUrl-for-screenshot-" + 0)), "1")));
        assertThat(scenarioScreenshots.add(createScreenshot(1, SimpleBrowserEvent.afterNavigateTo("someUrl-for-screenshot-" + 1))), is(new IdentifiableScreenshot(createScreenshot(1, SimpleBrowserEvent.afterNavigateTo("someUrl-for-screenshot-" + 1)), "2")));
    }

    private Screenshot createScreenshot(int index, BrowserEvent browserEvent) {
        return new Screenshot(browserEvent, "scenarioName" + index, "scenario;" + index, new byte[0], "", "url" + index, "title" + index);
    }
}