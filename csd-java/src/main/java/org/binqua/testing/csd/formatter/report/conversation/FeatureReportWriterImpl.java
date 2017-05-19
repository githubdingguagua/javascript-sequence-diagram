package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.ReportFileNames;

class FeatureReportWriterImpl implements FeatureReportWriter {

    private ReportFileNames reportFileNames;
    private JsonWriter jsonWriter;

    FeatureReportWriterImpl(ReportFileNames reportFileNames, JsonWriter jsonWriter) {
        this.reportFileNames = reportFileNames;
        this.jsonWriter = jsonWriter;
    }

    @Override
    public void write(String featureId, ToJson featureConversation) {
        jsonWriter.write(reportFileNames.featureReportFile(featureId), featureConversation);
    }

}