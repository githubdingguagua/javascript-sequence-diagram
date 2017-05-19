package org.binqua.testing.csd.formatter.report.conversation;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.binqua.testing.csd.JsonTestUtil.asJsonAfterReplaceDoubleQuotes;

public class ScenarioTest  {

    @Test
    public void scenarioJsonIsCorrect() throws Exception {
        final String name = "scenario Name";
        final String id = "scenario Id";

        asJsonAfterReplaceDoubleQuotes(new Scenario(name, id), is("{'name':'scenario Name','id':'scenario Id'}"));
    }

}