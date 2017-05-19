package org.binqua.testing.csd.formatter.report.featuremenu;

import com.google.gson.GsonBuilder;

import org.binqua.testing.csd.formatter.report.conversation.ToJson;

public class StringBuilderFeatureMenuContentGenerator implements FeatureMenuContentGenerator{

    @Override
    public String content(ToJson someJsonContent) {
        return new StringBuilder().append("var testsMenu = ").append(prettyPrint(someJsonContent)).toString();
    }

    private String prettyPrint(ToJson someJsonContent) {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(someJsonContent.asJson());
    }
}
