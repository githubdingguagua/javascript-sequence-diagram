package org.binqua.testing.csd.bridge.external;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StepContext {
    private String context;
    private StepId stepId;

    public StepContext(String context, StepId stepId) {
        this.context = context;
        this.stepId = stepId;
    }

    public static StepContext stepContext(String context, StepId stepId) {
        return new StepContext(context, stepId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public StepId stepId() {
        return stepId;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "stepId");
    }

}
