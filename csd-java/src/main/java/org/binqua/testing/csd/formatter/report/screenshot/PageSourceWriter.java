package org.binqua.testing.csd.formatter.report.screenshot;

import java.io.File;

public interface PageSourceWriter {

    void write(File screenshotPageSourceFile, String pageSourceContent);

}
