package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.cucumberreports.MustacheReportPrinter;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.report.ReportFileNames;

public class FeatureReportsWriterImpl implements FeatureIndexWriter {

    private ReportFileNames reportFileNames;
    private MustacheReportPrinter mustacheReportPrinter;
    private FeatureReportWriter featureReportWriter;
    private JsonFeatureReportFactory jsonFeatureReportFactory;

    public FeatureReportsWriterImpl(ReportFileNames reportFileNames,
                                    MustacheReportPrinter mustacheReportPrinter,
                                    FeatureReportWriter featureReportWriter,
                                    JsonFeatureReportFactory jsonFeatureReportFactory
    ) {
        this.reportFileNames = reportFileNames;
        this.mustacheReportPrinter = mustacheReportPrinter;
        this.featureReportWriter = featureReportWriter;
        this.jsonFeatureReportFactory = jsonFeatureReportFactory;
    }

    @Override
    public void write(String cucumberFeatureId, Feature feature) {
        mustacheReportPrinter.print(feature, reportFileNames.featureReportDir(cucumberFeatureId));
        featureReportWriter.write(cucumberFeatureId, jsonFeatureReportFactory.createJsonFeatureReport(feature));
    }
}
