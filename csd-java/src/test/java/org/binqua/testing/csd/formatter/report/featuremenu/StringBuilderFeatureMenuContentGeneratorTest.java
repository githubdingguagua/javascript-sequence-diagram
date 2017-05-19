package org.binqua.testing.csd.formatter.report.featuremenu;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.junit.Test;

import org.binqua.testing.csd.formatter.report.conversation.ToJson;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StringBuilderFeatureMenuContentGeneratorTest {

    private final ToJson toJson = mock(ToJson.class);

    @Test
    public void contentReturnAJavaScriptVariableInitialisationWithFeatureTestResult() throws Exception {

        final JsonObject jsonContent = new JsonObject();
        jsonContent.add("k", new JsonPrimitive("<a>sometext</a>"));

        when(toJson.asJson()).thenReturn(jsonContent);

        FeatureMenuContentGenerator featureMenuContentGenerator = new StringBuilderFeatureMenuContentGenerator();

        assertThat(featureMenuContentGenerator.content(toJson), is("var testsMenu = {\n  \"k\": \"<a>sometext</a>\"\n}"));

    }
}