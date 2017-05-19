package org.binqua.testing.csd.formatter.report.featuremenu;

import org.joda.time.DateTime;

interface DurationFormatter {

    String formatDuration(DateTime generationStartDateTime, DateTime generationEndDateTime);

}
