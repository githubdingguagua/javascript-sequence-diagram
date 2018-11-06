package org.binqua.testing.csd.bridge.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
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

    private ObjectMapper objectMapper;
    private MessageObserver messageObserver;

    private UrlAliasResolver urlAliasResolver;

    private IdentifierGenerator identifierGenerator = new TheIdentifierGenerator();

    CsdNotifiersFactoryImpl(UrlAliasResolver urlAliasResolver, ObjectMapper objectMapper) {
        this(urlAliasResolver, objectMapper, ConversationHttpMessageObserverFactory.messageObserverInstance());
    }

    CsdNotifiersFactoryImpl(UrlAliasResolver urlAliasResolver, String serviceName, MessageObserver messageObserver, ObjectMapper objectMapper) {
        this.urlAliasResolver = urlAliasResolver;
        this.serviceName = serviceName;
        this.messageObserver = messageObserver;
        this.httpParametersFactory = httpParametersFactory(objectMapper);
    }

    CsdNotifiersFactoryImpl(UrlAliasResolver urlAliasResolver, ObjectMapper objectMapper, MessageObserver messageObserver) {
        this.urlAliasResolver = urlAliasResolver;
        this.objectMapper = objectMapper;
        this.messageObserver = messageObserver;
        this.httpParametersFactory = httpParametersFactory(objectMapper);
    }

    @Override
    public CsdNotifiers newNotifiersFor(SystemAlias calleeSystemAlias) {
        return new CxfCsdNotifiers(calleeSystemAlias, httpParametersFactory, identifierGenerator, urlAliasResolver, messageObserver, objectMapper);
    }

    @Override
    public CsdNotifiers newNotifiers() {
        if (StringUtils.isEmpty(serviceName)) {
            throw new IllegalArgumentException("service name not defined");
        }
        return new CxfCsdNotifiers(new SimpleSystemAlias(serviceName), httpParametersFactory, identifierGenerator, urlAliasResolver, messageObserver, objectMapper);
    }

    private HttpParametersFactory httpParametersFactory(ObjectMapper objectMapper) {
        return new SimpleHttpParametersFactory(identifierGenerator,
                new DescriptionResolverFactory(),
                new JsonXmlContentTypeBasedBodyFactory(objectMapper)
        );
    }

    private class TheIdentifierGenerator implements IdentifierGenerator {
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