package org.binqua.testing.csd.external;

import org.binqua.testing.csd.mongo.external.MongoNotifierFactory;

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;

public interface CsdNotifiers {

    ClientRequestFilter httpRequestNotifier();

    ClientResponseFilter httpResponseNotifier();

    CsdJmsSenderFactoryDecorator jsmSenderFactoryNotifier();

    MongoNotifierFactory mongoNotifierFactory();

}
