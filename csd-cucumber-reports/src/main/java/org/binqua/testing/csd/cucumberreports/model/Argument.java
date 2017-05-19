package org.binqua.testing.csd.cucumberreports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Argument {

    private final String val;
    private final int offset;

    public Argument(
        @JsonProperty("val") String val,
        @JsonProperty("offset") Integer offset) {
        this.val = val;
        this.offset = (offset == null) ? -1 : offset;
    }

    public String getVal() {
        return val;
    }

    public int getOffset() {
        return offset;
    }
}
