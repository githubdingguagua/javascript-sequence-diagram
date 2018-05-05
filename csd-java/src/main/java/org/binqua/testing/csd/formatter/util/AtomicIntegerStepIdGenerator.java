package org.binqua.testing.csd.formatter.util;

import org.binqua.testing.csd.bridge.external.StepId;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicIntegerStepIdGenerator implements StepIdGenerator {

    private static AtomicInteger counter = new AtomicInteger(1);

    @Override
    public StepId next() {
        return StepId.stepId("step-" + counter.getAndIncrement());
    }

}
