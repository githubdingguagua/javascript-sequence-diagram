package org.binqua.testing.csd.external;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class YamlBasedUrlAliasResolver implements UrlAliasResolver {

    public static final String NAME_PART_SEPARATOR = "_";
    private final Map<String, String> urlToAliasMap;

    private Map<String, Object> yamlAsMap;

    public YamlBasedUrlAliasResolver(String yamlFileInTheClasspath) {
        yamlAsMap = fromYamlToMap(yamlFileInTheClasspath);

        urlToAliasMap = yamlAsMap.entrySet()
            .stream()
            .filter(mapEntry ->
                        mapEntry.getValue() instanceof String &&
                        ((String) mapEntry.getValue()).startsWith("http://") &&
                        mapEntry.getKey().endsWith("Service")
            ).collect(Collectors.toMap(t -> extractServerAndPort(t),
                                       o -> toAliasConventionName(o.getKey())
                      )
            );

        if (urlToAliasMap.isEmpty()) {
            throw new IllegalArgumentException(format("There are not services mapped in %s", yamlFileInTheClasspath));
        }

    }

    private String extractServerAndPort(Map.Entry<String, Object> t) {
        final String urlToBeAnalysed = (String) t.getValue();
        return urlToBeAnalysed.substring(0, urlToBeAnalysed.lastIndexOf("/"));
    }

    private String toAliasConventionName(String key) {
        final StringBuilder modifiedString = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (Character.isUpperCase(c)) {
                modifiedString.append(NAME_PART_SEPARATOR);
            }
            modifiedString.append(c);
        }
        return modifiedString.toString().toUpperCase().replace("_SERVICE","");
    }


    @Override
    public String aliasFromUrl(String urlToBeAnalysed) {
        if(urlToBeAnalysed.contains("tcp://localhost:61616")){
            return "INTERNAL_BROKER";
        }
        if(urlToBeAnalysed.contains("tcp://localhost:61617")){
            return "EXTERNAL_BROKER";
        }
        if(urlToBeAnalysed.contains("tcp://localhost:61618")){
            return "PUBLIC_BROKER";
        }
        return urlToAliasMap.get(urlToAliasMap.keySet()
                                     .stream()
                                     .filter(urlToBeAnalysed::startsWith)
                                     .findFirst()
                                     .orElseThrow(() -> new IllegalArgumentException(message(urlToBeAnalysed))));
    }

    private String message(String urlToBeAnalysed) {
        return format("Cannot find system mapped to url %s in\n%s",
                      urlToBeAnalysed,
                      mapToString(urlToAliasMap)
        );
    }

    public String serviceName() {
        return ((String) yamlAsMap.get("serviceName")).replaceAll("-", "_").toUpperCase().replace("_SERVICE","");
    }

    private String mapToString(Map<String, String> aliasUrlMap) {
        return aliasUrlMap.entrySet()
            .stream()
            .map(x -> format("[%s, %s]", x.getKey(), x.getValue()))
            .collect(Collectors.joining("\n"));
    }

    private Map<String, Object> fromYamlToMap(String originalYamlFileName) {
        final BufferedReader reader = new BufferedReader(
            new InputStreamReader(getClass().getClassLoader().getResourceAsStream(originalYamlFileName))
        );
        return toMap(originalYamlFileName, reader);
    }

    private Map<String, Object> toMap(String originalYamlFileName, BufferedReader reader) {
        try {
            return (Map) new YamlReader(reader).read();
        } catch (YamlException e) {
            throw new RuntimeException("Cannot read yaml file " + originalYamlFileName + " from classpath", e);
        }
    }
}
