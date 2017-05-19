package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class FeatureConversation implements ToJson {

    private List<ScenarioReport> listOfScenarioReports = new ArrayList<>();

    @Override
    public JsonElement asJson() {
        final JsonArray aJsonArray = new JsonArray();
        for (ScenarioReport aScenarioReport : listOfScenarioReports) {
            aJsonArray.add(aScenarioReport.asJson());
        }
        return aJsonArray;
    }

    public void add(ScenarioReport aScenarioReport) {
        listOfScenarioReports.add(aScenarioReport);
    }

}
