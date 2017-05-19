package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class Scenario implements ToJson{

    private String name;
    private String id;

    public Scenario(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public JsonElement asJson() {
        return new Gson().toJsonTree(this);
    }
}
