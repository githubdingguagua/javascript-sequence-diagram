package org.binqua.testing.csd.cucumberreports.model;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Feature {

    private final String id;
    private final List<Tag> tags;
    private final String description;
    private final String name;
    private final String keyword;
    private final int line;
    private final List<Scenario> scenarios;
    private final String uri;

    public Feature(
            @JsonProperty("id") String id,
            @JsonProperty("tags") List<Tag> tags,
            @JsonProperty("description") String description,
            @JsonProperty("name") String name,
            @JsonProperty("keyword") String keyword,
            @JsonProperty("line") Integer line,
            @JsonProperty("elements") List<Scenario> scenarios,
            @JsonProperty("uri") String uri) {
        this.id = id;
        this.tags = tags;
        this.description = description;
        this.name = name;
        this.keyword = keyword;
        this.line = (line == null) ? -1 : line;
        this.scenarios = (scenarios == null) ? Lists.newArrayList() : scenarios;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getLine() {
        return line;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public String getUri() {
        return uri;
    }

    public Status getStatus() {
        if (scenarios.stream().filter(scenario -> scenario.getStatus() == Status.FAILED).findAny().isPresent()) {
            return Status.FAILED;
        }
        return Status.PASSED;
    }

    public String commaSeparatedTags() {
        return tags.stream().map(Tag::getName).collect(Collectors.joining(", "));
    }

    public TestCounter testCounter() {
        return new TestCounter(
                sumOfTests(TestCounter::numberOfPassedTests),
                sumOfTests(TestCounter::numberOfFailedTests),
                sumOfTests(TestCounter::numberOfSkippedTests)
        );
    }

    private int sumOfTests(ToIntFunction<TestCounter> toIntFunction) {
        return scenarios.stream()
                .map(Scenario::testCounter)
                .mapToInt(toIntFunction)
                .sum();
    }

    public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }
}
