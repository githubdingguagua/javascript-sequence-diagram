package org.binqua.testing.csd.multiplereportswebapp;

public class BuildsSummaryModel {

    private final int reloadInterval;

    public String getCsdHomePageUrl() {
        return csdHomePageUrl;
    }

    public String getCsdBuildNumber() {
        return csdBuildNumber;
    }

    public String getBuildStartTimeFormatted() {
        return buildStartTimeFormatted;
    }

    public int getScanPeriodInSecs() {
        return scanPeriodInSecs;
    }

    public String getNextBuildStartTimeFormatted() {
        return nextBuildStartTimeFormatted;
    }

    public String getBuildsSummary() {
        return buildsSummary;
    }

    public int getReloadInterval() {
        return reloadInterval;
    }

    private final String csdHomePageUrl;
    private final String csdBuildNumber;
    private final String buildStartTimeFormatted;
    private final int scanPeriodInSecs;
    private final String nextBuildStartTimeFormatted;
    private final String buildsSummary;

    public BuildsSummaryModel(String csdHomePageUrl, String csdBuildNumber, String buildStartTimeFormatted, int scanPeriodInSecs, String nextBuildStartTimeFormatted, String buildsSummary) {
        this.csdHomePageUrl = csdHomePageUrl;
        this.csdBuildNumber = csdBuildNumber;
        this.buildStartTimeFormatted = buildStartTimeFormatted;
        this.scanPeriodInSecs = scanPeriodInSecs;
        this.nextBuildStartTimeFormatted = nextBuildStartTimeFormatted;
        this.buildsSummary = buildsSummary;
        this.reloadInterval = scanPeriodInSecs * 1000;
    }

}
