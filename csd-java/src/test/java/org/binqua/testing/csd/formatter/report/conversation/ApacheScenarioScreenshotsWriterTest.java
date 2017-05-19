package org.binqua.testing.csd.formatter.report.conversation;

import org.junit.Test;
import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;

import java.io.File;

import static org.mockito.Mockito.*;

public class ApacheScenarioScreenshotsWriterTest {

    private final ScenarioScreenshots scenarioScreenshots = mock(ScenarioScreenshots.class);
    private final ReportFileNames reportFileNames = mock(ReportFileNames.class);
    private final JsonWriter jsonWriter = mock(JsonWriter.class);
    private final File expectedScreenshotsFile = mock(File.class);

    @Test
    public void writeScreenshotsDelegatesToJsonWriter() throws Exception {
        final String someCucumberFeatureId = "aaa";
        final String someCucumberScenarioId = "bbb";

        when(reportFileNames.scenarioScreenshotsFile(someCucumberFeatureId, someCucumberScenarioId)).thenReturn(expectedScreenshotsFile);

        final ScenarioScreenshotsWriter scenarioScreenshotsWriter = new ApacheScenarioScreenshotsWriter(jsonWriter, reportFileNames);

        scenarioScreenshotsWriter.write(someCucumberFeatureId, someCucumberScenarioId, scenarioScreenshots);

        verify(jsonWriter).write(expectedScreenshotsFile, scenarioScreenshots);

    }

}