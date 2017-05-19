package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.common.ManifestReader;
import org.binqua.testing.csd.formatter.report.conversation.DateTimeCreator;
import org.binqua.testing.csd.formatter.report.conversation.DestinationDirectoryFactoryNameDateTimeAppender;

import java.net.URL;
import java.util.Properties;

class ConfigurationFactory {

    private final SystemPropertiesConfiguration configuration;

    ConfigurationFactory(URL htmlReportDir, Properties properties) {
        final UrlConverter urlConverter = new UrlConverter(htmlReportDir);
        final DestinationDirectoryFactoryNameDateTimeAppender destinationDirectoryFactoryName = new DestinationDirectoryFactoryNameDateTimeAppender(new DateTimeCreator());
        configuration = new SystemPropertiesConfiguration(properties, urlConverter, destinationDirectoryFactoryName , new ManifestReader());
    }

    Configuration createConfiguration() {
        return configuration;
    }

}
