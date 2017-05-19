package org.binqua.testing.csd.formatter.report.screenshot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bniqua.testing.csd.bridge.external.StepContext;
import org.bniqua.testing.csd.bridge.external.StepContextObserver;
import org.bniqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.formatter.report.conversation.ToJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.valueOf;

public class ScenarioScreenshots implements ToJson, StepContextObserver {

    private final AtomicInteger screenshotByScenarioCounter = new AtomicInteger(1);
    private List<IdentifiableScreenshot> screenshotsOfASingleStep = new ArrayList<>();
    private Map<StepId, List<IdentifiableScreenshot>> screenshotsByStepId = new TreeMap<>();

    public IdentifiableScreenshot add(Screenshot screenshot) {
        final IdentifiableScreenshot identifiableScreenshot = new IdentifiableScreenshot(
                screenshot,
                valueOf(screenshotByScenarioCounter.getAndIncrement())
        );
        screenshotsOfASingleStep.add(identifiableScreenshot);
        return identifiableScreenshot;
    }

    @Override
    public JsonElement asJson() {
        return toJson(screenshotsByStepId);
    }

    private JsonElement toJson(Map<StepId, List<IdentifiableScreenshot>> screenshotsByStepId) {
        final JsonArray theJsonRepresentationOfAllSteps = new JsonArray();
        screenshotsByStepId
                .keySet()
                .stream()
                .forEach(stepId -> theJsonRepresentationOfAllSteps.add(screenshotsRepresentationOfASingleStep(screenshotsByStepId.get(stepId), stepId)));
        return theJsonRepresentationOfAllSteps;
    }

    private JsonObject screenshotsRepresentationOfASingleStep(List<IdentifiableScreenshot> screenshotsOfASingleStep, StepId stepId) {
        final JsonObject json = new JsonObject();
        json.addProperty("step", stepId.asString());
        json.add("data", screenshotAsJson(screenshotsOfASingleStep));
        return json;
    }

    private JsonArray screenshotAsJson(List<IdentifiableScreenshot> screenshotsOfASingleStep) {
        final JsonArray screenshotsAsJson = new JsonArray();
        screenshotsOfASingleStep
                .stream()
                .forEach(identifiableScreenshot -> {
                    final JsonObject screenshotJson = new JsonObject();
                    screenshotJson.addProperty("id", Integer.valueOf(identifiableScreenshot.id()));
                    screenshotJson.addProperty("title", identifiableScreenshot.screenshot().getCurrentTitle());
                    screenshotJson.addProperty("url", identifiableScreenshot.screenshot().getCurrentUrl());
                    screenshotJson.addProperty("browserEvent", identifiableScreenshot.screenshot().getEvent().reason());
                    screenshotsAsJson.add(screenshotJson);
                });
        return screenshotsAsJson;
    }

    @Override
    public void notifyStepContextStart(StepContext stepContext) {
        screenshotsOfASingleStep = new ArrayList<>();
    }

    @Override
    public void notifyStepContextEnd(StepContext context) {
        screenshotsByStepId.put(context.stepId(), screenshotsOfASingleStep);
    }
}
