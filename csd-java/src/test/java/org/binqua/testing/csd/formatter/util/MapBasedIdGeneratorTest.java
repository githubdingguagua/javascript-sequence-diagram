package org.binqua.testing.csd.formatter.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MapBasedIdGeneratorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final IdGenerator idGenerator = new MapBasedIdGenerator("prefix");

    @Test
    public void givenAKeyHasBeenRecorderThenWhenItIsRetrievedAnIdWithAPrefixIsGenerated() throws Exception {

        final String aFirstKey = "a-first-key";
        idGenerator.record(aFirstKey);

        assertThat(idGenerator.idOf(aFirstKey), is("prefix-1"));

        final String aSecondKey = "a-second-key";
        idGenerator.record(aSecondKey);

        assertThat(idGenerator.idOf(aSecondKey), is("prefix-2"));

    }

    @Test
    public void keyShouldNotContainWhitespaces() throws Exception {
        final String aKey = "a b";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(format("Key cannot contain white spaces: %s is wrong", aKey));

        idGenerator.record(aKey);
    }

    @Test
    public void keyShouldNotContainUppercase() throws Exception {
        final String aKey = "A";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(format("Key cannot contain uppercase: %s is wrong", aKey));

        idGenerator.record(aKey);
    }

    @Test
    public void givenKeyContainsUppercaseThenIdThrowsIllegalArgumentException() throws Exception {
        final String aKey = "A";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(format("Key cannot contain uppercase: %s is wrong", aKey));

        idGenerator.idOf(aKey);
    }

    @Test
    public void givenKeyContainsWhitespacesThenIdThrowsIllegalArgumentException() throws Exception {
        final String aKey = "a b";

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(format("Key cannot contain white spaces: %s is wrong", aKey));

        idGenerator.idOf(aKey);
    }

    @Test
    public void backgroundIdShouldNotBeInvoked() throws Exception {

        thrown.expect(UnsupportedOperationException.class);

        idGenerator.backgroundId();

    }

}