package org.binqua.testing.csd.client.jms;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.mockito.Mockito;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.IdentifierGenerator;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.httpclient.SimpleHeaders;

import javax.jms.TextMessage;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SimpleJmsMessageFactoryTest {

    private static final String THE_EXTERNAL_TRACE_ID = "thisIsTheTraceId";

    private final Optional<String> anOptionalExceptionText = Optional.of("some exception text");
    private final IdentifierGenerator identifierGenerator = mock(IdentifierGenerator.class);
    private final Identifier theCsdMessageIdentifier = Mockito.mock(Identifier.class);
    private final Identifier theCsdCorrelationId = Mockito.mock(Identifier.class);
    private final JsmMessageBodyFactory jsmMessageBodyFactory = mock(JsmMessageBodyFactory.class);
    private final Body aMessageBody = mock(Body.class);

    private final SimpleJmsMessageFactory simpleJmsMessageFactory = new SimpleJmsMessageFactory(identifierGenerator, jsmMessageBodyFactory);

    @Test
    public void givenAValidTextMessageThenWeCreateTheRightJmsConversationMessage() throws Exception {

        final TextMessage aTextMessage = new ActiveMQTextMessage();
        aTextMessage.setText("[]");
        aTextMessage.setStringProperty("uc.destination.queue", "q1");
        aTextMessage.setStringProperty("uc.trace.id", THE_EXTERNAL_TRACE_ID);

        when(identifierGenerator.newIdentifier()).thenReturn(theCsdMessageIdentifier);
        when(identifierGenerator.newIdentifier(THE_EXTERNAL_TRACE_ID)).thenReturn(theCsdCorrelationId);

        when(jsmMessageBodyFactory.createAMessageBody(aTextMessage)).thenReturn(aMessageBody);

        final Message actualMessage = simpleJmsMessageFactory.createAJmsMessage(new SimpleSystemAlias("systemA"), () -> "broker1", aTextMessage, anOptionalExceptionText);

        final SimpleJmsMessage expectedMessage = new SimpleJmsMessage(
                new SimpleSystemAlias("systemA"),
                new SimpleSystemAlias("broker1.q1"),
                new SimpleHeaders().withKeyValue("uc.destination.queue", "q1").withKeyValue("uc.trace.id", THE_EXTERNAL_TRACE_ID),
                aMessageBody,
                theCsdMessageIdentifier,
                theCsdCorrelationId,
                anOptionalExceptionText
        );

        assertThat(actualMessage, is(expectedMessage));

    }

    @Test
    public void givenAnEmptyTextMessageThenWeCreateTheRightJmsConversationMessage() throws Exception {

        when(identifierGenerator.newIdentifier()).thenReturn(theCsdMessageIdentifier);
        when(identifierGenerator.newIdentifier(null)).thenReturn(theCsdCorrelationId);

        final TextMessage aTextMessage = new ActiveMQTextMessage();

        when(jsmMessageBodyFactory.createAMessageBody(aTextMessage)).thenReturn(aMessageBody);

        final Message actualMessage = simpleJmsMessageFactory.createAJmsMessage(new SimpleSystemAlias("systemA"), () -> "broker1", aTextMessage, anOptionalExceptionText);

        final SimpleJmsMessage expectedMessage = new SimpleJmsMessage(new SimpleSystemAlias("systemA"),
                new SimpleSystemAlias("broker1"),
                new SimpleHeaders(),
                aMessageBody,
                theCsdMessageIdentifier,
                theCsdCorrelationId,
                anOptionalExceptionText
        );

        assertThat(actualMessage, is(expectedMessage));

    }

}