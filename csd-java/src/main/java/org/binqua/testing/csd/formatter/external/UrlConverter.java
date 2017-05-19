package org.binqua.testing.csd.formatter.external;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlConverter {

    private final String actualFile;
    private final String originalUrlFile;

    UrlConverter(URL url) {
        this.originalUrlFile = extractFile(url);
        actualFile = calculateActualFile(originalUrlFile);
    }

    File file() {
        return new File(actualFile);
    }

    Map<String, Integer> clusterNamePortMap() {
        return calculateClusterNamePortMap(originalUrlFile.replace(actualFile + "?", "")) ;
    }

    private String extractFile(URL url) {
        final String urlFile = url.getFile();
        if (urlFile.endsWith("/")) {
            return urlFile.substring(0, urlFile.length() - 1);
        }
        return urlFile;
    }

    private String calculateActualFile(String file) {
        final int endIndexOfQuestionMark = file.indexOf("?");
        if (endIndexOfQuestionMark == -1) {
            return file;
        }
        return file.substring(0, endIndexOfQuestionMark);
    }

    private Map<String, Integer> calculateClusterNamePortMap(String urlContainingTheClusterNamePortMap) {
        final Map<String, Integer> clusterNamePortMap = new LinkedHashMap<>();
        final String[] pairs = urlContainingTheClusterNamePortMap.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            clusterNamePortMap.put(pair.substring(0, idx), Integer.valueOf(pair.substring(idx + 1)));
        }
        return clusterNamePortMap;
    }
}
