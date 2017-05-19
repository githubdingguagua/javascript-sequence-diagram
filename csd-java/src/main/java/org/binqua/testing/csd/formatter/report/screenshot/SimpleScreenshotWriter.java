package org.binqua.testing.csd.formatter.report.screenshot;

import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.awt.image.BufferedImage;

public class SimpleScreenshotWriter implements ScreenshotWriter {

    private ReportFileNames reportFileNames;
    private PageSourceWriter pageSourceWriter;
    private ImageWriter imageWriter;
    private ImageWriter thumbnailWriter;
    private ImageCropper imageCropper;

    public SimpleScreenshotWriter(ReportFileNames reportFileNames,
                                  PageSourceWriter pageSourceWriter,
                                  ImageWriter imageWriter,
                                  ImageWriter thumbnailWriter,
                                  ImageCropper imageCropper) {
        this.reportFileNames = reportFileNames;
        this.pageSourceWriter = pageSourceWriter;
        this.imageWriter = imageWriter;
        this.thumbnailWriter = thumbnailWriter;
        this.imageCropper = imageCropper;
    }

    @Override
    public void write(IdentifiableScreenshot identifiableScreenshot) {
        final Screenshot screenshot = identifiableScreenshot.screenshot();
        final String featureId = screenshot.featureId();
        final String scenarioId = screenshot.scenarioId();

        pageSourceWriter.write(reportFileNames.screenshotPageSourceFile(featureId, scenarioId, identifiableScreenshot.id()), screenshot.pageSource());

        final BufferedImage croppedImage = imageCropper.crop(screenshot.image());

        imageWriter.write(reportFileNames.screenshotImageFile(featureId, scenarioId, identifiableScreenshot.id()), croppedImage);

        thumbnailWriter.write(reportFileNames.screenshotThumbnailImageFile(featureId, scenarioId, identifiableScreenshot.id()), croppedImage);
    }

}
