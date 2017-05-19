package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.client.jms.SimpleJmsMessage;
import org.binqua.testing.csd.httpclient.HttpRequest;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.Message;

import java.util.List;

import static java.lang.String.format;

class ClickableSvgDecorator implements SvgDecorator {

    @Override
    public String decorate(String toBeDecorated, MessageDescriptionDictionary messageDescriptionDictionary, List<Message> messages) {
        for (Message aMessage : messages) {
            final Identifier id = aMessage.identifier();
            final String description = messageDescriptionDictionary.get(id);
            final String textToBeReplaced = format(">%s<", description);
            final String newText = format(" id=\"%s\" class=\"clickable %s\" >%s<", id.id(), messageTypeCss(aMessage), description);
            toBeDecorated = toBeDecorated.replaceFirst(textToBeReplaced, newText);
        }
        return toBeDecorated;
    }

    private String messageTypeCss(Message message) {
        String cssString = "";
        if (message instanceof HttpRequest) {
            cssString +=((HttpRequest)message).method().name().toLowerCase() + "-style ";
        }
        if (message instanceof SimpleJmsMessage) {
            cssString += "jmsMessage ";
            if (((SimpleJmsMessage) message).deliveryExceptionText().isPresent()){
                cssString += "undeliveredMessage";
            }
        }
        return cssString;
    }

}
