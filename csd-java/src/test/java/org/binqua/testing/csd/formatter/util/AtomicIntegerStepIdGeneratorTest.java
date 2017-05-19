package org.binqua.testing.csd.formatter.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.bniqua.testing.csd.bridge.external.StepId.stepId;

public class AtomicIntegerStepIdGeneratorTest {

    @Test
    public void stepsIdGeneratorStartsFrom1() throws Exception {

        final AtomicIntegerStepIdGenerator atomicIntegerStepsIdGenerator = new AtomicIntegerStepIdGenerator();

        assertThat(atomicIntegerStepsIdGenerator.next(), is(stepId("step-1")));
        assertThat(atomicIntegerStepsIdGenerator.next(), is(stepId("step-2")));
    }
}