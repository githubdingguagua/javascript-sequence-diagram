package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.common.ManifestReader;
import org.binqua.testing.csd.formatter.report.conversation.DateTimeCreator;
import org.binqua.testing.csd.formatter.report.conversation.DestinationDirectoryFactoryNameDateTimeAppender;

import java.util.Properties;

public class ConfigurationFactory {

    private final SystemPropertiesConfiguration configuration;

    public  ConfigurationFactory(Properties properties) {
        final DestinationDirectoryFactoryNameDateTimeAppender destinationDirectoryFactoryName = new DestinationDirectoryFactoryNameDateTimeAppender(new DateTimeCreator());
        configuration = new SystemPropertiesConfiguration(properties, destinationDirectoryFactoryName , new ManifestReader());
    }

    public Configuration createConfiguration() {
        return configuration;
    }

}
