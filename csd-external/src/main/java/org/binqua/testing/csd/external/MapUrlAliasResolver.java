package org.binqua.testing.csd.external;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class MapUrlAliasResolver implements UrlAliasResolver {

    private static final Pattern ALIAS_VALUE_REG_EX = Pattern.compile("[a-zA-Z]+[[a-zA-Z]*|_?]*[a-zA-Z]+");
    private Map<String, String> aliasUrlMap;

    public MapUrlAliasResolver(Map<String, String> aliasUrlMap) {
        checkAliasSyntaxIsValid(aliasUrlMap);
        this.aliasUrlMap = aliasUrlMap;

    }

    private void checkAliasSyntaxIsValid(Map<String, String> aliasUrlMap) {
        aliasUrlMap.values().stream().forEach(aliasValue -> {
            final Matcher matcher = ALIAS_VALUE_REG_EX.matcher(aliasValue);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(format("Alias key %s is not valid.Please use only underscore and/or letters", aliasValue));
            }
        });
    }

    @Override
    public String aliasFromUrl(String urlToBeAnalysed) {
        return aliasUrlMap.get(aliasUrlMap.keySet()
                                   .stream()
                                   .filter(urlToBeAnalysed::startsWith)
                                   .findFirst()
                                   .orElseThrow(() -> new IllegalArgumentException(format("Cannot find system %s in\n%s", urlToBeAnalysed, mapToString(aliasUrlMap)))));
    }

    private String mapToString(Map<String, String> aliasUrlMap) {
        return aliasUrlMap.entrySet()
            .stream()
            .map(x -> format("[%s, %s]", x.getKey(), x.getValue()))
            .collect(Collectors.joining("\n"));
    }
}
