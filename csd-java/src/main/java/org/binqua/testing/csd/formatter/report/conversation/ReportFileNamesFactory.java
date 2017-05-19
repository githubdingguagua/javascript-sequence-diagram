package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.util.IdGeneratorFactory;
import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.io.File;

public class ReportFileNamesFactory {

    private static File destinationDirectory;

    private static class ReportFileNamesHolder {
        private static final ReportFileNames INSTANCE = new ReportFileNamesImpl(destinationDirectory,
                                                                                IdGeneratorFactory.featureIdGeneratorInstance(),
                                                                                IdGeneratorFactory.scenarioIdGeneratorInstance());
    }

    public static ReportFileNames instance(File destinationDirectory) {
        ReportFileNamesFactory.destinationDirectory = destinationDirectory;
        return ReportFileNamesHolder.INSTANCE;
    }

    public static ReportFileNames instance() {
        if (destinationDirectory != null) {
            throw new IllegalStateException("destination dir not initialised yet");
        }
        return ReportFileNamesHolder.INSTANCE;
    }
}
