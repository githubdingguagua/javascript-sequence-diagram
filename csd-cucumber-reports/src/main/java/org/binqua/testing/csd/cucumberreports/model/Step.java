package org.binqua.testing.csd.cucumberreports.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {

    private final String id;
    private final String name;
    private final String keyword;
    private final int line;
    private final List<String> output;

    private Result result;
    private Match match;
    private transient CsdDataTable dataTable;

    public Step(
            @JsonProperty("id") String id,
            @JsonProperty("match") Match match,
            @JsonProperty("line") Integer line,
            @JsonProperty("keyword") String keyword,
            @JsonProperty("name") String name,
            @JsonProperty("result") Result result,
            @JsonProperty("output") List<String> output,
            CsdDataTable dataTable) {
        this.id = id;
        this.match = match;
        this.line = (line == null) ? -1 : line;
        this.keyword = keyword;
        this.name = name;
        this.result = result;
        this.output = (output == null) ? Lists.newArrayList() : output;
        this.dataTable = dataTable == null ? CsdDataTable.empty() : dataTable;
    }

    public Result getResult() {
        return result;
    }

    public String getId() {
        return id;
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

    public List<String> getOutput() {
        return output;
    }

    public Match getMatch() {
        return match;
    }

    public String htmlFormattedOutput() {
        return output.stream().collect(Collectors.joining("\n"));
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<CsdTableRow> getDataTableRows() {
        return dataTable.getRows();
    }

    public boolean hasANonEmptyDataTable() {
        return !dataTable.getRows().isEmpty();
    }
}
