package org.binqua.testing.csd.client.jms;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.JmsMessageFactory;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.external.core.MessageObserver;
import uk.gov.dwp.universe.messaging.sender.JmsSender;

import javax.jms.TextMessage;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CsdJmsNotifierSenderTest {

    private static final String ORIGINAL_EXCEPTION_TEXT = "Problem sending jms message";
    private static final String CAUSE_EXCEPTION_TEXT = "Cause exception text";

    private final JmsMessageFactory theJsmMessageFactory = mock(JmsMessageFactory.class);
    private final MessageObserver theMessageObserver = mock(MessageObserver.class);
    private final JmsSender originalSender = mock(JmsSender.class);
    private final SystemAlias fromAlias = mock(SystemAlias.class);
    private final TextMessage aTextMessage  = mock(TextMessage.class);
    private final SystemAlias toAlias = mock(SystemAlias.class);
    private final Message theConversationMessageToBeNotify = mock(Message.class);

    private final CsdJmsNotifierSender csdJmsNotifierSender = new CsdJmsNotifierSender(originalSender, fromAlias, toAlias, theMessageObserver, theJsmMessageFactory);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void sendAMessageAndNotifyToMessageObserver() throws Exception {

        when(theJsmMessageFactory.createAJmsMessage(fromAlias, toAlias, aTextMessage, Optional.empty())).thenReturn(theConversationMessageToBeNotify);

        csdJmsNotifierSender.send(aTextMessage);

        verify(originalSender).send(aTextMessage);
        verify(theMessageObserver).notify(theConversationMessageToBeNotify);

    }

    @Test
    public void givenAnExceptionSendingAMessageThenTheExceptionIsSendToMessageObserverToo() throws Exception {

        Throwable cause = new RuntimeException(CAUSE_EXCEPTION_TEXT);
        RuntimeException originalExceptionToBeThrown = new RuntimeException(ORIGINAL_EXCEPTION_TEXT, cause);

        expectedException.expect(is(originalExceptionToBeThrown));

        doThrow(originalExceptionToBeThrown).when(originalSender).send(aTextMessage);

        when(theJsmMessageFactory.createAJmsMessage(fromAlias, toAlias, aTextMessage, Optional.of(ORIGINAL_EXCEPTION_TEXT + "\n" + CAUSE_EXCEPTION_TEXT))).thenReturn(theConversationMessageToBeNotify);

        csdJmsNotifierSender.send(aTextMessage);

        verify(theMessageObserver).notify(theConversationMessageToBeNotify);
    }

}