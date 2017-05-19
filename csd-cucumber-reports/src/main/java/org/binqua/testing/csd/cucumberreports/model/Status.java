package org.binqua.testing.csd.cucumberreports.model;

import com.google.common.collect.Sets;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.Set;

@JsonDeserialize(using = Status.Deserializer.class)
public enum Status {

    PASSED("passed"),
    FAILED("failed"),
    PENDING("pending"),
    SKIPPED("skipped"),
    MISSING("missing"),
    UNDEFINED("undefined");

    private static final Set<Status> buildFailingStatuses = Sets.newHashSet(FAILED, MISSING, UNDEFINED);

    private final String statusString;

    Status(String statusString) {
        this.statusString = statusString;
    }

    public String getStatusString() {
        return statusString;
    }

    public boolean failsBuild() {
        return buildFailingStatuses.contains(this);
    }

    public static final Status withStatusString(final String statusString) {
        for (Status accountFilingType : values()) {
            if (accountFilingType.getStatusString().equals(statusString)) {
                return accountFilingType;
            }
        }
        return null;
    }

    public boolean skipped() {
        return this == SKIPPED;
    }

    public boolean passed() {
        return this == PASSED;
    }

    public static class Deserializer extends JsonDeserializer<Status> {
        @Override
        public Status deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            final String statusString = jsonParser.getCodec().readValue(jsonParser, String.class);
            return withStatusString(statusString);
        }
    }
}
