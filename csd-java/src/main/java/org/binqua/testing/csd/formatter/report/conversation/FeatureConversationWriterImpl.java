package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.ReportFileNames;

public class FeatureConversationWriterImpl implements FeatureReportWriter {

    private ReportFileNames reportFileNames;
    private JsonWriter jsonWriter;

    public FeatureConversationWriterImpl(ReportFileNames reportFileNames, JsonWriter jsonWriter) {
        this.reportFileNames = reportFileNames;
        this.jsonWriter = jsonWriter;
    }

    @Override
    public void write(String cucumberFeatureId, ToJson featureConversation) {
        jsonWriter.write(reportFileNames.conversationFile(cucumberFeatureId), featureConversation);
    }

}
