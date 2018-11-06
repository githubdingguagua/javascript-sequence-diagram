package org.binqua.testing.csd.report;

import org.binqua.testing.csd.bridge.external.StepContext;

public interface TestObserver {

    void notifyFeatureExecutionStarted(String featureId);

    void notifyScenarioExecutionStarted(String scenarioId);

    void notifyScenarioExecutionEnded(String cucumberFeatureId, String scenarioId);

    void notifyFeatureExecutionEnded(String cucumberFeatureId);

    void notifyTestEnded();

    void notifyStepExecutionStarted(StepContext stepContext);

    void notifyStepExecutionEnded(StepContext stepContext);
}
