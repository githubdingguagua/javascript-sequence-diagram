package org.binqua.testing.csd.httpclient;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.junit.Test;

import org.binqua.testing.csd.external.SystemAlias;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleHttpUriTest {

    @Test
    public void given1KeyValuePairWithNoDescriptionTheJsonIsCorrect() {

        final SimpleHttpUri httpUri = new SimpleHttpUri(anAlias("A"), "some/uri");

        assertThat(asString(httpUri.asJson()), is(afterReplacingSingleQuotes("{'uri':'some/uri','description':'some/uri','alias':'A'}")));
    }

    @Test
    public void given1KeyValuePairAndDescriptionTheJsonIsCorrect() {

        final SimpleHttpUri httpUri = new SimpleHttpUri(anAlias("A"), "some/uri", "some description");

        assertThat(asString(httpUri.asJson()), is(afterReplacingSingleQuotes("{'uri':'some/uri','description':'some description','alias':'A'}")));
    }

    private SystemAlias anAlias(final String name) {
        return new SystemAlias() {
            @Override
            public String name() {
                return name;
            }
        };
    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create().toJson(jsonElement);
    }

    private String afterReplacingSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }

}