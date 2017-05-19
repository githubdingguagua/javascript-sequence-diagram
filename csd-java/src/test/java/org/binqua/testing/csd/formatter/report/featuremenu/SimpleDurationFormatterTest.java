package org.binqua.testing.csd.formatter.report.featuremenu;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleDurationFormatterTest {

    private final SimpleDurationFormatter simpleDurationFormatter = new SimpleDurationFormatter();

    @Test
    public void givenDurationContainsOnlyMinutesAndSecondsThenIsFormattedCorrectly() throws Exception {

        final DateTime startTime = LocalDateTime.parse("2015-09-23T17:01:02.000").toDateTime();
        final DateTime endTime = startTime.plusMinutes(1).plusSeconds(2);

        assertThat(simpleDurationFormatter.formatDuration(startTime, endTime), is("00:01:02"));

    }

    @Test
    public void givenDurationContainsHoursMinutesAndSecondsThenIsFormattedCorrectly() throws Exception {

        final DateTime startTime = LocalDateTime.parse("2015-09-23T17:01:02.000").toDateTime();
        final DateTime endTime = startTime.plusHours(1).plusMinutes(10).plusSeconds(20);

        assertThat(simpleDurationFormatter.formatDuration(startTime, endTime), is("01:10:20"));

    }

}