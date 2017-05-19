package org.binqua.testing.csd.formatter.report.featuremenu;

import org.apache.commons.io.FileUtils;

import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.report.conversation.ToJson;

import java.io.File;
import java.io.IOException;

public class ApacheUtilFeatureMenuWriter implements FeatureMenuWriter {

    private FeatureMenuContentGenerator featureMenuContentGenerator;
    private ReportFileNames reportFileNames;

    public ApacheUtilFeatureMenuWriter(FeatureMenuContentGenerator featureMenuContentGenerator, ReportFileNames reportFileNames) {
        this.featureMenuContentGenerator = featureMenuContentGenerator;
        this.reportFileNames = reportFileNames;
    }

    @Override
    public void write(ToJson someJson) {
        try {
            final File destinationFile = new File(reportFileNames.reportDirectory(), "testsMenu.js");
            FileUtils.write(destinationFile, featureMenuContentGenerator.content(someJson));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
