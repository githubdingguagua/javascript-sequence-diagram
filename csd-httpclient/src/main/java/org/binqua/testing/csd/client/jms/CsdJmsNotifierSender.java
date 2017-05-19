package org.binqua.testing.csd.client.jms;

import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.JmsMessageFactory;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.blabla.data.Resource;
import org.binqua.blabla.messaging.sender.JmsSender;

import javax.jms.Message;
import java.util.Optional;

class CsdJmsNotifierSender implements JmsSender {

    private final MessageObserver messageObserver;
    private final JmsMessageFactory jmsMessageFactory;
    private final JmsSender sender;
    private final SystemAlias from;
    private final SystemAlias to;

    CsdJmsNotifierSender(JmsSender sender,
                         SystemAlias from,
                         SystemAlias to,
                         MessageObserver messageObserver,
                         JmsMessageFactory jmsMessageFactory) {
        this.sender = sender;
        this.from = from;
        this.to = to;
        this.messageObserver = messageObserver;
        this.jmsMessageFactory = jmsMessageFactory;
    }

    @Override
    public void send(Message message) {
        try {
            sender.send(message);
        } catch (Exception ex) {
            messageObserver.notify(jmsMessageFactory.createAJmsMessage(from, to, message, toOptionalExceptionText(ex)));
            throw ex;
        }
        messageObserver.notify(jmsMessageFactory.createAJmsMessage(from, to, message, Optional.empty()));
    }

    private Optional<String> toOptionalExceptionText(Exception ex) {
        String causeMessages = ex.getMessage();
        Throwable cause = ex.getCause();
        while (cause != null) {
            causeMessages += "\n" + cause.getMessage();
            cause = cause.getCause();
        }
        return Optional.of(causeMessages);
    }

    @Override
    public Resource getResource() {
        return sender.getResource();
    }
}
