package org.binqua.testing.csd.cucumberreports.model;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Match {

    private final String location;
    private final List<Argument> arguments;

    public Match(
        @JsonProperty("location") String location,
        @JsonProperty("arguments") List<Argument> arguments) {
        this.location = location;
        this.arguments = (arguments == null) ? Lists.newArrayList() : arguments;
    }

    public String getLocation() {
        return location;
    }

    public List<Argument> getArguments() {
        return arguments;
    }
}
