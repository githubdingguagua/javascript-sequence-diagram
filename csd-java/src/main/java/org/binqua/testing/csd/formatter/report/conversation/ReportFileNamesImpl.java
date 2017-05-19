package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.util.IdGenerator;

import java.io.File;

public class ReportFileNamesImpl implements ReportFileNames {

    private final IdGenerator featureIdGenerator;
    private final IdGenerator scenarioIdGenerator;
    private final File featuresDirectory;
    private final File destinationDirectory;

    public ReportFileNamesImpl(File destinationDirectory, IdGenerator featureIdGenerator, IdGenerator scenarioIdGenerator) {
        this.destinationDirectory = destinationDirectory;
        this.featureIdGenerator = featureIdGenerator;
        this.scenarioIdGenerator = scenarioIdGenerator;
        this.featuresDirectory = new File(destinationDirectory, "features");
    }

    @Override
    public File conversationFile(String featureId) {
        return new File(featureReportDir(featureId), "conversation.json");
    }

    @Override
    public File featureReportDir(String featureName) {
        return new File(featuresDirectory, featureIdGenerator.idOf(featureName));
    }

    @Override
    public File featuresReportDir() {
        return featuresDirectory;
    }

    @Override
    public File featureReportFile(String featureId) {
        return new File(featureReportDir(featureId), "featureReport.json");
    }

    @Override
    public File scenarioScreenshotsFile(String cucumberFeatureId, String cucumberScenarioId) {
        final File scenarioDir = scenarioDir(cucumberFeatureId, cucumberScenarioId);
        return new File(scenarioDir, "screenshots.json");
    }

    @Override
    public File reportDirectory() {
        return destinationDirectory;
    }

    @Override
    public File screenshotPageSourceFile(String featureCucumberId, String scenarioCucumberId, String screenshotIdentifier) {
        final File scenarioDir = scenarioDir(featureCucumberId, scenarioCucumberId);
        final File htmlDir = new File(scenarioDir, "html");
        return new File(htmlDir, screenshotIdentifier + ".html");
    }

    private File scenarioDir(String featureCucumberId, String scenarioCucumberId) {
        final File featureDir = new File(featuresReportDir(), featureIdGenerator.idOf(featureCucumberId));
        return new File(featureDir, scenarioIdGenerator.idOf(scenarioCucumberId));
    }

    @Override
    public File screenshotImageFile(String featureCucumberId, String scenarioCucumberId, String screenshotIdentifier) {
        return imagedLocation(featureCucumberId, scenarioCucumberId, screenshotIdentifier, "cropped", ".png");
    }

    private File imagedLocation(String featureCucumberId, String scenarioCucumberId, String screenshotIdentifier, String cropped, String s) {
        final File scenarioDir = scenarioDir(featureCucumberId, scenarioCucumberId);
        final File imagesDir = new File(scenarioDir, "images");
        final File croppedImagesDir = new File(imagesDir, cropped);
        return new File(croppedImagesDir, screenshotIdentifier + s);
    }

    @Override
    public File screenshotThumbnailImageFile(String featureCucumberId, String scenarioCucumberId, String screenshotIdentifier) {
        return imagedLocation(featureCucumberId, scenarioCucumberId, screenshotIdentifier, "thumbnails", ".png");
    }

}
