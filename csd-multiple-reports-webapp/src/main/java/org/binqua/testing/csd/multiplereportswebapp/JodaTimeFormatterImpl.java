package org.binqua.testing.csd.multiplereportswebapp;

import org.joda.time.DateTime;

import static org.joda.time.format.DateTimeFormat.forPattern;

class JodaTimeFormatterImpl implements DateTimeFormatter {

    @Override
    public String format(DateTime dateTime) {
        return forPattern("EEE dd-MMM-yyyy @ HH:mm:ss").print(dateTime);
    }

}
