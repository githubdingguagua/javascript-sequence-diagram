package org.binqua.testing.csd.formatter.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ScenarioIdGeneratorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final IdGenerator idGenerator = new ScenarioIdGenerator();

    @Test
    public void givenAKeyHasBeenRecorderThenWhenItIsRetrievedAnIdWithAPrefixIsGenerated() throws Exception {

        final String aFirstKey = "feature;scenario";
        idGenerator.record(aFirstKey);

        assertThat(idGenerator.idOf(aFirstKey), is("scenario-1"));

        final String aSecondKey = "anotherfeature;asecondscenario";
        idGenerator.record(aSecondKey);

        assertThat(idGenerator.idOf(aSecondKey), is("scenario-2"));

    }

    @Test
    public void backgroundIdIncreaseTheScenarioId() throws Exception {

        idGenerator.record("feature;scenario0");
        idGenerator.record("anotherfeature;scenario1");

        assertThat(idGenerator.backgroundId(), is("scenario-3"));

        idGenerator.record("anotherfeature;scenario4");

        assertThat(idGenerator.backgroundId(), is("scenario-5"));

        final String anotherKey = "anotherfeature;scenario6";
        idGenerator.record(anotherKey);

        assertThat(idGenerator.idOf(anotherKey), is("scenario-6"));

    }

    @Test
    public void givenAKeyHasBeenRecorderMoreThanOneTimeThenTheSameIdIsReturn() throws Exception {

        final String aFirstKey = "feature;scenario";
        idGenerator.record(aFirstKey);
        idGenerator.record(aFirstKey);
        idGenerator.record(aFirstKey);

        assertThat(idGenerator.record(aFirstKey), is("scenario-1"));
        assertThat(idGenerator.idOf(aFirstKey), is("scenario-1"));
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

}