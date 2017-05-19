package org.binqua.testing.csd.cucumberreports.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestCounter {

    private final int numberOfPassedTests;
    private final int numberOfFailingTests;
    private final int numberOfSkippedTests;

    public TestCounter(int numberOfPassedTests, int numberOfFailedTests, int numberOfSkippedTests) {
        this.numberOfPassedTests = numberOfPassedTests;
        this.numberOfFailingTests = numberOfFailedTests;
        this.numberOfSkippedTests = numberOfSkippedTests;
    }

    public int numberOfPassedTests() {
        return numberOfPassedTests;
    }

    public int numberOfFailedTests() {
        return numberOfFailingTests;
    }

    public int numberOfTotalTests() {
        return numberOfPassedTests + numberOfFailingTests + numberOfSkippedTests;
    }

    public int numberOfSkippedTests() {
        return numberOfSkippedTests ;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
