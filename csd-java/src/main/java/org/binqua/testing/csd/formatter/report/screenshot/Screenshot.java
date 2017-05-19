package org.binqua.testing.csd.formatter.report.screenshot;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static java.lang.String.format;

public class Screenshot {

    private final String scenarioName;
    private final String scenarioId;
    private final byte[] image;
    private final String pageSource;
    private final String currentUrl;
    private final String currentTitle;
    private final String featureId;
    private BrowserEvent browserEvent;

    public Screenshot(BrowserEvent browserEvent, String scenarioName, String scenarioId, byte[] image, String pageSource, String currentUrl, String currentTitle) {
        checkNoWhitespacesIn(scenarioId);
        this.browserEvent = browserEvent;
        this.scenarioName = scenarioName;
        this.scenarioId = scenarioId;
        this.featureId = calculateFeatureId();
        this.image = image;
        this.pageSource = pageSource;
        this.currentUrl = currentUrl;
        this.currentTitle = currentTitle;
    }

    private void checkNoWhitespacesIn(String scenarioId) {
        if (scenarioId.contains(" ")) {
            throw new IllegalArgumentException(format("Scenario id cannot contain white spaces: %s is wrong", scenarioId));
        }
    }

    public String scenarioName() {
        return scenarioName;
    }

    public String pageSource() {
        return pageSource;
    }

    public byte[] image() {
        return image;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public String featureId() {
        return featureId;
    }

    private String calculateFeatureId() {
        final int featureIdSeparatorIndex = scenarioId.lastIndexOf(";");
        if (featureIdSeparatorIndex == -1) {
            throw new IllegalArgumentException(format("Scenario id must contain a semicolon: %s is wrong", scenarioId));
        }
        return scenarioId.substring(0, featureIdSeparatorIndex);
    }

    public String scenarioId() {
        return scenarioId;
    }

    public BrowserEvent getEvent() {
        return browserEvent;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("current url:")
                .append(getCurrentUrl()).append("\n")
                .append("current title:")
                .append(getCurrentTitle()).append("\n")
                .append("scenarioName:")
                .append(scenarioName).append("\n")
                .append("scenarioId:")
                .append(scenarioId).append("\n")
                .append("featureId:")
                .append(featureId).append("\n")
                .toString();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "pageSource", "image");
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, "pageSource", "image");
    }

    public boolean isNotValid() {
        return ("about:blank".equals(getCurrentUrl()));
    }
}
