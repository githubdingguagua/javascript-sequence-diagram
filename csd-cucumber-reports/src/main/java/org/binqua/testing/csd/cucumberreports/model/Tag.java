package org.binqua.testing.csd.cucumberreports.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {

    private final String name;
    private final int line;

    public Tag(
        @JsonProperty("name") String name,
        @JsonProperty("line") Integer line) {
        this.name = name;
        this.line = (line == null) ? -1 : line;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }
}
