package org.binqua.testing.csd.bridge.external;

public interface StepContextObserver {

    void notifyStepContextEnd(org.binqua.testing.csd.bridge.external.StepContext context);

    void notifyStepContextStart(StepContext stepContext);
}
