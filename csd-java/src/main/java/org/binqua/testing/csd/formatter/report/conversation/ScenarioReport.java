package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;

public class ScenarioReport implements ToJson {

    private final Scenario scenario;
    private final ScenarioScreenshots scenarioScreenshots;
    private final DecoratedConversation decoratedConversation;

    public ScenarioReport(Scenario scenario, ScenarioScreenshots scenarioScreenshots, DecoratedConversation decoratedConversation) {
        this.scenario = scenario;
        this.scenarioScreenshots = scenarioScreenshots;
        this.decoratedConversation = decoratedConversation;
    }

    @Override
    public JsonElement asJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("scenario", scenario.asJson());
        jsonObject.add("screenshots", scenarioScreenshots.asJson());
        jsonObject.add("conversation", decoratedConversation.asJson());
        return jsonObject;
    }

}
