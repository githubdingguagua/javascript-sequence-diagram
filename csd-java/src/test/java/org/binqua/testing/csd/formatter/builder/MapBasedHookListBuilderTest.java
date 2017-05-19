package org.binqua.testing.csd.formatter.builder;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapBasedHookListBuilderTest {

    private final MapBasedHookListBuilder mapBasedHookListBuilder = new MapBasedHookListBuilder();

    @Test
    public void given2HooksThenHookListIsBuildCorrectly() throws Exception {

        final Match firstMatch = mock(Match.class);
        final Result firstResult = mock(Result.class);

        final Match secondMatch = mock(Match.class);
        final Result secondResult = mock(Result.class);

        final Map<String, Object> firstMatchMap = mock(Map.class);
        when(firstMatch.toMap()).thenReturn(firstMatchMap);

        final Map<String, Object> firstResultMap = mock(Map.class);
        when(firstResult.toMap()).thenReturn(firstResultMap);

        final Map<String, Object> secondMatchMap = mock(Map.class);
        when(secondMatch.toMap()).thenReturn(secondMatchMap);

        final Map<String, Object> secondResultMap = mock(Map.class);
        when(secondResult.toMap()).thenReturn(secondResultMap);

        mapBasedHookListBuilder.hookWith(firstMatch, firstResult);
        mapBasedHookListBuilder.hookWith(secondMatch, secondResult);

        final Map expectedFirstHookMap = new HashMap();
        expectedFirstHookMap.put("match", firstMatchMap);
        expectedFirstHookMap.put("result", firstResultMap);

        final Map expectedSecondHookMap = new HashMap();
        expectedSecondHookMap.put("match", secondMatchMap);
        expectedSecondHookMap.put("result", secondResultMap);

        final List<Map<String, Object>> expectedHookList = asList(expectedFirstHookMap, expectedSecondHookMap);

        assertThat(mapBasedHookListBuilder.hookList(), is(expectedHookList));

    }

    @Test
    public void givenNOHooksThenHookListIsBuildCorrectly() throws Exception {

        assertThat(mapBasedHookListBuilder.hookList(), hasSize(0));

    }
}