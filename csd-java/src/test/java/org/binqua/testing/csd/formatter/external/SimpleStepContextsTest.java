package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.formatter.util.StepIdGenerator;
import org.junit.Test;
import org.binqua.testing.csd.bridge.external.StepId;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.binqua.testing.csd.bridge.external.StepContext.stepContext;
import static org.binqua.testing.csd.bridge.external.StepId.stepId;

public class SimpleStepContextsTest {

    private final StepIdGenerator stepIdGenerator = mock(StepIdGenerator.class);

    private final SimpleStepContexts simpleConversationContexts = new SimpleStepContexts(stepIdGenerator);

    @Test
    public void givenSomeStepsHaveBeenRecordedThenAfterEveryMatchNextContextReturnsAllStepsRecorded() throws Exception {
        StepId firstStepId = mock(StepId.class);
        when(stepIdGenerator.next()).thenReturn(firstStepId);

        StepId secondStepId = mock(StepId.class);
        when(stepIdGenerator.next()).thenReturn(secondStepId);

        simpleConversationContexts.recordStep("context1");
        simpleConversationContexts.recordStep("context2");
        simpleConversationContexts.recordStep("context2");

        assertThat(simpleConversationContexts.recordStepMatch(), is(stepContext("context1", firstStepId)));

        assertThat(simpleConversationContexts.lastStepContextMatched(), is(stepContext("context1", firstStepId)));
        assertThat(simpleConversationContexts.lastStepContextMatched(), is(stepContext("context1", firstStepId)));
        assertThat(simpleConversationContexts.lastStepContextMatched(), is(stepContext("context1", firstStepId)));

        assertThat(simpleConversationContexts.recordStepMatch(), is(stepContext("context2", firstStepId)));
        assertThat(simpleConversationContexts.lastStepContextMatched(), is(stepContext("context2", firstStepId)));
        assertThat(simpleConversationContexts.lastStepContextMatched(), is(stepContext("context2", firstStepId)));
        assertThat(simpleConversationContexts.lastStepContextMatched(), is(stepContext("context2", firstStepId)));

    }

    @Test
    public void givenMatchHaveNotBeenCalledThenNextContextThrowsIllegalStateException() throws Exception {

        when(stepIdGenerator.next()).thenReturn(stepId("step-1"), stepId("step-2"));

        simpleConversationContexts.recordStep("context1");
        simpleConversationContexts.recordStep("context2");

        try {
            simpleConversationContexts.lastStepContextMatched();
            fail(IllegalStateException.class + " should have been thrown!");
        } catch (IllegalStateException iae) {
            assertThat(iae.getMessage(), is(equalTo("Recorded 2 steps:\nstep-1\nstep-2\nbut match never called yet. Something went wrong!")));
        }

    }
}