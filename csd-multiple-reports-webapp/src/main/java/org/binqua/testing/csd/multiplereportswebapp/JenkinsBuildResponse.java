package org.binqua.testing.csd.multiplereportswebapp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JenkinsBuildResponse {

    private final String longText;
    private final boolean isTestSuccessful;
    private final String buildUrl;
    private JENKINS_BUILD_STATUS buildStatus;
    private final String name;
    private final int number;
    private final String partOfReportHomePageUrl;
    private final String jobPrettyName;

    private JenkinsBuildResponse(JENKINS_BUILD_STATUS buildStatus, TestReportInfo testReportInfo) {
        this.buildStatus = buildStatus;
        this.name = testReportInfo.name();
        this.number = testReportInfo.number();
        this.longText = "#" + number() + " It looks like jenkins had problems";
        this.isTestSuccessful = false;
        this.partOfReportHomePageUrl = testReportInfo.partOfReportHomePageUrl();
        this.buildUrl = testReportInfo.url();
        this.jobPrettyName = testReportInfo.prettyName();
    }

    private JenkinsBuildResponse(JENKINS_BUILD_STATUS buildStatus, JenkinsBuildSummary jenkinsBuildSummary) {
        this.buildStatus = buildStatus;
        this.name = jenkinsBuildSummary.name();
        this.number = jenkinsBuildSummary.number();
        this.longText = jenkinsBuildSummary.longText();
        this.isTestSuccessful = jenkinsBuildSummary.testOutcome();
        this.partOfReportHomePageUrl = jenkinsBuildSummary.partOfReportHomePageUrl();
        this.buildUrl = "";
        this.jobPrettyName = jenkinsBuildSummary.jobPrettyName();
    }

    public static JenkinsBuildResponse successful(JenkinsBuildSummary jenkinsBuildSummary) {
        return new JenkinsBuildResponse(JENKINS_BUILD_STATUS.SUCCESS, jenkinsBuildSummary);
    }

    public static JenkinsBuildResponse failed(TestReportInfo testReportInfo) {
        return new JenkinsBuildResponse(JENKINS_BUILD_STATUS.FAILED, testReportInfo);
    }

    public String name() {
        return name;
    }

    public int number() {
        return number;
    }

    public String id() {
        return name() + "-" + number();
    }

    public String longText() {
        return longText;
    }

    public String partOfReportHomePageUrl() {
        return partOfReportHomePageUrl;
    }

    public boolean isSuccessful() {
        return buildStatus == JENKINS_BUILD_STATUS.SUCCESS;
    }

    public boolean isTestSuccessful() {
        return isTestSuccessful;
    }

    public String buildUrl() {
        return buildUrl;
    }

    public String jobPrettyName() {
        return jobPrettyName;
    }

    enum JENKINS_BUILD_STATUS {
        SUCCESS,
        FAILED
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[0]);
    }

    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
