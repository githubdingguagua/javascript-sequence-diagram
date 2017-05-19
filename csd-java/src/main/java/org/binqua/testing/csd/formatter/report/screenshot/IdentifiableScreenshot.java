package org.binqua.testing.csd.formatter.report.screenshot;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IdentifiableScreenshot {

    private final Screenshot screenshot;
    private final String screenshotIdentifier;

    public IdentifiableScreenshot(Screenshot screenshot, String screenshotIdentifier) {
        this.screenshot = screenshot;
        this.screenshotIdentifier = screenshotIdentifier;
    }

    public Screenshot screenshot() {
        return screenshot;
    }

    public String id() {
        return screenshotIdentifier;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
