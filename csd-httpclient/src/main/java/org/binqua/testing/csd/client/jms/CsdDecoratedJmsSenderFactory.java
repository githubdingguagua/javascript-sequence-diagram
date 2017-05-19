package org.binqua.testing.csd.client.jms;

import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.JmsMessageFactory;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.blabla.messaging.sender.JmsSender;
import org.binqua.blabla.messaging.sender.JmsSenderFactory;

class CsdDecoratedJmsSenderFactory implements JmsSenderFactory {

    private final MessageObserver messageObserver;
    private final JmsMessageFactory jmsMessageFactory;
    private final JmsSenderFactory jmsSenderFactory;
    private final SystemAlias from;
    private final SystemAlias to;

    CsdDecoratedJmsSenderFactory(JmsSenderFactory jmsSenderFactory,
                                 SystemAlias from,
                                 SystemAlias to,
                                 MessageObserver messageObserver,
                                 JmsMessageFactory jmsMessageFactory) {
        this.jmsSenderFactory = jmsSenderFactory;
        this.from = from;
        this.to = to;
        this.messageObserver = messageObserver;
        this.jmsMessageFactory = jmsMessageFactory;
    }

    public JmsSender createSender(String destinationQueue) {
        return new CsdJmsNotifierSender(
                jmsSenderFactory.createSender(destinationQueue),
                from,
                to,
                messageObserver,
                jmsMessageFactory
        );
    }

}


