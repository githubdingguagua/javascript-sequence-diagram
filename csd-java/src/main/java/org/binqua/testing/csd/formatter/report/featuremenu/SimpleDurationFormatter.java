package org.binqua.testing.csd.formatter.report.featuremenu;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

class SimpleDurationFormatter implements DurationFormatter {

    @Override
    public String formatDuration(DateTime generationStartDateTime, DateTime generationEndDateTime) {
        return durationFormatted(generationEndDateTime.getMillis() - generationStartDateTime.getMillis());
    }

    private String durationFormatted(long millis) {
        return String.format("%02d:%02d:%02d",
                             TimeUnit.MILLISECONDS.toHours(millis),
                             TimeUnit.MILLISECONDS.toMinutes(millis) -
                             TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                             TimeUnit.MILLISECONDS.toSeconds(millis) -
                             TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

}
