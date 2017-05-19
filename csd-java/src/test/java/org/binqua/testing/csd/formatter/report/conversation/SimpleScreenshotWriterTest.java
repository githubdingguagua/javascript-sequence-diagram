package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.screenshot.*;
import org.junit.Test;
import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.mockito.Mockito.*;

public class SimpleScreenshotWriterTest {

    private final ReportFileNames reportFileNames = mock(ReportFileNames.class);
    private final PageSourceWriter pageSourceWriter = mock(PageSourceWriter.class);
    private final ImageWriter imageWriter = mock(ImageWriter.class);
    private final ImageWriter thumbnailWriter = mock(ImageWriter.class);
    private final ImageCropper imageCropper = mock(ImageCropper.class);

    private final ScreenshotWriter screenshotWriter = new SimpleScreenshotWriter(
            reportFileNames,
            pageSourceWriter,
            imageWriter,
            thumbnailWriter,
            imageCropper
    );

    @Test
    public void writeDelegatesToPageSourceImageAndThumbnailWriter() throws Exception {
        final Screenshot aScreenshot = new Screenshot(SimpleBrowserEvent.afterNavigateTo("someUrl"), "scenarioName", "feature;scenarioName", new byte[0], "pageSourceContent", "pageUrl", "pageTitle");
        final IdentifiableScreenshot identifiableScreenshot = new IdentifiableScreenshot(aScreenshot, "1");

        final BufferedImage theCroppedImage = mock(BufferedImage.class);

        when(imageCropper.crop(aScreenshot.image())).thenReturn(theCroppedImage);

        final File screenshotPageSourceFile = new File("screenshotPageSourceFile");
        when(reportFileNames.screenshotPageSourceFile(aScreenshot.featureId(), aScreenshot.scenarioId(), identifiableScreenshot.id())).thenReturn(screenshotPageSourceFile);

        final File screenshotImageFile = new File("screeshotImageFile");
        when(reportFileNames.screenshotImageFile(aScreenshot.featureId(), aScreenshot.scenarioId(), identifiableScreenshot.id())).thenReturn(screenshotImageFile);

        final File screenshotThumbnailFile = new File("screenshotThumbnailFile");
        when(reportFileNames.screenshotThumbnailImageFile(aScreenshot.featureId(), aScreenshot.scenarioId(), identifiableScreenshot.id())).thenReturn(screenshotThumbnailFile);

        screenshotWriter.write(identifiableScreenshot);

        verify(pageSourceWriter).write(screenshotPageSourceFile, aScreenshot.pageSource());
        verify(imageWriter).write(screenshotImageFile, theCroppedImage);
        verify(thumbnailWriter).write(screenshotThumbnailFile, theCroppedImage);

    }

}