package org.binqua.testing.csd.multiplereportswebapp;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.binqua.testing.csd.common.ManifestReader;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.apache.commons.cli.Option.builder;

class OptionsReportBuilderConfiguration implements ReportBuilderConfiguration {

    private static final String DIRECTORY_TO_SCAN = "directoryToScan";
    private static final String REPORT_DIR = "reportDir";
    private static final String SCAN_PERIOD_IN_SECS = "scanPeriodInSecs";
    private static final String REPORT_DIRECTORY_REGEX_MATCHER = "reportDirectoryRegexMatcher";
    private static final String REPORT_HOMEPAGE_URL_PATTERN = "reportHomePageUrlPattern";

    private CommandLine line;
    private ManifestReader manifestReader;

    OptionsReportBuilderConfiguration(ManifestReader manifestReader, String[] args) {
        this.manifestReader = manifestReader;

        final Options options = new Options();
        options.addOption(builder(DIRECTORY_TO_SCAN).required().hasArg().desc("directory where Jenkins send the report to").build());
        options.addOption(builder(REPORT_DIR).required().hasArg().desc("directory where the main report web app is going to be created").build());
        options.addOption(builder(SCAN_PERIOD_IN_SECS).hasArg().desc("interval of seconds between directoryToScan is going to be scanned. Default is 10 secs").build());
        options.addOption(
            builder(REPORT_DIRECTORY_REGEX_MATCHER).hasArg().desc("Regex to find directories inside directoryToScan to scan for build yml file. Default is <cucumber-screenshots-report-.*-\\d+>")
                .build());
        options.addOption(builder(REPORT_HOMEPAGE_URL_PATTERN).hasArg()
                              .desc("Report home page url pattern allow to build the url of the report index page. Default is <http://devopstools.uc/jenkins_data/$/index.html>").build());

        try {
            line = new DefaultParser().parse(options, args);
        } catch (ParseException exp) {
            HelpFormatter formatter = new HelpFormatter();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            formatter.printHelp(printWriter, 100, " ", " ", options, 200, 200, "");
            throw new IllegalArgumentException(stringWriter.toString());
        }

    }

    @Override
    public File directoryToScan() {
        return new File(line.getOptionValue(DIRECTORY_TO_SCAN));
    }

    @Override
    public File reportDirectoryRoot() {
        return new File(line.getOptionValue(REPORT_DIR));
    }

    @Override
    public int scanPeriodInSecs() {
        return Integer.parseInt(line.getOptionValue(SCAN_PERIOD_IN_SECS, "10"));
    }

    @Override
    public String reportDirectoryRegexMatcher() {
        return line.getOptionValue(REPORT_DIRECTORY_REGEX_MATCHER, "cucumber-screenshots-report-.*-\\d+");
    }

    @Override
    public String reportHomePageUrlPattern() {
        return line.getOptionValue(REPORT_HOMEPAGE_URL_PATTERN, "http://devopstools.uc/jenkins_data/$/index.html");
    }

    @Override
    public String csdBuildNumber() {
        final Optional<String> stringOptional = manifestReader.buildNumberOfManifestContainingAttributeValue("csd-multiple-reports-webapp");
        return stringOptional.isPresent() ? stringOptional.get() : "UNKNOWN";
    }

    @Override
    public String csdHomePageUrl() {
        final Optional<String> stringOptional = manifestReader.buildNumberOfManifestContainingAttributeValue("csd-multiple-reports-webapp");
        final String defaultValue = "http://jenkins.uc/job/cucumber-sequence-diagram";
        return stringOptional.isPresent() ? defaultValue + "/" + stringOptional.get() : defaultValue;
    }
}
