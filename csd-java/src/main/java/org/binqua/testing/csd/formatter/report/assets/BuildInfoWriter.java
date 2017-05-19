package org.binqua.testing.csd.formatter.report.assets;

import org.binqua.testing.csd.formatter.external.Configuration;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class BuildInfoWriter implements AssetsWriter {

    private final Configuration configuration;

    public BuildInfoWriter(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void write() {

        String fileContent = format("buildUrl : %s\nbuildPrettyName : %s\nnumberOfBuildsToShow : %s",
                                    configuration.buildUrl(),
                                    configuration.buildPrettyName(),
                                    configuration.numberOfBuildsToShow()
                                    );

        try {
            writeStringToFile(new File(configuration.reportDestinationDirectory(), "buildInfo.yml"), fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
