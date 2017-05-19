package org.bniqua.testing.csd.bridge.external;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.bniqua.testing.csd.bridge.external.StepId.stepId;

public class StepIdTest  {

    @Test
    public void stepsAreSortedByDecimalPartOfTheirId() throws Exception {

        assertThat(stepId("step-100").compareTo(stepId("step-101")), is(-1));

        assertThat(stepId("step-11").compareTo(stepId("step-2")), is(1));

        assertThat(stepId("step-1").compareTo(stepId("step-1")), is(0));
    }

}