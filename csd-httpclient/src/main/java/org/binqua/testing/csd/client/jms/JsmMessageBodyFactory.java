package org.binqua.testing.csd.client.jms;

import org.binqua.testing.csd.external.core.Body;

import javax.jms.Message;

interface JsmMessageBodyFactory {

    Body createAMessageBody(Message textMessage);

}
