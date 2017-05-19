package org.binqua.testing.csd.external.core;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.hamcrest.core.Is;
import org.junit.Test;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_DASHES;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextBodyTest {

    @Test
    public void givenAValidBodyThenAsJsonIsCorrect() throws Exception {
        final String bodyContent = "some content";
        final TextBody textBody = new TextBody(bodyContent);

        assertThat(asString(textBody.asJson()), is("{\"value\":\"some content\",\"content-type\":\"text\"}"));

        assertThat(textBody.contentType(), Is.is(Body.ContentType.TEXT));
        assertThat(textBody.rawValue(), Is.is(bodyContent));
    }

    @Test
    public void givenAnEmptyBodyThenAsJsonPresentationHasAnEmptyValue() throws Exception {
        assertThat(asString(new TextBody("").asJson()), is("{\"value\":\"\",\"content-type\":\"text\"}"));
    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(LOWER_CASE_WITH_DASHES).create().toJson(jsonElement);
    }

}