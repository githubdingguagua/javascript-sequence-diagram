package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.BodyFactory;

import java.io.IOException;

import static org.binqua.testing.csd.external.core.Body.ContentType;

public class SimpleHttpResponseStreamSerializer implements StreamSerializer<SimpleHttpResponse> {

    private final Gson gson = new Gson();
    private final BodyFactory bodyFactory;

    public SimpleHttpResponseStreamSerializer(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    @Override
    public void write(ObjectDataOutput out, SimpleHttpResponse httpResponse) throws IOException {
        out.writeUTF(httpResponse.description());
        out.writeUTF(httpResponse.body().rawValue());
        out.writeUTF(httpResponse.body().contentType().toString());
        out.writeUTF(httpResponse.identifier().id());
        out.writeInt(httpResponse.status());
        out.writeUTF(gson.toJson(httpResponse.headers().asJson()));
        out.writeUTF(httpResponse.from().name());
        out.writeUTF(httpResponse.to().name());
        out.writeUTF(httpResponse.correlationIdentifier().id());
    }

    @Override
    public SimpleHttpResponse read(ObjectDataInput in) throws IOException {
        return new SimpleHttpResponse(
            in.readUTF(),
            bodyFactory.createAMessageBody(in.readUTF(), ContentType.valueOf(in.readUTF())),
            new SimpleIdentifier(in.readUTF()),
            in.readInt(),
            new Gson().fromJson(in.readUTF(), SimpleHeaders.class),
            new SimpleSystemAlias(in.readUTF()),
            new SimpleSystemAlias(in.readUTF()),
            new SimpleIdentifier(in.readUTF())
        );
    }

    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    public void destroy() {
    }
}
