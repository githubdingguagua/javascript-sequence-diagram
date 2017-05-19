package org.binqua.testing.csd.formatter.builder;

import java.util.List;
import java.util.Map;

import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;

public interface HookListBuilder {

    void hookWith(Match match, Result result);

    List<Map<String, Object>> hookList();

}
