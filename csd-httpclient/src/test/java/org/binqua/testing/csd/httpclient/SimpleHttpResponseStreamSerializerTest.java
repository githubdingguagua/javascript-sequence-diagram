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

public class SimpleHttpResponseStreamSerializerTest {

    private static final SimpleIdentifier RESPONSE_IDENTIFIER_ID = new SimpleIdentifier("responseIdentifierId");
    private static final SimpleIdentifier CORRELATION_ID = new SimpleIdentifier("correlationId");
    private static final Headers HEADERS = new SimpleHeaders().withKeyValue("k", "v");
    private static final SimpleSystemAlias FROM_SYSTEM = new SimpleSystemAlias("A");
    private static final SimpleSystemAlias TO_SYSTEM = new SimpleSystemAlias("B");
    private static final String A_DESCRIPTION = "a description";
    private static final int STATUS = 200;
    private static final TextBody MESSAGE_BODY = new TextBody("some content");

    private BodyFactory bodyFactory = mock(BodyFactory.class);

    @Test
    public void simpleHttpResponseCanBeSerialised() throws Exception {

        when(bodyFactory.createAMessageBody(MESSAGE_BODY.rawValue(), Body.ContentType.TEXT)).thenReturn(MESSAGE_BODY);

        final SerializationService serializationService = createASerializationService(bodyFactory);

        final Data serializedSimpleHttpResponse = serializationService.toData(new SimpleHttpResponse(
            A_DESCRIPTION,
            MESSAGE_BODY,
            RESPONSE_IDENTIFIER_ID,
            STATUS,
            HEADERS,
            FROM_SYSTEM,
            TO_SYSTEM,
            CORRELATION_ID
        ));

        assertThat(serializedSimpleHttpResponse, is(notNullValue()));

        final HttpResponse deSerializedSimpleHttpResponse = serializationService.toObject(serializedSimpleHttpResponse);

        assertThat(deSerializedSimpleHttpResponse.description(), is(A_DESCRIPTION));
        assertThat(deSerializedSimpleHttpResponse.body(), is(MESSAGE_BODY));
        assertThat(deSerializedSimpleHttpResponse.identifier(), is(RESPONSE_IDENTIFIER_ID));
        assertThat(deSerializedSimpleHttpResponse.correlationIdentifier(), is(CORRELATION_ID));
        assertThat(deSerializedSimpleHttpResponse.from(), is(FROM_SYSTEM));
        assertThat(deSerializedSimpleHttpResponse.to(), is(TO_SYSTEM));
        assertThat(deSerializedSimpleHttpResponse.messageType(), is(MessageTypeEnumImpl.response));

        assertThat(deSerializedSimpleHttpResponse.status(), is(STATUS));
        assertThat(deSerializedSimpleHttpResponse.headers(), is(HEADERS));
    }

    private SerializationService createASerializationService(BodyFactory bodyFactory) {
        final SerializationConfig serializationConfig = new SerializationConfig().setGlobalSerializerConfig(
            new GlobalSerializerConfig().setImplementation(new SimpleHttpResponseStreamSerializer(bodyFactory))
        );
        return new DefaultSerializationServiceBuilder().setConfig(serializationConfig).build();
    }

}