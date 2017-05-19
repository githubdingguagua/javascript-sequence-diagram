package org.binqua.testing.csd.multiplereportswebapp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class JenkinsBuildReportResource {

    List<JenkinsBuildResponse> getJenkinsBuildResponses(BuildsUrl buildsUrl) {

        return buildsUrl
            .testReportInfoList()
            .stream()
            .map(buildUrl -> {
                try {
                    return JenkinsBuildResponse.successful(toJson(buildUrl));
                } catch (IOException e) {
                    return JenkinsBuildResponse.failed(buildUrl);
                }
            }).collect(Collectors.toList());
    }

    private JenkinsBuildSummary toJson(TestReportInfo testReportInfo) throws IOException {
        final String asString = Request.Get(buildSummaryJsonUrl(testReportInfo)).execute().returnContent().toString();

        final JsonObject jsonObject = new Gson().fromJson(asString, JsonObject.class);

        return new JenkinsBuildSummary(testReportInfo.prettyName(),
                                       jsonObject.get("fullDisplayName").getAsString(),
                                       testReportInfo.partOfReportHomePageUrl(),
                                       jsonObject.get("number").getAsInt(),
                                       jsonObject.get("duration").getAsLong(),
                                       jsonObject.get("timestamp").getAsLong(),
                                       jsonObject.get("result").getAsString()
        );
    }

    private String buildSummaryJsonUrl(TestReportInfo testReportInfo) {
        return testReportInfo.url() + "/api/json?tree=fullDisplayName,number,result,timestamp,duration";
    }

}
