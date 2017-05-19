package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ApacheJsonWriter implements JsonWriter {

    @Override
    public void write(File file, ToJson toJson) {
        file.getParentFile().mkdirs();

        try {
            FileUtils.writeStringToFile(file, prettyPrint(toJson));
        } catch (IOException e) {
            throw new RuntimeException("Problem creating file " + file, e);
        }
    }

    private String prettyPrint(ToJson featureConversation) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(featureConversation.asJson());
    }
}
