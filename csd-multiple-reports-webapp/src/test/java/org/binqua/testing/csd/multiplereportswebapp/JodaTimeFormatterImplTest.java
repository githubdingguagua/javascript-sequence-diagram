package org.binqua.testing.csd.multiplereportswebapp;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.junit.Assert.assertThat;

public class JodaTimeFormatterImplTest {

    @Test
    public void formattedIsOkay() throws Exception {
        assertThat(new JodaTimeFormatterImpl().format(toDateTime("12/03/1972 11:30:01")), is("Sun 12-Mar-1972 @ 11:30:01"));

    }

    private DateTime toDateTime(String format) {
        return forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(format);
    }
}