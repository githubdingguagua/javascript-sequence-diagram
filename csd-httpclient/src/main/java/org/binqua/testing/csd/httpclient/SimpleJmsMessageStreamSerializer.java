package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.binqua.testing.csd.client.jms.SimpleJmsMessage;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.BodyFactory;

import java.io.IOException;
import java.util.Optional;

public class SimpleJmsMessageStreamSerializer implements StreamSerializer<SimpleJmsMessage> {

    private final Gson gson = new Gson();

    private final BodyFactory bodyFactory;

    public SimpleJmsMessageStreamSerializer(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    @Override
    public void write(ObjectDataOutput out, SimpleJmsMessage simpleJmsMessage) throws IOException {
        out.writeUTF(simpleJmsMessage.from().name());
        out.writeUTF(simpleJmsMessage.to().name());
        out.writeUTF(gson.toJson(simpleJmsMessage.headers().asJson()));
        out.writeUTF(simpleJmsMessage.body().rawValue());
        out.writeUTF(simpleJmsMessage.body().contentType().toString());
        out.writeUTF(simpleJmsMessage.identifier().id());
        out.writeUTF(simpleJmsMessage.correlationIdentifier().id());
        if (simpleJmsMessage.deliveryExceptionText().isPresent()) {
            out.writeBoolean(true);
            out.writeUTF(simpleJmsMessage.deliveryExceptionText().get());
        }else{
            out.writeBoolean(false);
        }
    }

    @Override
    public SimpleJmsMessage read(ObjectDataInput in) throws IOException {
        return new SimpleJmsMessage(
                new SimpleSystemAlias(in.readUTF()),
                new SimpleSystemAlias(in.readUTF()),
                gson.fromJson(in.readUTF(), SimpleHeaders.class),
                bodyFactory.createAMessageBody(in.readUTF(), Body.ContentType.valueOf(in.readUTF())),
                new SimpleIdentifier(in.readUTF()),
                new SimpleIdentifier(in.readUTF()),
                in.readBoolean() ? Optional.of(in.readUTF()) : Optional.empty()
        );
    }

    @Override
    public int getTypeId() {
        return 3;
    }

    @Override
    public void destroy() {
    }
}
