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

public class SimpleHttpRequestTest {

    private static final SimpleIdentifier REQUEST_IDENTIFIER_ID = new SimpleIdentifier("requestIdentifierId");
    private static final SimpleIdentifier CORRELATION_ID = new SimpleIdentifier("correlationId");
    private static final SimpleSystemAlias FROM_SYSTEM = new SimpleSystemAlias("A");
    private static final String A_DESCRIPTION = "a description";

    private final Headers headers = mock(Headers.class);
    private final HttpUri httpUri = mock(HttpUri.class);
    private final Body body = mock(Body.class);

    @Test
    public void aSimpleHttpRequestCanBeConvertedToJson() throws Exception {

        when(httpUri.asJson()).thenReturn(new JsonObject());
        when(body.asJson()).thenReturn(someJsonElement("b1", "b2"));
        when(headers.asJson()).thenReturn(someJsonElement("k", "v"));

        SimpleHttpRequest aSimpleHttpRequest = new SimpleHttpRequest(
            A_DESCRIPTION,
            body,
            REQUEST_IDENTIFIER_ID,
            CORRELATION_ID,
            FROM_SYSTEM,
            HttpMessage.HttpMethod.GET,
            httpUri,
            headers
        );

        final String actualJson = aSimpleHttpRequest.asJson();

        assertThat(actualJson, is(replaceSingleQuotes("{"
                                                      + "'from':'A',"
                                                      + "'to':{},"
                                                      + "'type':'request',"
                                                      + "'headers':{"
                                                      + "'k':'v'"
                                                      + "},"
                                                      + "'body':{'b1':'b2'},"
                                                      + "'id':'requestIdentifierId',"
                                                      + "'correlationId':'correlationId',"
                                                      + "'method':'GET',"
                                                      + "'description':'a description'"
                                                      + "}")));
    }

    private JsonObject someJsonElement(String k, String v) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(k, v);
        return jsonObject;
    }

    private String replaceSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }

}