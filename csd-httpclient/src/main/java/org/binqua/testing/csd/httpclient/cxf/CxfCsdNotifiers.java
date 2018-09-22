package org.binqua.testing.csd.httpclient.cxf;

import org.binqua.testing.csd.client.jms.ExternalSimpleCsdJmsSenderFactoryDecorator;
import org.binqua.testing.csd.client.mockito.SimpleHttpUriFactory;
import org.binqua.testing.csd.client.mockito.SimpleMockitoNotifier;
import org.binqua.testing.csd.external.*;
import org.binqua.testing.csd.external.core.IdentifierGenerator;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.httpclient.HttpParametersFactory;

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;

public class CxfCsdNotifiers implements CsdNotifiers {

    private final ClientRequestFilter requestNotifier;
    private final ClientResponseFilter responseNotifier;
    private final CsdJmsSenderFactoryDecorator senderFactory;
    private final SimpleMockitoNotifier mockitoNotifier;

    public CxfCsdNotifiers(SystemAlias thisSystemAlias,
                           HttpParametersFactory httpParametersFactory,
                           IdentifierGenerator identifierGenerator,
                           UrlAliasResolver urlAliasResolver,
                           MessageObserver messageObserver) {
        this.requestNotifier = new CsdRequestNotifier(thisSystemAlias, httpParametersFactory, urlAliasResolver, messageObserver);
        this.responseNotifier = new CsdResponseNotifier(httpParametersFactory, messageObserver);
        this.senderFactory = new ExternalSimpleCsdJmsSenderFactoryDecorator(thisSystemAlias, urlAliasResolver, messageObserver, identifierGenerator);
//        this.mongoNotifierFactory = new MongoNotifierFactoryImplementation(thisSystemAlias, urlAliasResolver, messageObserver, identifierGenerator);
        this.mockitoNotifier = new SimpleMockitoNotifier(thisSystemAlias, httpParametersFactory, messageObserver, urlAliasResolver, new SimpleHttpUriFactory(), new SimpleHttpMethodExtractor());
    }

    @Override
    public ClientRequestFilter httpRequestNotifier() {
        return requestNotifier;
    }

    @Override
    public ClientResponseFilter httpResponseNotifier() {
        return responseNotifier;
    }

    @Override
    public CsdJmsSenderFactoryDecorator jsmSenderFactoryNotifier() {
        return senderFactory;
    }

//    @Override
//    public MongoNotifierFactory mongoNotifierFactory() {
//        return mongoNotifierFactory;
//    }

    @Override
    public MockitoNotifier mockitoNotifier() {
        return mockitoNotifier;
    }


}
