package org.binqua.testing.csd.formatter.report.screenshot;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ApachePageSourceWriter implements PageSourceWriter {

    @Override
    public void write(File screenshotPageSourceFile, String pageSourceContent) {
        try {
            FileUtils.write(screenshotPageSourceFile, pageSourceContent);
        } catch (IOException e) {
            throw new IllegalStateException("cannot write file " + screenshotPageSourceFile, e);
        }
    }
}
