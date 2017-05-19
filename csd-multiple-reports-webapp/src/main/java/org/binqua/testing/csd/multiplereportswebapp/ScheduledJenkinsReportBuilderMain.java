package org.binqua.testing.csd.multiplereportswebapp;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import org.binqua.testing.csd.common.ManifestReader;

import java.util.concurrent.Executors;

public class ScheduledJenkinsReportBuilderMain {

    public static void main(String[] args) {
        final DateTimeFormatter dateTimeFormatter = new JodaTimeFormatterImpl();

        final SupportNotifierFactory supportNotifierFactory = ScheduledJenkinsReportBuilderMain::createSupportNotifier;
        final ReportBuilderConfiguration configuration = new OptionsReportBuilderConfiguration(new ManifestReader(), args);
        new JenkinsReportBuilder(
            configuration,
            Executors.newScheduledThreadPool(1),
            new ReportRunnable(
                new FilteredByNumberOfBuildsReportDirectoryScannerDecorator(
                    new ReportDirectoryScannerImpl(
                        supportNotifierFactory,
                        configuration.reportDirectoryRegexMatcher(),
                        configuration.directoryToScan())
                ),
                new JenkinsBuildReportResource(),
                new MustacheBuildsSummaryGenerator(new PrettyFormatSummaryFormatter(new JsonBuildsSummaryFormatter(configuration)), configuration, dateTimeFormatter, DateTime::new),
                new AssetsWriter(configuration.reportDirectoryRoot()),
                supportNotifierFactory,
                configuration.scanPeriodInSecs(),
                configuration.csdBuildNumber()),
            supportNotifierFactory).run();
    }

    private static SupportNotifier createSupportNotifier(final Class<?> clazz) {
        return new SupportNotifier() {
            @Override
            public void info(String message) {
                LoggerFactory.getLogger(clazz).info(message);
            }

            @Override
            public void info(String message, Exception e) {
                LoggerFactory.getLogger(clazz).info(message, e);
            }
        };
    }

}
