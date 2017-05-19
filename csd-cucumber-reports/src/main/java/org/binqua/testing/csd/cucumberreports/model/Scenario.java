package org.binqua.testing.csd.cucumberreports.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scenario {

    private static final String BACKGROUND = "Background";

    private final String id;
    private final String description;
    private final String name;
    private final String keyword;
    private final int line;
    private final List<Step> steps;
    private final String type;
    private final List<Tag> tags;

    public Scenario(
            @JsonProperty("id") String id,
            @JsonProperty("description") String description,
            @JsonProperty("name") String name,
            @JsonProperty("keyword") String keyword,
            @JsonProperty("line") Integer line,
            @JsonProperty("steps") List<Step> steps,
            @JsonProperty("type") String type,
            @JsonProperty("tags") List<Tag> tags) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.keyword = keyword;
        this.line = line;
        this.steps = (steps == null) ? Lists.newArrayList() : steps;
        this.type = type;
        this.tags = (tags == null) ? Lists.newArrayList() : tags;
    }

    public String getId() {
        return id;
    }

    public int idAsInt() {
        return Integer.parseInt(id.replace("scenario-", ""));
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

    public List<Step> getSteps() {
        return steps;
    }

    public String getType() {
        return type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean isBackground() {
        return BACKGROUND.equals(keyword);
    }

    public Status getStatus() {
        boolean hasFailed = getSteps().stream().filter(failed()).findAny().isPresent();
        if (hasFailed) {
            return Status.FAILED;
        }

        return Status.PASSED;
    }

    public String commaSeparatedTags() {
        return tags.stream().map(Tag::getName).collect(Collectors.joining(", "));
    }

    public TestCounter testCounter() {
        return new TestCounter(numberOfTests(passed()), numberOfTests(failed()), numberOfTests(skipped()));
    }

    private int numberOfTests(Predicate<Step> predicate) {
        return (int) getSteps().stream().filter(predicate).count();
    }

    private Predicate<Step> passed() {
        return x -> x.getResult().getStatus().passed();
    }

    private Predicate<Step> failed() {
        return x -> x.getResult().getStatus().failsBuild();
    }

    private Predicate<Step> skipped() {
        return x -> x.getResult().getStatus().skipped();
    }

    public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }
}
