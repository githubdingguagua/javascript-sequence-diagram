package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;

public interface ScenarioScreenshotsWriter {
    void write(String cucumberFeatureId, String cucumberScenarioId, ScenarioScreenshots scenarioScreenshots);
}
