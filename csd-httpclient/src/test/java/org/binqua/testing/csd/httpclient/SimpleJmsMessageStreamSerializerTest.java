package org.binqua.testing.csd.httpclient;

import com.hazelcast.config.GlobalSerializerConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.DefaultSerializationServiceBuilder;
import com.hazelcast.nio.serialization.SerializationService;
import org.binqua.testing.csd.client.jms.SimpleJmsMessage;
import org.junit.Test;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.BodyFactory;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.TextBody;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleJmsMessageStreamSerializerTest {

    private static final SimpleIdentifier IDENTIFIER_ID = new SimpleIdentifier("identifier");
    private static final SimpleIdentifier CORRELATION_ID = new SimpleIdentifier("correlationId");
    private static final SimpleSystemAlias FROM_SYSTEM = new SimpleSystemAlias("A");
    private static final SimpleSystemAlias TO_SYSTEM = new SimpleSystemAlias("B");
    private static final TextBody MESSAGE_BODY = new TextBody("some content");
    private static final Headers HEADERS = new SimpleHeaders().withKeyValue("k", "v");
    private static final Optional<String> SOME_DELIVERY_EXCEPTION_TEXT = Optional.of("some text");

    private BodyFactory bodyFactory = mock(BodyFactory.class);

    @Test
    public void givenASimpleJmsMessageWithADeliveryExceptionThenItCanBeSerialised() throws Exception {

        when(bodyFactory.createAMessageBody(MESSAGE_BODY.rawValue(), Body.ContentType.TEXT)).thenReturn(MESSAGE_BODY);

        final SerializationService serializationService = createASerializationService(bodyFactory);

        final Data serializedSimpleJmsMessage = serializationService.toData(new SimpleJmsMessage(
            FROM_SYSTEM,
            TO_SYSTEM,
            HEADERS,
            MESSAGE_BODY,
            IDENTIFIER_ID,
            CORRELATION_ID,
            SOME_DELIVERY_EXCEPTION_TEXT
        ));

        assertThat(serializedSimpleJmsMessage, is(notNullValue()));

        final SimpleJmsMessage deSerializedSimpleHttpResponse = serializationService.toObject(serializedSimpleJmsMessage);

        assertThat(deSerializedSimpleHttpResponse.from(), is(FROM_SYSTEM));
        assertThat(deSerializedSimpleHttpResponse.to(), is(TO_SYSTEM));
        assertThat(deSerializedSimpleHttpResponse.headers(), is(HEADERS));
        assertThat(deSerializedSimpleHttpResponse.body(), is(MESSAGE_BODY));
        assertThat(deSerializedSimpleHttpResponse.identifier(), is(IDENTIFIER_ID));
        assertThat(deSerializedSimpleHttpResponse.correlationIdentifier(), is(CORRELATION_ID));
        assertThat(deSerializedSimpleHttpResponse.deliveryExceptionText(), is(SOME_DELIVERY_EXCEPTION_TEXT));

    }

    @Test
    public void givenASimpleJmsMessageWithAnEmptyDeliveryExceptionThenItCanBeSerialised() throws Exception {

        when(bodyFactory.createAMessageBody(MESSAGE_BODY.rawValue(), Body.ContentType.TEXT)).thenReturn(MESSAGE_BODY);

        final SerializationService serializationService = createASerializationService(bodyFactory);

        final Data serializedSimpleJmsMessage = serializationService.toData(new SimpleJmsMessage(
                FROM_SYSTEM,
                TO_SYSTEM,
                HEADERS,
                MESSAGE_BODY,
                IDENTIFIER_ID,
                CORRELATION_ID,
                Optional.empty()
        ));

        assertThat(serializedSimpleJmsMessage, is(notNullValue()));

        final SimpleJmsMessage deSerializedSimpleHttpResponse = serializationService.toObject(serializedSimpleJmsMessage);

        assertThat(deSerializedSimpleHttpResponse.from(), is(FROM_SYSTEM));
        assertThat(deSerializedSimpleHttpResponse.to(), is(TO_SYSTEM));
        assertThat(deSerializedSimpleHttpResponse.headers(), is(HEADERS));
        assertThat(deSerializedSimpleHttpResponse.body(), is(MESSAGE_BODY));
        assertThat(deSerializedSimpleHttpResponse.identifier(), is(IDENTIFIER_ID));
        assertThat(deSerializedSimpleHttpResponse.correlationIdentifier(), is(CORRELATION_ID));
        assertThat(deSerializedSimpleHttpResponse.deliveryExceptionText(), is(Optional.empty()));

    }

    private SerializationService createASerializationService(BodyFactory bodyFactory) {
        final SerializationConfig serializationConfig = new SerializationConfig().setGlobalSerializerConfig(
            new GlobalSerializerConfig().setImplementation(new SimpleJmsMessageStreamSerializer(bodyFactory))
        );
        return new DefaultSerializationServiceBuilder().setConfig(serializationConfig).build();
    }

}