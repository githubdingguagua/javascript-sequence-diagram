package org.binqua.testing.csd.formatter.report.conversation;

import org.junit.Test;

import org.binqua.testing.csd.cucumberreports.MustacheReportPrinter;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeatureReportsWriterImplTest {

    private final MustacheReportPrinter mustacheReportPrinter = mock(MustacheReportPrinter.class);
    private final JsonFeatureReportFactory jsonFeatureReportFactory = mock(JsonFeatureReportFactory.class);
    private final FeatureReportWriter featureReportWriter = mock(FeatureReportWriter.class);
    private final ReportFileNames reportFileNames = mock(ReportFileNames.class);
    private final ToJson sameJson = mock(ToJson.class);
    private final Feature aFeature = mock(Feature.class);

    @Test
    public void writesTheMustacheFeatureReportAndTheJsonFeatureReport() throws Exception {

        final String featureName = "a Feature name";
        final File featureReportDir = new File("someReportDirForAGivenFeatureId");

        when(reportFileNames.featureReportDir(featureName)).thenReturn(featureReportDir);

        when(jsonFeatureReportFactory.createJsonFeatureReport(aFeature)).thenReturn(sameJson);

        new FeatureReportsWriterImpl(reportFileNames, mustacheReportPrinter, featureReportWriter, jsonFeatureReportFactory).write(featureName, aFeature);

        verify(mustacheReportPrinter).print(aFeature, featureReportDir);
        verify(featureReportWriter).write(featureName, sameJson);

    }
}