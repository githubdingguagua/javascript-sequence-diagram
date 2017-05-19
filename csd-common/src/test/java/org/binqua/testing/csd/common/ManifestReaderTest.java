package org.binqua.testing.csd.common;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ManifestReaderTest {

    private final ManifestReader manifestReader = new ManifestReader();

    @org.junit.Test
    public void canReadImplementationVersionOfAJarContainingAManifestWithSpecifiedImplementationTitle() throws Exception {
        assertThat(manifestReader.buildNumberOfManifestContainingAttributeValue("the-project-title"), is(Optional.of("123")));
    }

    @org.junit.Test
    public void givenNoImplementationTitleThenAnEmptyOptionalIsReturn() throws Exception {
        assertThat(manifestReader.buildNumberOfManifestContainingAttributeValue("the-project-title-that-does-not-exist"), is(Optional.empty()));
    }

}