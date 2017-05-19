package org.binqua.testing.csd.formatter.report.conversation;

public interface FeatureReportWriter {

    void write(String cucumberFeatureId, ToJson toJson);

}
