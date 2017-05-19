package org.binqua.testing.csd.client.jms;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.IdentifierGenerator;
import org.binqua.testing.csd.external.core.JmsMessageFactory;
import org.binqua.testing.csd.httpclient.SimpleHeaders;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Enumeration;
import java.util.Optional;

class SimpleJmsMessageFactory implements JmsMessageFactory {

    private IdentifierGenerator identifierGenerator;
    private final JsmMessageBodyFactory jsmMessageBodyFactory;

    SimpleJmsMessageFactory(IdentifierGenerator identifierGenerator, JsmMessageBodyFactory jsmMessageBodyFactory) {
        this.identifierGenerator = identifierGenerator;
        this.jsmMessageBodyFactory = jsmMessageBodyFactory;
    }

    @Override
    public org.binqua.testing.csd.external.core.Message createAJmsMessage(SystemAlias from, SystemAlias to, Message message, Optional<String> anOptionalExceptionText) {
        return new SimpleJmsMessage(
                from,
                calculateSystemAliasForQueue(to, message),
                toHeaders(message),
                jsmMessageBodyFactory.createAMessageBody(message),
                identifierGenerator.newIdentifier(),
                identifierGenerator.newIdentifier(extractTraceIdFrom(message)),
                anOptionalExceptionText
        );
    }

    private Headers toHeaders(Message textMessage) {
        final Optional<Enumeration> optionalPropertyNames = extractPropertyNames(textMessage);
        if (optionalPropertyNames.isPresent()) {
            return toHeaders(optionalPropertyNames.get(), textMessage);
        }
        return new SimpleHeaders();
    }

    private Headers toHeaders(Enumeration<String> enumeration, Message textMessage) {
        SimpleHeaders simpleHeaders = new SimpleHeaders();
        while (enumeration.hasMoreElements()) {
            final String nextElement = enumeration.nextElement();
            final String stringProperty = extractPropertyValue(textMessage, nextElement);
            simpleHeaders.withKeyValue(nextElement, stringProperty);
        }
        return simpleHeaders;
    }

    private String extractPropertyValue(Message textMessage, String nextElement) {
        try {
            return textMessage.getStringProperty(nextElement);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return "Problem retrieving property value " + nextElement;
    }

    private Optional<Enumeration> extractPropertyNames(Message textMessage) {
        try {
            return Optional.of(textMessage.getPropertyNames());
        } catch (JMSException e) {
            return Optional.empty();
        }
    }

    private String extractTraceIdFrom(Message textMessage) {
        return extractPropertyValue(textMessage, "uc.trace.id");
    }

    private SimpleSystemAlias calculateSystemAliasForQueue(SystemAlias to, Message textMessage) {
        return new SimpleSystemAlias(to.name() + extractQueueName(textMessage));
    }

    private String extractQueueName(Message textMessage) {
        try {
            final String queueName = textMessage.getStringProperty("uc.destination.queue");
            return queueName == null ? "" : "." + queueName;
        } catch (JMSException e) {
            e.printStackTrace();
            return "";
        }
    }

}
