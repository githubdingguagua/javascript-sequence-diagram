package org.binqua.testing.csd.client.mongo;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.MessageObserver;
import org.binqua.testing.csd.mongo.external.MongoNotifier;

import java.util.Optional;


public class MongoNotifierImpl implements MongoNotifier {

    private MessageObserver messageObserver;
    private MongoMessageBodyFactory mongoMessageBodyFactory;

    public MongoNotifierImpl(SystemAlias fromServiceName,
                             MessageObserver messageObserver,
                             DBCollection dbCollection,
                             MongoMessageBodyFactory mongoMessageBodyFactory) {
        this.messageObserver = messageObserver;
        this.mongoMessageBodyFactory = mongoMessageBodyFactory;
//        this.to = new SimpleSystemAlias(dbCollection.get)
    }

    @Override
    public void insert(DBObject[] document, WriteResult insertResult) {
        System.out.println(document);
        System.out.println(insertResult);
//        messageObserver.notify(mongoMessageFactory.createAMessage(from,to,description,mongoMessageBodyFactory.createAMessageBody(document,insertResult)));
    }

    @Override
    public void insert(DBObject[] document, Optional<String> exceptionText) {
        System.out.println(document);
        System.out.println(exceptionText);

    }

    @Override
    public void save(DBObject document, WriteResult writeResult) {
        System.out.println(document);
        System.out.println(writeResult);

    }

    @Override
    public void save(DBObject document, Optional<String> exceptionText) {
        System.out.println(document);
        System.out.println(exceptionText);

    }
}
