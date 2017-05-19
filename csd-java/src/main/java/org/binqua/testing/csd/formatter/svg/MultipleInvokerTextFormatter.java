package org.binqua.testing.csd.formatter.svg;

import java.util.LinkedHashMap;
import java.util.Map;

class MultipleInvokerTextFormatter {

    private Map<String, Integer> maxNumberOfTextOccurrencesMap;
    private Map<String, Integer> numberOfInvocationsMap = new LinkedHashMap<>();

    MultipleInvokerTextFormatter(Map<String, Integer> maxNumberOfTextOccurrencesMap) {
        this.maxNumberOfTextOccurrencesMap = maxNumberOfTextOccurrencesMap;
    }

    public String format(String text) {
        numberOfInvocationsMap = updateNumberOfInvocationsMap(text);

        if (maxNumberOfTextOccurrencesMap.get(text) == 1) {
            return text;
        }
        return String.format("%s of %s %s", occurrencesOfTextUntilNow(text), maxNumberOfTextOccurrencesMap.get(text), text);
    }

    private Map<String, Integer> updateNumberOfInvocationsMap(String text) {
        if (occurrencesOfTextUntilNow(text) != null) {
            numberOfInvocationsMap.put(text, occurrencesOfTextUntilNow(text) + 1);
        } else {
            numberOfInvocationsMap.put(text, 1);
        }
        return numberOfInvocationsMap;
    }

    private Integer occurrencesOfTextUntilNow(String text) {
        return numberOfInvocationsMap.get(text);
    }
}
