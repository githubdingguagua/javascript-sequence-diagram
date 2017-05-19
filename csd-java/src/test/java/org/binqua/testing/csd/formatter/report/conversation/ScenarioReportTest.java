package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.binqua.testing.csd.JsonTestUtil;
import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScenarioReportTest {

    private final DecoratedConversation decoratedConversation = mock(DecoratedConversation.class);
    private final ScenarioScreenshots scenarioScreenshots = mock(ScenarioScreenshots.class);

    @Test
    public void scenarioJsonIsCorrect() throws Exception {
        final String name = "scenario Name";
        final String id = "scenario Id";

        final JsonObject conversationAsJson = new JsonObject();
        conversationAsJson.add("key", new JsonPrimitive("value"));

        when(decoratedConversation.asJson()).thenReturn(conversationAsJson);
        when(scenarioScreenshots.asJson()).thenReturn(someJson());

        final String expectedJson = "{" +
                "'scenario':{'name':'scenario Name','id':'scenario Id'}," +
                "'screenshots':['someJson']," +
                "'conversation':{'key':'value'}" +
                "}";

        JsonTestUtil.asJsonAfterReplaceDoubleQuotes(new ScenarioReport(new Scenario(name, id), scenarioScreenshots, decoratedConversation), is(expectedJson));
    }

    private JsonElement someJson() {
        final JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonPrimitive("someJson"));
        return jsonArray;
    }

}