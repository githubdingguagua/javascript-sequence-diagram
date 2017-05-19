package org.binqua.testing.csd.multiplereportswebapp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import static org.joda.time.format.DateTimeFormat.forPattern;

public class JenkinsBuildSummary {

    private static final String DATE_TIME_FORMAT = "EEE dd-MMM-yyyy @ HH:mm:ss";
    private final String prettyName;
    private final String fullDisplayName;
    private final String partOfReportHomePageUrl;
    private final int number;
    private final long duration;
    private final long timestamp;
    private final String result;

    public JenkinsBuildSummary(String prettyName, String fullDisplayName, String partOfReportHomePageUrl, int number, long duration, long timestamp, String result) {
        this.prettyName = prettyName;
        this.fullDisplayName = fullDisplayName;
        this.partOfReportHomePageUrl = partOfReportHomePageUrl;
        this.number = number;
        this.duration = duration;
        this.timestamp = timestamp;
        this.result = result;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String jobFullDisplayName() {
        return fullDisplayName;
    }

    public String name() {
        return fullDisplayName.substring(0, fullDisplayName.indexOf(" "));
    }

    public String id() {
        return name() + "-" + number;
    }

    public String longText() {
        return String.format("#%s %s took %s", number, format(toDateTime()), durationFormatted(duration));
    }

    private String durationFormatted(long millis) {
        return String.format("%02d:%02d:%02d",
                             TimeUnit.MILLISECONDS.toHours(millis),
                             TimeUnit.MILLISECONDS.toMinutes(millis) -
                             TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                             TimeUnit.MILLISECONDS.toSeconds(millis) -
                             TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private String format(DateTime readableInstant) {
        return forPattern(DATE_TIME_FORMAT).print(readableInstant);
    }

    private DateTime toDateTime() {
        return new DateTime(new Timestamp(timestamp), DateTimeZone.getDefault());
    }

    public int number() {
        return number;
    }

    public boolean testOutcome() {
        return result.equals("SUCCESS");
    }

    public String partOfReportHomePageUrl() {
        return partOfReportHomePageUrl;
    }

    public String jobPrettyName() {
        return prettyName;
    }
}
