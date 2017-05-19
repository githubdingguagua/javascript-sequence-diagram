package org.binqua.testing.csd.client.jms;

import org.binqua.testing.csd.external.CsdJmsSenderFactoryDecorator;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.IdentifierGenerator;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.blabla.messaging.sender.JmsSenderFactory;

public class ExternalSimpleCsdJmsSenderFactoryDecorator implements CsdJmsSenderFactoryDecorator {

    private final CsdJmsSenderFactoryDecorator csdJmsSenderFactoryDecorator;

    public ExternalSimpleCsdJmsSenderFactoryDecorator(SystemAlias thisSystemAlias,
                                                      UrlAliasResolver urlAliasResolver,
                                                      MessageObserver messageObserver,
                                                      IdentifierGenerator identifierGenerator) {
        this.csdJmsSenderFactoryDecorator = new SimpleCsdJmsSenderFactoryDecorator(
                thisSystemAlias,
                urlAliasResolver,
                messageObserver,
                new SimpleJmsMessageFactory(
                        identifierGenerator,
                        new SimpleJsmMessageBodyFactory()
                )
        );
    }

    @Override
    public JmsSenderFactory decorate(JmsSenderFactory jmsSenderFactory, String to) {
        return csdJmsSenderFactoryDecorator.decorate(jmsSenderFactory, to);
    }

}
