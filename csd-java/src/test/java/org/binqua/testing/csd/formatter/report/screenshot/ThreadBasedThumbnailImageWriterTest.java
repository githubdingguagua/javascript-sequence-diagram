package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

public class ThreadBasedThumbnailImageWriterTest {

    private final MyExecutor myExecutor = new MyExecutor();
    private final ImageWriter imageWriter = mock(ImageWriter.class);
    private final BufferedImage aBufferedImage = mock(BufferedImage.class);
    private final File aScreenshotImageFile = mock(File.class);

    private final ThreadBasedThumbnailImageWriter underTest = new ThreadBasedThumbnailImageWriter(myExecutor, imageWriter);

    @Test
    public void givenThatTheRunnableInsideTheExecutorRunsThenScreenshotWriterIsExercise() throws Exception {

        underTest.write(aScreenshotImageFile, aBufferedImage);

        myExecutor.getTheRunnable().run();

        verify(imageWriter).write(aScreenshotImageFile, aBufferedImage);
    }

    @Test
    public void givenThatTheRunnableInsideTheExecutorDoesNotRunThenImageWriterIsNotExercise() throws Exception {

        underTest.write(aScreenshotImageFile, aBufferedImage);

        verifyZeroInteractions(imageWriter);

    }

    class MyExecutor implements Executor {

        private Runnable command;

        @Override
        public void execute(Runnable command) {
            this.command = command;
        }

        public Runnable getTheRunnable() {
            return command;
        }
    }

}