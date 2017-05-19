package org.binqua.testing.csd.external.core;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_DASHES;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JsonBodyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenAValidJsonBodyWithoutExceptionThenAsJsonIsCorrectAndPrettyFormatted() throws Exception {
        final String rawBodyValue = "{\"a\":1}";
        final JsonBody jsonBody = new JsonBody(rawBodyValue);

        assertThat(asString(jsonBody.asJson()), is("{\"value\":\"{\\n  \\\"a\\\": 1\\n}\",\"content-type\":\"json\"}"));

        assertThat(jsonBody.contentType(),is(Body.ContentType.JSON));
        assertThat(jsonBody.rawValue(), is(rawBodyValue));
    }

    @Test
    public void givenAnEmptyBodyThenAsJsonPresentationHasAnEmptyValue() throws Exception {
        assertThat(asString(new JsonBody("").asJson()), is("{\"value\":\"\",\"content-type\":\"json\"}"));
    }

    @Test
    public void notValidJsonThrowsRuntimeException() throws Exception {
        thrown.expect(JsonSyntaxException.class);
        thrown.expectMessage("com.google.gson.stream.MalformedJsonException:");
        new JsonBody("a}").asJson();
    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(LOWER_CASE_WITH_DASHES).create().toJson(jsonElement);
    }

}