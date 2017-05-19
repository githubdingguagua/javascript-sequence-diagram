package org.binqua.testing.csd.formatter.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;

class MapBasedHookListBuilder implements HookListBuilder {

    private List<Map<String, Object>> hooks = new ArrayList<>();

    @Override
    public void hookWith(Match match, Result result) {
        hooks.add(buildHookMap(match, result));
    }

    @Override
    public List<Map<String, Object>> hookList() {
        return hooks;
    }

    private Map buildHookMap(final Match match, final Result result) {
        final Map hookMap = new HashMap();
        hookMap.put("match", match.toMap());
        hookMap.put("result", result.toMap());
        return hookMap;
    }

}
