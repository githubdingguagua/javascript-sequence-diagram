package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.BodyFactory;

import java.io.IOException;

public class SimpleHttpRequestStreamSerializer implements StreamSerializer<SimpleHttpRequest> {

    private final BodyFactory bodyFactory;

    private final Gson gson = new Gson();

    public SimpleHttpRequestStreamSerializer(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    @Override
    public void write(ObjectDataOutput out, SimpleHttpRequest httpRequest) throws IOException {
        out.writeUTF(httpRequest.description());
        out.writeUTF(httpRequest.body().rawValue());
        out.writeUTF(httpRequest.body().contentType().toString());
        out.writeUTF(httpRequest.identifier().id());
        out.writeUTF(httpRequest.correlationIdentifier().id());
        out.writeUTF(httpRequest.from().name());
        out.writeUTF(httpRequest.method().name());
        out.writeUTF(httpRequest.to().name());
        out.writeUTF(httpRequest.uri().value());
        out.writeUTF(gson.toJson(httpRequest.headers().asJson()));
    }

    @Override
    public SimpleHttpRequest read(ObjectDataInput in) throws IOException {
        return new SimpleHttpRequest(in.readUTF(),
                                     bodyFactory.createAMessageBody(in.readUTF(), Body.ContentType.valueOf(in.readUTF())),
                                     new SimpleIdentifier(in.readUTF()),
                                     new SimpleIdentifier(in.readUTF()),
                                     new SimpleSystemAlias(in.readUTF()),
                                     HttpMessage.HttpMethod.toHttpMethod(in.readUTF()),
                                     new SimpleHttpUri(new SimpleSystemAlias(in.readUTF()), in.readUTF()),
                                     gson.fromJson(in.readUTF(), SimpleHeaders.class)
        );
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {
    }
}
