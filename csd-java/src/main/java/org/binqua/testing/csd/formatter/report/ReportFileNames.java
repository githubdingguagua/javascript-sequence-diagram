package org.binqua.testing.csd.formatter.report;

import java.io.File;

public interface ReportFileNames {

    File conversationFile(String featureId);

    File featureReportDir(String featureId);

    File featuresReportDir();

    File reportDirectory();

    File screenshotPageSourceFile(String featureId, String scenarioId, String screenshotIdentifier);

    File screenshotImageFile(String featureId, String scenarioId, String screenshotIdentifier);

    File screenshotThumbnailImageFile(String featureId, String scenarioId, String screenshotIdentifier);

    File featureReportFile(String featureId);

    File scenarioScreenshotsFile(String cucumberFeatureId, String cucumberScenarioId);
}
