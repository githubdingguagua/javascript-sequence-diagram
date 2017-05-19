package org.binqua.testing.csd;

import com.google.gson.GsonBuilder;

import org.hamcrest.Matcher;

import org.binqua.testing.csd.formatter.report.conversation.ToJson;

import static org.junit.Assert.assertThat;

public class JsonTestUtil {

    public static void asJsonAfterReplaceDoubleQuotes(ToJson toJson, Matcher<String> matcher) {
        final String expectedJson = new GsonBuilder().disableHtmlEscaping().create().toJson(toJson.asJson());
        assertThat(expectedJson.replaceAll("\"", "'"), matcher);
    }

}
