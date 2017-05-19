package org.binqua.testing.csd.formatter.report.screenshot;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static java.lang.String.format;

public class SimpleBrowserEvent implements BrowserEvent {

    private String reason;

    public SimpleBrowserEvent(String reason) {
        this.reason = reason;
    }

    public static BrowserEvent afterNavigateTo(String theUrl) {
        return new SimpleBrowserEvent(format("after navigating to %s", theUrl));
    }

    public static BrowserEvent beforeClickingOnLink(String text) {
        return new SimpleBrowserEvent(format("before clicking link: %s", text));
    }

    public static BrowserEvent afterClickingOnLink(String text) {
        return new SimpleBrowserEvent(format("after clicking link: %s", text));
    }

    public static BrowserEvent navigatingForward() {
        return new SimpleBrowserEvent("before navigating forward");
    }

    public static BrowserEvent justBeforeTestFinished() {
        return new SimpleBrowserEvent("just before test finished");
    }

    public static BrowserEvent navigatingBack() {
        return new SimpleBrowserEvent("before navigating back");
    }

    public static BrowserEvent beforeSubmitVia(String submitText) {
        return new SimpleBrowserEvent(format("before submit via %s", submitText));
    }

    public static BrowserEvent afterSubmitVia(String submitText) {
        return new SimpleBrowserEvent(format("after submit via %s", submitText));
    }

    @Override
    public String reason() {
        return reason;
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
