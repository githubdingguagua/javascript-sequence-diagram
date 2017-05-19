package org.binqua.testing.csd.httpclient;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.junit.Test;

import org.binqua.testing.csd.external.core.Headers;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_DASHES;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleHeadersTest {

    @Test
    public void given1KeyValuePairTheJsonIsCorrect() {

        final Headers headers = new SimpleHeaders().withKeyValue("k1", "v1");

        assertThat(asString(headers.asJson()), is(replaceSingleQuotes("{'http-headers':{'k1':'v1'}}")));
    }

    @Test
    public void givenNoKeyValuePairsTheJsonIsCorrect() {
        assertThat(asString(new SimpleHeaders().asJson()), is(replaceSingleQuotes("{'http-headers':{}}")));
    }

    @Test
    public void valueOfReturnsRightValue() {
        assertThat(new SimpleHeaders().withKeyValue("k1", "v1").valueOf("k1"), is("v1"));
    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().setFieldNamingPolicy(LOWER_CASE_WITH_DASHES).create().toJson(jsonElement);
    }

    private String replaceSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }
}