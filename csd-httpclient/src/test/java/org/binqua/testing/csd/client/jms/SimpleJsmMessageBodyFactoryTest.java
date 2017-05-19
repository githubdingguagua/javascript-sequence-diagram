package org.binqua.testing.csd.client.jms;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.JsonBody;

import javax.jms.TextMessage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleJsmMessageBodyFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String JSON_BODY_CONTENT = "{\"a\":1}";

    private final SimpleJsmMessageBodyFactory simpleJsmMessageBodyFactory = new SimpleJsmMessageBodyFactory();

    @Test
    public void givenATextMessageThenItIsPossibleCreateABody() throws Exception {
        final TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText(JSON_BODY_CONTENT);

        final Body actualMessageBody = simpleJsmMessageBodyFactory.createAMessageBody(textMessage);

        assertThat(actualMessageBody, is(new JsonBody(JSON_BODY_CONTENT)));

    }

    @Test
    public void givenABytesMessageThenItIsPossibleCreateABody() throws Exception {
        final ActiveMQBytesMessage aBytesMessage = new ActiveMQBytesMessage();
        aBytesMessage.writeChar('a');
        aBytesMessage.writeChar('b');
        aBytesMessage.storeContent();

        aBytesMessage.setReadOnlyBody(true);

        final Body actualMessageBody = simpleJsmMessageBodyFactory.createAMessageBody(aBytesMessage);

        assertThat(actualMessageBody, is(new JsonBody("\u0000a\u0000b")));

    }

    @Test
    public void givenANonTextMessageOrBytesMessageThenRuntimeExceptionIsThrown() throws Exception {

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Sorry but only interface javax.jms.TextMessage or interface javax.jms.BytesMessage are accepted messages. class org.apache.activemq.command.ActiveMQMessage is not. You have to add some code to do it");

        simpleJsmMessageBodyFactory.createAMessageBody(new ActiveMQMessage());

    }
}