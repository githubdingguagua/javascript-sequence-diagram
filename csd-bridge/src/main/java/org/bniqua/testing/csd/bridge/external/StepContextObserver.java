package org.bniqua.testing.csd.bridge.external;

public interface StepContextObserver {

    void notifyStepContextEnd(StepContext context);

    void notifyStepContextStart(StepContext stepContext);
}
