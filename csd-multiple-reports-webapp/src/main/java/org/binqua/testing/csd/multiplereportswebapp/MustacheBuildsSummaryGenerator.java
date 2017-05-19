package org.binqua.testing.csd.multiplereportswebapp;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MustacheBuildsSummaryGenerator implements BuildsSummaryWriter {

    private static final String FEATURE_REPORT_TEMPLATE_FILE = "org/binqua/testing/csd/multiplereportswebapp/buildsSummary.mustache";

    private final ReportBuilderConfiguration reportBuilderConfiguration;
    private final BuildsSummaryFormatter buildsSummaryFormatter;
    private final DateTimeFormatter dateTimeFormatter;
    private final DateTimeGenerator dateTimeGenerator;
    private final Mustache buildsSummaryTemplate;

    public MustacheBuildsSummaryGenerator(BuildsSummaryFormatter buildsSummaryFormatter,
                                          ReportBuilderConfiguration reportBuilderConfiguration,
                                          DateTimeFormatter dateTimeFormatter,
                                          DateTimeGenerator dateTimeGenerator) {
        this.buildsSummaryFormatter = buildsSummaryFormatter;
        this.reportBuilderConfiguration = reportBuilderConfiguration;
        this.dateTimeFormatter = dateTimeFormatter;
        this.dateTimeGenerator = dateTimeGenerator;

        buildsSummaryTemplate = new DefaultMustacheFactory().compile(FEATURE_REPORT_TEMPLATE_FILE);

    }

    @Override
    public void write(List<JenkinsBuildResponse> jenkinsBuildResponses) {
        final DateTime buildStartTime = dateTimeGenerator.now();
        final String buildStartTimeFormatted = dateTimeFormatter.format(buildStartTime);
        final int scanPeriodInSecs = reportBuilderConfiguration.scanPeriodInSecs();
        final String buildEndTimeFormatted = dateTimeFormatter.format(buildStartTime.plusSeconds(scanPeriodInSecs));

        try {
            try (FileWriter buildsSummaryWriter = new FileWriter(new File(reportBuilderConfiguration.reportDirectoryRoot(), "buildsSummary.js"))) {
                buildsSummaryTemplate.execute(buildsSummaryWriter, new BuildsSummaryModel(reportBuilderConfiguration.csdHomePageUrl(),
                                                                                          reportBuilderConfiguration.csdBuildNumber(),
                                                                                          buildStartTimeFormatted,
                                                                                          scanPeriodInSecs,
                                                                                          buildEndTimeFormatted,
                                                                                          buildsSummaryFormatter.format(jenkinsBuildResponses)

                ));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create the report", e);
        }
    }

}
