package org.binqua.testing.csd.mongo.external;

import com.mongodb.DBCollection;

public interface MongoNotifierFactory {

    MongoNotifier createAMongoNotifier(String fromSystem, DBCollection dbCollection);

}

