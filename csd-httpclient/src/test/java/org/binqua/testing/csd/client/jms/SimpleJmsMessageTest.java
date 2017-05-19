package org.binqua.testing.csd.client.jms;

import com.google.gson.JsonObject;
import org.binqua.testing.csd.httpclient.SimpleHeaders;
import org.junit.Test;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.httpclient.SimpleIdentifier;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleJmsMessageTest {

    private static final SimpleIdentifier CORRELATION_IDENTIFIER = new SimpleIdentifier("2");
    private static final Headers HEADERS = new SimpleHeaders().withKeyValue("k", "v");
    private static final SimpleIdentifier IDENTIFIER = new SimpleIdentifier("1");
    private static final SimpleSystemAlias FROM = new SimpleSystemAlias("A");
    private static final SimpleSystemAlias TO = new SimpleSystemAlias("B");

    private final Body body = mock(Body.class);

    @Test
    public void aSimpleJmsMessageWithoutDeliveryExceptionCanBeRepresentedAsJson() throws Exception {

        when(body.asJson()).thenReturn(new JsonObject());

        SimpleJmsMessage simpleJmsMessage = new SimpleJmsMessage(
                FROM,
                TO,
                HEADERS,
                body,
                IDENTIFIER,
                CORRELATION_IDENTIFIER,
                Optional.empty()
        );

        final String expectedJson = replaceSingleQuotes(
                "{"
                        + "'from':'A',"
                        + "'to':'B',"
                        + "'type':'JMS',"
                        + "'headers':{'http-headers':{'k':'v'}},"
                        + "'body':{},"
                        + "'id':'1',"
                        + "'correlationId':'2',"
                        + "'description':'JMS message from A to B'"
                        + "}"
        );

        assertThat(simpleJmsMessage.from(), is(FROM));
        assertThat(simpleJmsMessage.to(), is(TO));
        assertThat(simpleJmsMessage.asJson(), is(expectedJson));
        assertThat(simpleJmsMessage.description(), is("JMS message from A to B"));
        assertThat(simpleJmsMessage.identifier(), is(IDENTIFIER));
        assertThat(simpleJmsMessage.correlationIdentifier(), is(CORRELATION_IDENTIFIER));
        assertThat(simpleJmsMessage.messageType().name(), is("JMS"));
        assertThat(simpleJmsMessage.headers(), is(HEADERS));
        assertThat(simpleJmsMessage.deliveryExceptionText(), is(Optional.empty()));
    }

    @Test
    public void aSimpleJmsMessageWithDeliveryExceptionCanBeRepresentedAsJson() throws Exception {

        when(body.asJson()).thenReturn(new JsonObject());

        SimpleJmsMessage simpleJmsMessage = new SimpleJmsMessage(
                FROM,
                TO,
                HEADERS,
                body,
                IDENTIFIER,
                CORRELATION_IDENTIFIER,
                Optional.of("some exception")
        );

        final String containedJson = replaceSingleQuotes(""
                + "'deliveryException':'some exception'"
        );

        assertThat(simpleJmsMessage.asJson(), containsString(containedJson));

        assertThat(simpleJmsMessage.deliveryExceptionText(), is(Optional.of("some exception")));
    }


    private String replaceSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }
}