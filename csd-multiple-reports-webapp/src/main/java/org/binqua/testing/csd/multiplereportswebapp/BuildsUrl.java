package org.binqua.testing.csd.multiplereportswebapp;

import java.util.List;

public class BuildsUrl {

    private List<TestReportInfo> testReportInfos;

    public BuildsUrl(List<TestReportInfo> testReportInfos) {
        this.testReportInfos = testReportInfos;
    }

    public List<TestReportInfo> testReportInfoList() {
        return testReportInfos;
    }

}
