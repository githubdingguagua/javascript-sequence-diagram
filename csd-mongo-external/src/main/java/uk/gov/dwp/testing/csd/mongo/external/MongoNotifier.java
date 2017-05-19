package org.binqua.testing.csd.mongo.external;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import java.util.Optional;

public interface MongoNotifier {

    void insert(DBObject[] document, WriteResult insertResult);

    void insert(DBObject[] document, Optional<String> exceptionText);

    void save(DBObject document, WriteResult writeResult);

    void save(DBObject document, Optional<String> exceptionText);

}

