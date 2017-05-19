package org.binqua.testing.csd.cucumberreports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

    private final long duration;
    private final Status status;
    private final String errorMessage;

    public Result(
        @JsonProperty("duration") Long duration,
        @JsonProperty("status") Status status,
        @JsonProperty("error_message") String errorMessage) {
        this.duration = (duration == null) ? 0 : duration;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public long getDuration() {
        return duration;
    }

    public Status getStatus() {
        return status;
    }

    public String errorMessage() {
        if (status == Status.UNDEFINED) {
            return "This step is not yet implemented";
        }
        if (errorMessage != null) {
            return errorMessage.replaceAll("\\tat ", "<br/>at ").replaceAll("Caused by: ","<br/>Caused by: ");
        }
        return "";
    }

}
