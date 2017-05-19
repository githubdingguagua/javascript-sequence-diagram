package org.binqua.testing.csd.formatter.external;

import org.bniqua.testing.csd.bridge.external.StepContext;
import org.bniqua.testing.csd.bridge.external.StepId;

public interface StepContexts {

    StepId recordStep(String name);

    StepContext recordStepMatch();

    StepContext lastStepContextMatched();
}
