package org.binqua.testing.csd.formatter.svg;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MultipleInvokerTextFormatterTest {

    @Test
    public void givenMaxNumberOfTextOccurrencesBiggerThan1ThenNewTextIsCorrect() throws Exception {

        final Map<String, Integer> maxNumberOfTextOccurrencesMap = new HashMap<>();
        maxNumberOfTextOccurrencesMap.put("a", 3);

        final MultipleInvokerTextFormatter multipleInvokerTextFormatter = new MultipleInvokerTextFormatter(maxNumberOfTextOccurrencesMap);

        assertThat(multipleInvokerTextFormatter.format("a"), is("1 of 3 a"));
        assertThat(multipleInvokerTextFormatter.format("a"), is("2 of 3 a"));
        assertThat(multipleInvokerTextFormatter.format("a"), is("3 of 3 a"));

    }

    @Test
    public void givenMaxNumberOfTextOccurrencesEqualsTo1ThenNewTextIsCorrect() throws Exception {

        final Map<String, Integer> maxNumberOfTextOccurrencesMap = new HashMap<>();
        maxNumberOfTextOccurrencesMap.put("a", 1);

        final MultipleInvokerTextFormatter multipleInvokerTextFormatter = new MultipleInvokerTextFormatter(maxNumberOfTextOccurrencesMap);

        assertThat(multipleInvokerTextFormatter.format("a"), is("a"));

    }
}