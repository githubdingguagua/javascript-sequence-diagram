package org.binqua.testing.csd.multiplereportswebapp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TestReportInfo {

    private final String name;
    private final String url;
    private final String partOfReportHomePageUrl;
    private final String prettyName;
    private final int number;
    private final int numberOfBuildsToShow;

    public TestReportInfo(String buildUrl, String partOfReportHomePageUrl, String prettyName, int numberOfBuildsToShow) {
        this.url = buildUrl;
        this.partOfReportHomePageUrl = partOfReportHomePageUrl;
        this.prettyName = prettyName;
        this.name = extractNameFrom(buildUrl);
        this.number = extractNumberFrom(buildUrl);
        if (numberOfBuildsToShow == 0) {
            throw new IllegalArgumentException("numberOfBuildsToShow cannot be 0");
        }
        this.numberOfBuildsToShow = numberOfBuildsToShow;
    }

    private int extractNumberFrom(String url) {
        return Integer.valueOf(url.replaceAll("^.*/job/" + extractNameFrom(url) + "/", "").replaceAll("/", ""));
    }

    private String extractNameFrom(String url) {
        return url.replaceAll("^.*/job/", "").replaceAll("/.*$", "");
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[0]);
    }

    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public String toString() {
        return "buildUrl: " + url + " reportDirectory: " + partOfReportHomePageUrl + " buildPrettyName: " + prettyName;
    }

    public String url() {
        return url;
    }

    public String name() {
        return name;
    }

    public int number() {
        return number;
    }

    public String partOfReportHomePageUrl() {
        return partOfReportHomePageUrl;
    }

    public String prettyName() {
        return prettyName;
    }

    public int numberOfBuildsToShow() {
        return numberOfBuildsToShow;
    }
}
