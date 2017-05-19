package org.binqua.testing.csd.formatter.report.conversation;

import org.junit.Test;
import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeatureReportWriterImplTest {

    private static final String CUCUMBER_FEATURE_ID = "abc";

    private ReportFileNames reportFileNames = mock(ReportFileNames.class);
    private JsonWriter jsonWriter = mock(JsonWriter.class);
    private ToJson someJson = mock(ToJson.class);

    @Test
    public void writesTheRightReportFile() throws Exception {

        final File reportFile = new File("");

        when(reportFileNames.featureReportFile(CUCUMBER_FEATURE_ID)).thenReturn(reportFile);

        new FeatureReportWriterImpl(reportFileNames, jsonWriter).write(CUCUMBER_FEATURE_ID, someJson);

        verify(jsonWriter).write(reportFile, someJson);

    }

}