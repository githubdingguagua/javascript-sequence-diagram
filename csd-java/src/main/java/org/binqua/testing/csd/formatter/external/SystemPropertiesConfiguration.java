package org.binqua.testing.csd.formatter.external;

import org.apache.cxf.common.util.StringUtils;

import org.binqua.testing.csd.common.ManifestReader;
import org.binqua.testing.csd.formatter.report.conversation.DestinationDirectoryFactoryNameDateTimeAppender;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SystemPropertiesConfiguration implements Configuration {

    private Properties systemProperties;
    private UrlConverter htmlReportDir;
    private DestinationDirectoryFactoryNameDateTimeAppender destinationDirectory;
    private ManifestReader manifestReader;

    SystemPropertiesConfiguration(Properties systemProperties,
                                  UrlConverter htmlReportDir,
                                  DestinationDirectoryFactoryNameDateTimeAppender destinationDirectory,
                                  ManifestReader manifestReader) {
        this.systemProperties = systemProperties;
        this.htmlReportDir = htmlReportDir;
        this.destinationDirectory = destinationDirectory;
        this.manifestReader = manifestReader;
    }

    public String buildUrl() {
        return systemProperties.getProperty("csdBuildUrl");
    }

    @Override
    public String buildPrettyName() {
        return systemProperties.getProperty("csdBuildPrettyName");
    }

    @Override
    public boolean isGenerateSequenceDiagramEnabled() {
        return isEnabled(systemProperties.getProperty("generateSequenceDiagram"));
    }

    @Override
    public boolean isAccessibleFromMultipleReportsPage() {
        return org.apache.commons.lang3.StringUtils.isNotEmpty(buildUrl());
    }

    @Override
    public File reportDestinationDirectory() {
        return Boolean.valueOf(systemProperties.getProperty("csdMakeEveryReportDirUnique")) ? destinationDirectory.createDirectoryNameFrom(htmlReportDir.file()) : htmlReportDir.file();
    }

    @Override
    public Map<String, Integer> clusterNamePortMap() {
        return isGenerateSequenceDiagramEnabled() ? htmlReportDir.clusterNamePortMap() : Collections.emptyMap();
    }

    @Override
    public String buildNumber() {
        final Matcher matcher = findDigitsAtTheEnd().matcher(buildUrl());
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    private Pattern findDigitsAtTheEnd() {
        return Pattern.compile("^.+/(\\d+)/?$");
    }

    @Override
    public String multipleReportsHomeUrl() {
        final String multipleReportsHomeUrl = systemProperties.getProperty("multipleReportsHomeUrl");
        return StringUtils.isEmpty(multipleReportsHomeUrl) ? "http://devopstools.uc/jenkins_data/dwp_multiple_reports/index.html" : multipleReportsHomeUrl;
    }

    @Override
    public String csdBuildNumber() {
        final Optional<String> stringOptional = manifestReader.buildNumberOfManifestContainingAttributeValue("csd-java");
        return stringOptional.isPresent() ? stringOptional.get() : "UNKNOWN";
    }

    @Override
    public boolean makeEveryReportDirUnique() {
        return Boolean.getBoolean(systemProperties.getProperty("csdMakeEveryReportDirUnique"));
    }

    private boolean isEnabled(String generateBuildReportEnabled) {
        return "enabled".equals(generateBuildReportEnabled);
    }

    @Override
    public int numberOfBuildsToShow() {
        final String csdNumberOfBuildToShow = systemProperties.getProperty("csdNumberOfBuildsToShow");
        return StringUtils.isEmpty(csdNumberOfBuildToShow) ? 10 : Integer.valueOf(csdNumberOfBuildToShow);
    }
}
