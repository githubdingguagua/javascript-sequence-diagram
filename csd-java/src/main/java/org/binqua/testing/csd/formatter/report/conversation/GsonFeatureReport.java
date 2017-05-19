package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.cucumberreports.model.Step;
import org.binqua.testing.csd.cucumberreports.model.Scenario;

import java.util.List;

class GsonFeatureReport implements ToJson {

    private Feature feature;

    GsonFeatureReport(Feature feature) {
        this.feature = feature;
    }

    @Override
    public JsonElement asJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(feature.getId()));
        jsonObject.add("title", new JsonPrimitive(feature.getName()));
        jsonObject.add("scenarios", toJson(feature.getScenarios()));
        return jsonObject;
    }

    private JsonElement toJson(List<Scenario> scenarios) {
        final JsonArray aJsonArray = new JsonArray();
        for (Scenario scenario : scenarios) {
            aJsonArray.add(asJson(scenario));
        }
        return aJsonArray;
    }

    private JsonElement asJson(Scenario scenario) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(scenario.getId()));
        jsonObject.add("title", new JsonPrimitive(scenario.getName()));
        jsonObject.add("steps", asJson(scenario.getSteps()));
        return jsonObject;
    }

    private JsonElement asJson(List<Step> steps) {
        final JsonArray aJsonArray = new JsonArray();
        for (Step step : steps) {
            aJsonArray.add(asJson(step));
        }
        return aJsonArray;
    }

    private JsonElement asJson(Step step) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(step.getId()));
        jsonObject.add("key", new JsonPrimitive(step.getKeyword()));
        jsonObject.add("text", new JsonPrimitive(step.getName()));
        return jsonObject;
    }

}
