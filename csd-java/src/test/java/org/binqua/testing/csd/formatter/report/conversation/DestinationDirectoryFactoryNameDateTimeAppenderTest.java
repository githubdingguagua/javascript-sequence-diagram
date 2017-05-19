package org.binqua.testing.csd.formatter.report.conversation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import org.binqua.testing.csd.formatter.external.Configuration;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DestinationDirectoryFactoryNameDateTimeAppenderTest {

    private static final File DESTINATION_DIR = new File("someDir");

    private final DateTimeCreator dateTimeCreator = mock(DateTimeCreator.class);
    private final Configuration configuration = mock(Configuration.class);


    @Test
    public void directoryNameHasDateTimeAppendedToIt() throws Exception {

        when(configuration.makeEveryReportDirUnique()).thenReturn(true);

        when(dateTimeCreator.now()).thenReturn(dateTimeFrom("08-Aug-12 12.00.00.001"), dateTimeFrom("09-Sep-12 12.00.01.001"));

        assertThat(new DestinationDirectoryFactoryNameDateTimeAppender(dateTimeCreator).createDirectoryNameFrom(DESTINATION_DIR).getName(), is("someDir_08-Aug-2012_12-00-00-001"));
        assertThat(new DestinationDirectoryFactoryNameDateTimeAppender(dateTimeCreator).createDirectoryNameFrom(DESTINATION_DIR).getName(), is("someDir_09-Sep-2012_12-00-01-001"));

    }

    @Test
    public void directoryNameIsCached() throws Exception {

        when(dateTimeCreator.now()).thenReturn(dateTimeFrom("08-Aug-12 12.00.00.001"));

        final DestinationDirectoryFactoryNameDateTimeAppender underTest = new DestinationDirectoryFactoryNameDateTimeAppender(dateTimeCreator);
        assertThat(underTest.createDirectoryNameFrom(DESTINATION_DIR).getName(), is("someDir_08-Aug-2012_12-00-00-001"));
        assertThat(underTest.createDirectoryNameFrom(DESTINATION_DIR).getName(), is("someDir_08-Aug-2012_12-00-00-001"));
        assertThat(underTest.createDirectoryNameFrom(DESTINATION_DIR).getName(), is("someDir_08-Aug-2012_12-00-00-001"));

    }

    private static DateTime dateTimeFrom(String input) {
        return DateTime.parse(input, DateTimeFormat.forPattern("dd-MMM-yy hh.mm.ss.SSS"));
    }

}