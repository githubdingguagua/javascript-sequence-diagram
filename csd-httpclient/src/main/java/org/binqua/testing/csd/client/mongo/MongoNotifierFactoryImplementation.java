package org.binqua.testing.csd.client.mongo;

import com.mongodb.DBCollection;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.UrlAliasResolver;
import org.binqua.testing.csd.external.core.IdentifierGenerator;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.mongo.external.MongoNotifier;
import org.binqua.testing.csd.mongo.external.MongoNotifierFactory;

public class MongoNotifierFactoryImplementation implements MongoNotifierFactory {

    private MessageObserver messageObserver;

    public MongoNotifierFactoryImplementation(SystemAlias thisSystemAlias,
                                              UrlAliasResolver urlAliasResolver,
                                              MessageObserver messageObserver,
                                              IdentifierGenerator identifierGenerator) {

        this.messageObserver = messageObserver;
    }

    @Override
    public MongoNotifier createAMongoNotifier(String fromSystem, DBCollection dbCollection) {
        return new MongoNotifierImpl(new SimpleSystemAlias(fromSystem),
                messageObserver,
                dbCollection,
                new SimpleMongoMessageBodyFactory());
    }

}
