package org.binqua.testing.csd.httpclient;

import com.google.gson.JsonObject;

import org.junit.Test;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.Headers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleHttpResponseTest {

    private static final SimpleIdentifier RESPONSE_IDENTIFIER = new SimpleIdentifier("requestIdentifierId");
    private static final SimpleIdentifier CORRELATION_ID = new SimpleIdentifier("correlationId");
    private static final SimpleSystemAlias FROM_SYSTEM = new SimpleSystemAlias("A");
    private static final SimpleSystemAlias TO_SYSTEM = new SimpleSystemAlias("B");
    private static final String A_DESCRIPTION = "a description";
    private static final int STATUS = 200;

    private final Headers headers = mock(Headers.class);
    private final Body body = mock(Body.class);

    @Test
    public void aSimpleHttpResponseCanBeConvertedToJson() throws Exception {

        when(body.asJson()).thenReturn(someJsonElement("b1", "b2"));
        when(headers.asJson()).thenReturn(someJsonElement("k1", "v1"));

        final SimpleHttpResponse aSimpleHttpResponse = new SimpleHttpResponse(
            A_DESCRIPTION,
            body,
            RESPONSE_IDENTIFIER,
            STATUS,
            headers,
            FROM_SYSTEM,
            TO_SYSTEM,
            CORRELATION_ID
        );

        final String actualJson = aSimpleHttpResponse.asJson();

        assertThat(actualJson, is(replaceSingleQuotes("{"
                                                      + "'from':'A',"
                                                      + "'to':'B',"
                                                      + "'type':'response',"
                                                      + "'headers':{"
                                                      + "'k1':'v1'"
                                                      + "},"
                                                      + "'body':{'b1':'b2'},"
                                                      + "'id':'requestIdentifierId',"
                                                      + "'correlationId':'correlationId',"
                                                      + "'status':200,"
                                                      + "'description':'a description'"
                                                      + "}")));
    }

    private String replaceSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }

    private JsonObject someJsonElement(String k, String v) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(k, v);
        return jsonObject;
    }
}