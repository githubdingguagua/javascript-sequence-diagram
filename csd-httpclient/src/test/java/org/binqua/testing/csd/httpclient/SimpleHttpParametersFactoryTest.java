package org.binqua.testing.csd.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.*;
import org.junit.Test;

import java.util.HashMap;

import static org.binqua.testing.csd.httpclient.HttpClientParameters.HttpBody.aBodyWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleHttpParametersFactoryTest {

    private static final SimpleSystemAlias FROM_SYSTEM = new SimpleSystemAlias("A");
    private static final SystemAlias TO_SYSTEM = new SimpleSystemAlias("B");

    private final DescriptionResolverFactory descriptionResolverFactory = mock(DescriptionResolverFactory.class);
    private final IdentifierGenerator identifierGenerator = mock(IdentifierGenerator.class);
    private final ExecutionContext anExecutionContext = mock(ExecutionContext.class);
    private final DescriptionResolver aDescriptionResolver = mock(DescriptionResolver.class);
    private final BodyFactory bodyFactory = mock(BodyFactory.class);
    private final Body aMessageBody = mock(Body.class,"message body");
    private final Headers headers = someHeaders("b", 1);

    private final HttpParametersFactory factory = new SimpleHttpParametersFactory(identifierGenerator,
                                                                                  descriptionResolverFactory,
                                                                                  bodyFactory);
    private final HttpUri aHttpUri = mock(HttpUri.class);

    @Test
    public void newHttpRequest() {
        final String aRequestDescription = "the request description";
        final String requestBodyContent = "requestBodyContent";

        when(descriptionResolverFactory.request()).thenReturn(aDescriptionResolver);
        when(aDescriptionResolver.resolve(anExecutionContext, HttpMessage.HttpMethod.GET, FROM_SYSTEM, aHttpUri)).thenReturn(aRequestDescription);
        when(bodyFactory.createAMessageBody(requestBodyContent, headers)).thenReturn(aMessageBody);

        when(identifierGenerator.newIdentifier()).thenReturn(anIdentifier("1"));
        when(aHttpUri.asJson()).thenReturn(someJsonWithKeyValue("a", 1));
        when(aHttpUri.alias()).thenReturn(TO_SYSTEM);
        when(aHttpUri.value()).thenReturn("http://localhost:8365/something");

        when(aMessageBody.asJson()).thenReturn(new JsonObject());

        final HttpRequest actualHttpRequest = factory.newHttpRequest(anExecutionContext,
                                                                     FROM_SYSTEM,
                                                                     HttpMessage.HttpMethod.GET,
                                                                     aBodyWith(requestBodyContent),
                aHttpUri,
                                                                     headers);

        final String expectedJson = replaceSingleQuotes(
            "{"
            + "'from':'A',"
            + "'to':{'a':1},"
            + "'type':'request',"
            + "'headers':{'b':1},"
            + "'body':{},"
            + "'id':'request-1',"
            + "'correlationId':'1',"
            + "'method':'GET',"
            + "'description':'the request description'"
            +"}");

        assertThat(actualHttpRequest.asJson(), is(expectedJson));
    }

    @Test
    public void newHttpResponse() {
        final String aResponseDescription = "the response description";
        final String responseBodyContent = "responseBody";

        final HttpRequest httpRequest = mock(HttpRequest.class);

        when(httpRequest.method()).thenReturn(HttpMessage.HttpMethod.POST);
        when(identifierGenerator.newIdentifier()).thenReturn(anIdentifier("1"));

        when(descriptionResolverFactory.response()).thenReturn(aDescriptionResolver);
        when(aDescriptionResolver.resolve(anExecutionContext, FROM_SYSTEM, aHttpUri)).thenReturn(aResponseDescription);

        when(httpRequest.correlationIdentifier()).thenReturn(anIdentifier("2"));
        when(httpRequest.from()).thenReturn(FROM_SYSTEM);
        when(httpRequest.to()).thenReturn(TO_SYSTEM);

        when(httpRequest.callerSystem()).thenReturn(FROM_SYSTEM);
        when(httpRequest.uri()).thenReturn(aHttpUri);
        when(aHttpUri.alias()).thenReturn(TO_SYSTEM);
        when(aHttpUri.value()).thenReturn("http://localhost:8365/something");

        when(bodyFactory.createAMessageBody(responseBodyContent, headers)).thenReturn(aMessageBody);
        when(aMessageBody.asJson()).thenReturn(new JsonObject());

        HttpResponse httpResponse = factory.newHttpResponse(anExecutionContext, httpRequest, 200, aBodyWith(responseBodyContent), headers);

        final String expectedJson = replaceSingleQuotes("{"
                                                        + "'from':'B',"
                                                        + "'to':'A',"
                                                        + "'type':'response',"
                                                        + "'headers':{'b':1},"
                                                        + "'body':{},"
                                                        + "'id':'response-1',"
                                                        + "'correlationId':'2',"
                                                        + "'status':200,"
                                                        + "'description':'the response description'"
                                                        + "}");

        assertThat(httpResponse.asJson(), is(expectedJson));

    }

    @Test
    public void newDirectHttpMethodCallRequest() {
        final String aRequestDescription = "the request description";
        final TheBodyOfTheRequest theBodyOfTheRequest = new TheBodyOfTheRequest();

        when(descriptionResolverFactory.request()).thenReturn(aDescriptionResolver);
        when(aDescriptionResolver.resolve(anExecutionContext, HttpMessage.HttpMethod.GET, FROM_SYSTEM, aHttpUri)).thenReturn(aRequestDescription);
        when(bodyFactory.createAJsonMessageBody(theBodyOfTheRequest)).thenReturn(aMessageBody);

        when(identifierGenerator.newIdentifier()).thenReturn(anIdentifier("1"));
        when(aHttpUri.asJson()).thenReturn(someJsonWithKeyValue("a", 1));
        when(aHttpUri.alias()).thenReturn(TO_SYSTEM);
        when(aHttpUri.value()).thenReturn("http://localhost:8365/something");

        when(aMessageBody.asJson()).thenReturn(new JsonObject());


        final HttpRequest actualHttpRequest = factory.newDirectHttpMethodCallRequest(anExecutionContext,
                FROM_SYSTEM,
                HttpMessage.HttpMethod.GET,
                theBodyOfTheRequest,
                aHttpUri);

        final String expectedJson = replaceSingleQuotes(
                "{"
                        + "'from':'A',"
                        + "'to':{'a':1},"
                        + "'type':'request',"
                        + "'headers':{'http-headers':{}},"
                        + "'body':{},"
                        + "'id':'request-1',"
                        + "'correlationId':'1',"
                        + "'method':'GET',"
                        + "'description':'the request description'"
                        +"}");

        assertThat(actualHttpRequest.asJson(), is(expectedJson));
    }

    @Test
    public void newDirectHttpMethodCallResponse() {
        final String aResponseDescription = "the response description";
        final Object bodyResponse = new Object();

        final HttpRequest httpRequest = mock(HttpRequest.class);

        when(httpRequest.method()).thenReturn(HttpMessage.HttpMethod.POST);
        when(identifierGenerator.newIdentifier()).thenReturn(anIdentifier("1"));

        when(descriptionResolverFactory.response()).thenReturn(aDescriptionResolver);
        when(aDescriptionResolver.resolve(anExecutionContext, FROM_SYSTEM, aHttpUri)).thenReturn(aResponseDescription);

        when(httpRequest.correlationIdentifier()).thenReturn(anIdentifier("2"));
        when(httpRequest.from()).thenReturn(FROM_SYSTEM);
        when(httpRequest.to()).thenReturn(TO_SYSTEM);

        when(httpRequest.callerSystem()).thenReturn(FROM_SYSTEM);
        when(httpRequest.uri()).thenReturn(aHttpUri);
        when(aHttpUri.alias()).thenReturn(TO_SYSTEM);
        when(aHttpUri.value()).thenReturn("http://localhost:8365/something");

        when(bodyFactory.createAJsonMessageBody(bodyResponse)).thenReturn(aMessageBody);
        when(aMessageBody.asJson()).thenReturn(new JsonObject());

        HttpMessage httpResponse = factory.newDirectHttpMethodCallResponse(anExecutionContext, httpRequest, bodyResponse);

        final String expectedJson = replaceSingleQuotes("{"
                + "'from':'B',"
                + "'to':'A',"
                + "'type':'response',"
                + "'headers':{'http-headers':{}},"
                + "'body':{},"
                + "'id':'response-1',"
                + "'correlationId':'2',"
                + "'description':'the response description'"
                + "}");

        assertThat(httpResponse.asJson(), is(expectedJson));

    }

    private JsonElement someJsonWithKeyValue(String key, int value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        return jsonObject;
    }

    private Identifier anIdentifier(final String theId) {
        return () -> theId;
    }

    private String replaceSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }

    private Headers someHeaders(final String b, final int i) {
        return new Headers() {
            @Override
            public JsonElement asJson() {
                return someJsonWithKeyValue(b, i);
            }

            @Override
            public Headers withKeyValue(String key, String value) {
                return null;
            }

            @Override
            public HashMap<String, String> httpHeaders() {
                return null;
            }

            @Override
            public String valueOf(String key) {
                return null;
            }
        };
    }

    private class TheBodyOfTheRequest {
    }
}