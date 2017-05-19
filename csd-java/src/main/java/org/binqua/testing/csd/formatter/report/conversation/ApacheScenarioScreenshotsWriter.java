package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;

public class ApacheScenarioScreenshotsWriter implements ScenarioScreenshotsWriter {

    private final ReportFileNames reportFileNames;
    private final JsonWriter jsonWriter;

    public ApacheScenarioScreenshotsWriter(JsonWriter jsonWriter, ReportFileNames reportFileNames) {
        this.jsonWriter = jsonWriter;
        this.reportFileNames = reportFileNames;
    }

    @Override
    public void write(String cucumberFeatureId, String cucumberScenarioId, ScenarioScreenshots scenarioScreenshots) {
        jsonWriter.write(reportFileNames.scenarioScreenshotsFile(cucumberFeatureId, cucumberScenarioId), scenarioScreenshots);
    }
}
