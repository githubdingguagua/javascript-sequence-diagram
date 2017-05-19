package org.binqua.testing.csd.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class HttpClientParameters {

    public static class HttpBody {

        private String body;

        private HttpBody(String body) {
            this.body = body;
        }

        public String value() {
            return body;
        }

        public static HttpBody aBodyWith(String test) {
            if(test == null || test.isEmpty() || test.equals("null")) {
                return new HttpBody("");
            }
            return new HttpBody(test);
        }

        @Override
        public String toString() {
            return "body: " + body;
        }

        public static HttpBody empty() {
            return aBodyWith("");
        }
    }

    public static class UriPath implements DataSerializable {

        private String path;

        private String description;

        private UriPath(String path, String description) {
            this.path = path;
            this.description = description;
        }

        public String value() {
            return path;
        }

        public String description() {
            return description;
        }

        public JsonElement asJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value", path);
            jsonObject.addProperty("alias", description);
            return jsonObject;
        }

        public static UriPath with(String path, String description) {
            return new UriPath(path, description);
        }

        public static UriPath fromPath(String path, String description) {
            return new UriPath(path, description);
        }

        @Override
        public String toString() {
            return "path: " + path;
        }

        @Override
        public void writeData(ObjectDataOutput out) throws IOException {
            out.writeUTF(path);
            out.writeUTF(description);
        }

        @Override
        public void readData(ObjectDataInput in) throws IOException {
            path = in.readUTF();
            description = in.readUTF();
        }
    }

    public static class ReturningInstance<T> {

        private Class<T> clazz;

        private ReturningInstance(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Class<T> clazz() {
            return clazz;
        }

        public static <T> ReturningInstance<T> returningA(Class<T> clazz) {
            return new ReturningInstance<T>(clazz);
        }

        public static <T> ReturningInstance<T> a(Class<T> clazz) {
            return new ReturningInstance<T>(clazz);
        }
    }
}
