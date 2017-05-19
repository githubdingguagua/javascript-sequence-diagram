package org.binqua.testing.csd.formatter.report.conversation;

import org.bniqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.report.screenshot.Screenshot;

public interface CucumberNotifier {

    void notifyFeatureExecutionStarted(String featureId);

    void notifyScenarioExecutionStarted(String scenarioId);

    void notifyScreenshot(Screenshot screenshot);

    void notifyScenarioExecutionEnded(String cucumberFeatureId, String scenarioId);

    void notifyFeatureExecutionEnded(String cucumberFeatureId, Feature feature);

    void notifyTestEnded();

    void notifyBackgroundStarted();

    void notifyStepExecutionStarted(StepContext stepContext);

    void notifyStepExecutionEnded(StepContext stepContext);
}
