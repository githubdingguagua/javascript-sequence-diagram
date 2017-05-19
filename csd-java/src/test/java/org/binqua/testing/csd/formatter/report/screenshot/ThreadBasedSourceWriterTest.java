package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

public class ThreadBasedSourceWriterTest {

    private static final String THE_CONTENT_TO_BE_WRITTEN = "some content";

    private final PageSourceWriter pageSourceWriter = mock(PageSourceWriter.class);
    private final File aScreenshotPageSourceFile = mock(File.class);
    private final MyExecutor myExecutor = new MyExecutor();

    private final PageSourceWriter underTest = new ThreadBasedSourceWriter(myExecutor, pageSourceWriter);

    @Test
    public void givenThatTheRunnableInsideTheExecutorRunsThenScreenshotWriterIsExercise() throws Exception {

        underTest.write(aScreenshotPageSourceFile, THE_CONTENT_TO_BE_WRITTEN);

        myExecutor.getTheRunnable().run();

        verify(pageSourceWriter).write(aScreenshotPageSourceFile, THE_CONTENT_TO_BE_WRITTEN);
    }

    @Test
    public void givenThatTheRunnableInsideTheExecutorDoesNotRunThenScreenshotWriterIsNotExercise() throws Exception {

        underTest.write(aScreenshotPageSourceFile, THE_CONTENT_TO_BE_WRITTEN);

        verifyZeroInteractions(pageSourceWriter);

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