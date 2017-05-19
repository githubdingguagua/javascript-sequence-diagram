package org.binqua.testing.csd.external;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

public class MapUrlAliasResolverTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenOneSystemThenAliasFromUrlExtractTheRightAlias() throws Exception {
        final String firstSystemUrlPrefix = "http://localhost:80/firstSystemAlias";
        final String firstSystemAlias = "firstSystemAlias";

        final Map<String, String> aliasUrlMap = new HashMap<>();
        aliasUrlMap.put(firstSystemUrlPrefix, firstSystemAlias);

        assertThat(new MapUrlAliasResolver(aliasUrlMap).aliasFromUrl(firstSystemUrlPrefix + "/doThis"), is(firstSystemAlias));
    }

    @Test
    public void aliasCanContainOnlyUnderscoreDash() throws Exception {
        assertThatAliasKeyIsNotValid("a-b");
        assertThatAliasKeyIsNotValid("a b");
        assertThatAliasKeyIsNotValid("a_b_");
        assertThatAliasKeyIsNotValid("_a_b");

        assertThatAliasKeyIValid("a_b");
        assertThatAliasKeyIValid("A_B");
    }

    private void assertThatAliasKeyIValid(String aliasValue) {
        final Map<String, String> aliasUrlMap = new HashMap<>();
        aliasUrlMap.put("something", aliasValue);

        new MapUrlAliasResolver(aliasUrlMap);
    }

    private void assertThatAliasKeyIsNotValid(String keyValue) {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Alias key " + keyValue + " is not valid.Please use only underscore and/or letters");

        final Map<String, String> aliasUrlMap = new HashMap<>();
        aliasUrlMap.put("something", keyValue);

        new MapUrlAliasResolver(aliasUrlMap);
    }

    @Test
    public void givenAnUnmappedUrlThenAliasFromUrlThrowsIllegalArgumentException() throws Exception {
        final String firstSystemUrlPrefix = "http://localhost:80/firstSystemAlias";
        final String firstSystemAlias = "firstSystemAlias";

        final Map<String, String> aliasUrlMap = new HashMap<>();
        aliasUrlMap.put(firstSystemUrlPrefix, firstSystemAlias);

        final String urlToAnaylsed = "someUrl/doThis";

        try {
            new MapUrlAliasResolver(aliasUrlMap).aliasFromUrl(urlToAnaylsed);

            fail(IllegalArgumentException.class + " should have been thrown!");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), is(equalTo(format("Cannot find system %s in\n[http://localhost:80/firstSystemAlias, firstSystemAlias]", urlToAnaylsed))));
        }
    }
}