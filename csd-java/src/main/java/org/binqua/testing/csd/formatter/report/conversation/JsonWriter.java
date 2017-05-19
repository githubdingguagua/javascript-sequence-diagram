package org.binqua.testing.csd.formatter.report.conversation;

import java.io.File;

public interface JsonWriter {

    void write(File file, ToJson toJson);

}
