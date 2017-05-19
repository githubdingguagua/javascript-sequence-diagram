package org.binqua.testing.csd.httpclient;


import com.hazelcast.config.GlobalSerializerConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.DefaultSerializationServiceBuilder;
import com.hazelcast.nio.serialization.SerializationService;

import org.junit.Test;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.BodyFactory;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.MessageTypeEnumImpl;
import org.binqua.testing.csd.external.core.TextBody;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleHttpRequestStreamSerializerTest {

    private static final SimpleIdentifier REQUEST_IDENTIFIER_ID = new SimpleIdentifier("requestIdentifierId");
    private static final SimpleSystemAlias TO_SYSTEM = new SimpleSystemAlias("B");
    private static final SimpleHttpUri SERVER_URI = new SimpleHttpUri(TO_SYSTEM, "anUrl");
    private static final SimpleIdentifier CORRELATION_ID = new SimpleIdentifier("correlationId");
    private static final SimpleSystemAlias FROM_SYSTEM = new SimpleSystemAlias("A");
    private static final String A_DESCRIPTION = "a description";
    private static final Headers HEADERS = new SimpleHeaders().withKeyValue("k", "v");
    private static final TextBody MESSAGE_BODY = new TextBody("some content");

    private BodyFactory bodyFactory = mock(BodyFactory.class);

    @Test
    public void simpleHttpRequestCanBeSerialised() throws Exception {

        when(bodyFactory.createAMessageBody(MESSAGE_BODY.rawValue(), Body.ContentType.TEXT)).thenReturn(MESSAGE_BODY);

        final SerializationService serializationService = createASerializationService(bodyFactory);

        final Data serializedSimpleHttpRequest = serializationService.toData(new SimpleHttpRequest(
                                                                                 A_DESCRIPTION,
                                                                                 MESSAGE_BODY,
                                                                                 REQUEST_IDENTIFIER_ID,
                                                                                 CORRELATION_ID,
                                                                                 FROM_SYSTEM,
                                                                                 HttpMessage.HttpMethod.GET,
                                                                                 SERVER_URI,
                                                                                 HEADERS
                                                                             )
        );

        assertThat(serializedSimpleHttpRequest, is(notNullValue()));

        final SimpleHttpRequest deSerializedSimpleHttpRequest = serializationService.toObject(serializedSimpleHttpRequest);

        assertThat(deSerializedSimpleHttpRequest.description(), is(A_DESCRIPTION));
        assertThat(deSerializedSimpleHttpRequest.body(), is(MESSAGE_BODY));
        assertThat(deSerializedSimpleHttpRequest.identifier(), is(REQUEST_IDENTIFIER_ID));
        assertThat(deSerializedSimpleHttpRequest.correlationIdentifier(), is(CORRELATION_ID));
        assertThat(deSerializedSimpleHttpRequest.from(), is(FROM_SYSTEM));
        assertThat(deSerializedSimpleHttpRequest.to(), is(TO_SYSTEM));
        assertThat(deSerializedSimpleHttpRequest.messageType(), is(MessageTypeEnumImpl.request));
        assertThat(deSerializedSimpleHttpRequest.method(), is(HttpMessage.HttpMethod.GET));

        assertThat(deSerializedSimpleHttpRequest.uri(), is(SERVER_URI));
        assertThat(deSerializedSimpleHttpRequest.headers(), is(HEADERS));

    }

    private SerializationService createASerializationService(BodyFactory bodyFactory) {
        final SerializationConfig serializationConfig = new SerializationConfig().setGlobalSerializerConfig(
            new GlobalSerializerConfig().setImplementation(new SimpleHttpRequestStreamSerializer(bodyFactory))
        );
        return new DefaultSerializationServiceBuilder().setConfig(serializationConfig).build();
    }

}