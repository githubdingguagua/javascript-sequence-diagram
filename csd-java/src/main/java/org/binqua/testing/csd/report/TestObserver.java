package org.binqua.testing.csd.report;

import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.cucumberreports.model.Feature;

public interface TestObserver {

    void notifyFeatureExecutionStarted(String featureId);

    void notifyScenarioExecutionStarted(String scenarioId);

    void notifyScenarioExecutionEnded(String cucumberFeatureId, String scenarioId);

    void notifyFeatureExecutionEnded(String cucumberFeatureId, Feature feature);

    void notifyTestEnded();

    void notifyStepExecutionStarted(StepContext stepContext);

    void notifyStepExecutionEnded(StepContext stepContext);
}
