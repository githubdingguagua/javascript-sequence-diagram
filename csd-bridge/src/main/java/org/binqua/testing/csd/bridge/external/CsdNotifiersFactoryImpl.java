package org.binqua.testing.csd.bridge.external;

import org.apache.commons.lang3.StringUtils;
import org.binqua.testing.csd.external.*;
import org.binqua.testing.csd.external.*;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.IdentifierGenerator;
import org.binqua.testing.csd.external.core.JsonXmlContentTypeBasedBodyFactory;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.DescriptionResolverFactory;
import org.binqua.testing.csd.httpclient.HttpParametersFactory;
import org.binqua.testing.csd.httpclient.SimpleHttpParametersFactory;
import org.binqua.testing.csd.httpclient.SimpleIdentifier;
import org.binqua.testing.csd.httpclient.cxf.CxfCsdNotifiers;

import java.util.UUID;

class CsdNotifiersFactoryImpl implements CsdNotifiersFactory {

    private final HttpParametersFactory httpParametersFactory;

    private String serviceName;

    private MessageObserver messageObserver;

    private UrlAliasResolver urlAliasResolver;

    private IdentifierGenerator identifierGenerator = new TheIdentifierGenerator();

    CsdNotifiersFactoryImpl(UrlAliasResolver urlAliasResolver) {
        this(urlAliasResolver, ConversationHttpMessageObserverFactory.httpMessageNotifierInstance());
    }

    CsdNotifiersFactoryImpl(UrlAliasResolver urlAliasResolver, String serviceName, MessageObserver messageObserver) {
        this.urlAliasResolver = urlAliasResolver;
        this.serviceName = serviceName;
        this.messageObserver = messageObserver;
        this.httpParametersFactory = httpParametersFactory();
    }

    CsdNotifiersFactoryImpl(UrlAliasResolver urlAliasResolver, MessageObserver messageObserver) {
        this.urlAliasResolver = urlAliasResolver;
        this.messageObserver = messageObserver;
        this.httpParametersFactory = httpParametersFactory();
    }

    @Override
    public CsdNotifiers newNotifiersFor(SystemAlias calleeSystemAlias) {
        return new CxfCsdNotifiers(calleeSystemAlias, httpParametersFactory, identifierGenerator, urlAliasResolver, messageObserver);
    }

    @Override
    public CsdNotifiers newNotifiers() {
        if (StringUtils.isEmpty(serviceName)) {
            throw new IllegalArgumentException("service name not defined");
        }
        return new CxfCsdNotifiers(new SimpleSystemAlias(serviceName), httpParametersFactory, identifierGenerator, urlAliasResolver, messageObserver);
    }

    private HttpParametersFactory httpParametersFactory() {
        return new SimpleHttpParametersFactory(identifierGenerator,
                                               new DescriptionResolverFactory(),
                                               new JsonXmlContentTypeBasedBodyFactory()
        );
    }

    private class TheIdentifierGenerator implements IdentifierGenerator{
        @Override
        public Identifier newIdentifier() {
            return new SimpleIdentifier(UUID.randomUUID().toString());
        }

        @Override
        public Identifier newIdentifier(String externalIdentifier) {
            return new SimpleIdentifier(externalIdentifier);
        }
    }

}