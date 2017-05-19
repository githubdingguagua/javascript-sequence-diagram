package org.binqua.testing.csd.multiplereportswebapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class JsonBuildsSummaryFormatter implements BuildsSummaryFormatter {

    private String reportHomePageUrlPattern;

    JsonBuildsSummaryFormatter(ReportBuilderConfiguration reportHomePageUrlPattern) {
        this.reportHomePageUrlPattern = reportHomePageUrlPattern.reportHomePageUrlPattern();
    }

    @Override
    public String format(List<JenkinsBuildResponse> jenkinsBuildSummaries) {

        Map<String, List<JenkinsBuildResponse>> mapGroupedByJobUrls = new TreeMap<>(jenkinsBuildSummaries.stream().collect(Collectors.groupingBy(JenkinsBuildResponse::name)));

        final List<JsonObject> jsonObjects = mapGroupedByJobUrls.keySet()
            .stream()
            .map(toJsonObject(mapGroupedByJobUrls))

            .collect(Collectors.toList());

        return toMenuJson(jsonObjects);
    }

    private Function<String, JsonObject> toJsonObject(final Map<String, List<JenkinsBuildResponse>> mapGroupedByJobUrls) {
        return new Function<String, JsonObject>() {
            @Override
            public JsonObject apply(String key) {
                return toSingleJobJson(mapGroupedByJobUrls.get(key));
            }

            private JsonObject toSingleJobJson(List<JenkinsBuildResponse> jenkinsBuildSummaries) {
                final JsonObject jsonElement = new JsonObject();
                addId(jsonElement, jenkinsBuildSummaries.get(0).name());
                addText(jsonElement, jenkinsBuildSummaries.get(0).jobPrettyName());
                addImg(jsonElement);
                addNodes(jsonElement, jenkinsBuildSummaries);
                return jsonElement;
            }

            private void addNodes(JsonObject jsonElement, List<JenkinsBuildResponse> summary) {
                jsonElement.add("nodes", toNodes(summary));
            }

            private void addImg(JsonObject jsonElement) {
                jsonElement.add("img", new JsonPrimitive("icon-folder"));
            }

            private JsonElement toNodes(List<JenkinsBuildResponse> jenkinsBuildResponses) {
                final JsonArray jsonArray = new JsonArray();
                sortDescendingByBuildNumber(jenkinsBuildResponses).forEach(response -> jsonArray.add(toNode(response)));
                return jsonArray;
            }

            private JsonElement toNode(JenkinsBuildResponse response) {
                final JsonObject jsonElement = new JsonObject();
                addId(jsonElement, response.id());
                addText(jsonElement, wrapWithSpan(response.longText(), response.isSuccessful(), response.isTestSuccessful()));
                addUrl(jsonElement, response);
                return jsonElement;
            }

            private String wrapWithSpan(String textToBeWrapped, boolean responseSuccessful, boolean testSuccessful) {
                StringBuilder text = new StringBuilder("<span class=\"");
                if (responseSuccessful) {
                    text.append(testSuccessful ? "testPassed" : "testFailed").append("\">");
                } else {
                    text.append("serverProblems").append("\">");
                }
                return text.append(textToBeWrapped).append("</span>").toString();
            }

            private void addText(JsonObject jsonElement, String value) {
                jsonElement.add("text", new JsonPrimitive(value));
            }

            private void addId(JsonObject jsonElement, String value) {
                jsonElement.add("id", new JsonPrimitive(value));
            }

            private void addUrl(JsonObject jsonElement, JenkinsBuildResponse jenkinsResponse) {
                String urlValue = jenkinsResponse.isSuccessful() ? buildReportUrl(jenkinsResponse) : jenkinsResponse.buildUrl();
                jsonElement.add("url", new JsonPrimitive(urlValue));
            }

            private String buildReportUrl(JenkinsBuildResponse jenkinsResponse) {
                return reportHomePageUrlPattern.replaceAll("\\$", jenkinsResponse.partOfReportHomePageUrl());
            }
        };
    }

    private List<JenkinsBuildResponse> sortDescendingByBuildNumber(List<JenkinsBuildResponse> jenkinsBuildResponses) {
        return jenkinsBuildResponses.stream().sorted((o1, o2) -> o1.number() > o2.number() ? -1 : 1).collect(toList());
    }

    private String toMenuJson(List<JsonObject> jsonObjects) {
        final JsonArray jsonArray = new JsonArray();
        jsonObjects.stream().forEach(jsonArray::add);
        final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(jsonArray);
    }
}
