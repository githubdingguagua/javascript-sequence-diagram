package org.bniqua.testing.csd.bridge.external;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StepId implements Comparable<StepId> {

    private final String stepId;
    private final int index;

    public StepId(String stepId) {
        this.stepId = stepId;
        this.index = Integer.valueOf(this.stepId.replace("step-", ""));
    }

    public static StepId stepId(String stepId) {
        return new StepId(stepId);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "stepId");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String asString() {
        return stepId;
    }

    @Override
    public int compareTo(StepId that) {
        if (this.index == that.index) {
            return 0;
        }
        return this.index >= that.index ? 1 : -1;
    }
}
