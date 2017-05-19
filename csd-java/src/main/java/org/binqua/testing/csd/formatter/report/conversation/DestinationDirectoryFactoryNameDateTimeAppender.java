package org.binqua.testing.csd.formatter.report.conversation;

import org.joda.time.format.DateTimeFormat;

import java.io.File;

public class DestinationDirectoryFactoryNameDateTimeAppender implements DestinationDirectoryNameFactory {

    private final String suffix;

    public DestinationDirectoryFactoryNameDateTimeAppender(DateTimeCreator dateTimeCreator) {
        this.suffix = suffix(dateTimeCreator);
    }

    @Override
    public File createDirectoryNameFrom(File destinationDir) {
        return new File(destinationDir.getParentFile(), destinationDir.getName() + suffix);
    }

    private String suffix(DateTimeCreator dateTimeCreator) {
        return "_" + DateTimeFormat.forPattern("dd-MMM-yyyy_hh-mm-ss-SSS").print(dateTimeCreator.now());
    }
}
