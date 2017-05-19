package org.binqua.testing.csd.client.jms;

import org.apache.commons.io.IOUtils;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.JsonBody;
import org.binqua.testing.csd.external.core.XmlBody;
import org.binqua.blabla.messaging.message.BytesMessageInputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

class SimpleJsmMessageBodyFactory implements JsmMessageBodyFactory {

    @Override
    public Body createAMessageBody(Message aJmsMessage) {
        if (aJmsMessage instanceof TextMessage) {
            return toJson((TextMessage) aJmsMessage);
        }
        if (aJmsMessage instanceof BytesMessage) {
            return toJson((BytesMessage) aJmsMessage);
        }
        throw new RuntimeException(String.format("Sorry but only %s or %s are accepted messages. %s is not. You have to add some code to do it", TextMessage.class, BytesMessage.class, aJmsMessage.getClass()));
    }

    private Body toJson(BytesMessage aJmsMessage) {
        BytesMessageInputStream bytesMessageInputStream = new BytesMessageInputStream(aJmsMessage);
        try {
            return new JsonBody(new String(IOUtils.toByteArray(bytesMessageInputStream)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Body toJson(TextMessage aTextMessage) {
        try {
            final String body = aTextMessage.getText();
            if (body != null && body.startsWith("<")) {
                return new XmlBody(body);
            }
            return new JsonBody(body == null ? "" : body);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
