package org.binqua.testing.csd.multiplereportswebapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.util.List;

public class PrettyFormatSummaryFormatter implements BuildsSummaryFormatter {

    private BuildsSummaryFormatter buildsSummaryFormatter;

    public PrettyFormatSummaryFormatter(BuildsSummaryFormatter buildsSummaryFormatter) {
        this.buildsSummaryFormatter = buildsSummaryFormatter;
    }

    @Override
    public String format(List<JenkinsBuildResponse> jenkinsBuildSummaries) {
        return prettyFormat(buildsSummaryFormatter.format(jenkinsBuildSummaries));
    }

    private String prettyFormat(String jsonAsString) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(new JsonParser().parse(jsonAsString));
    }
}
