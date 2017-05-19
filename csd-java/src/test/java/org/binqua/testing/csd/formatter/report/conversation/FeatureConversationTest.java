package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.binqua.testing.csd.JsonTestUtil;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeatureConversationTest extends JsonTestUtil {

    @Test
    public void asJsonIsCorrect() throws Exception {

        final ScenarioReport firstScenarioReport = mock(ScenarioReport.class);
        final ScenarioReport secondScenarioReport = mock(ScenarioReport.class);

        final JsonObject firstScenarioReportAsJson = new JsonObject();
        firstScenarioReportAsJson.add("k1", new JsonPrimitive("v1"));

        final JsonObject secondScenarioReportAsJson = new JsonObject();
        secondScenarioReportAsJson.add("k2", new JsonPrimitive("v2"));

        when(firstScenarioReport.asJson()).thenReturn(firstScenarioReportAsJson);
        when(secondScenarioReport.asJson()).thenReturn(secondScenarioReportAsJson);

        final FeatureConversation featureConversation = new FeatureConversation();

        featureConversation.add(firstScenarioReport);
        featureConversation.add(secondScenarioReport);

        asJsonAfterReplaceDoubleQuotes(featureConversation, is("[{'k1':'v1'},{'k2':'v2'}]"));

    }
}