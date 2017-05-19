package org.binqua.testing.csd.client.jms;

import org.binqua.testing.csd.external.CsdJmsSenderFactoryDecorator;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.JmsMessageFactory;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.blabla.messaging.sender.JmsSenderFactory;

class SimpleCsdJmsSenderFactoryDecorator implements CsdJmsSenderFactoryDecorator {

    private final UrlAliasResolver urlAliasResolver;
    private final MessageObserver messageObserver;
    private final JmsMessageFactory jmsMessageFactory;
    private final SystemAlias thisSystemAlias;

    SimpleCsdJmsSenderFactoryDecorator(SystemAlias thisSystemAlias,
                                       UrlAliasResolver urlAliasResolver,
                                       MessageObserver messageObserver,
                                       JmsMessageFactory jmsMessageFactory
    ) {
        this.thisSystemAlias = thisSystemAlias;
        this.urlAliasResolver = urlAliasResolver;
        this.messageObserver = messageObserver;
        this.jmsMessageFactory = jmsMessageFactory;
    }

    @Override
    public JmsSenderFactory decorate(JmsSenderFactory jmsSenderFactory, String to) {
        return new CsdDecoratedJmsSenderFactory(jmsSenderFactory,
                thisSystemAlias,
                new SimpleSystemAlias(urlAliasResolver.aliasFromUrl(to)),
                messageObserver,
                jmsMessageFactory);
    }
}
