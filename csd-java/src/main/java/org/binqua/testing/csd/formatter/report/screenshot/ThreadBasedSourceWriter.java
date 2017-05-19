package org.binqua.testing.csd.formatter.report.screenshot;

import java.io.File;
import java.util.concurrent.Executor;

public class ThreadBasedSourceWriter implements PageSourceWriter {

    private final Executor executor;
    private final PageSourceWriter pageSourceWriter;

    public ThreadBasedSourceWriter(Executor executor, PageSourceWriter pageSourceWriter) {
        this.executor = executor;
        this.pageSourceWriter = pageSourceWriter;
    }

    @Override
    public void write(File screenshotPageSourceFile, String pageSourceContent) {
        executor.execute(() -> pageSourceWriter.write(screenshotPageSourceFile, pageSourceContent));
    }

}

