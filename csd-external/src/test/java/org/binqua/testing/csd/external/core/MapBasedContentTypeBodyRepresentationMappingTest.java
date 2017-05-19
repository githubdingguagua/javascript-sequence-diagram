package org.binqua.testing.csd.external.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MapBasedContentTypeBodyRepresentationMappingTest {

    private final ModifiableContentTypeBodyRepresentationMapping mapBasedContentTypeBodyRepresentationMapping = new MapBasedContentTypeBodyRepresentationMapping();

    @Test
    public void givenAnExistingMappingThenValueAssociatedToReturnsRightValue() {
        mapBasedContentTypeBodyRepresentationMapping.addKeyValue("k1", Body.ContentType.JSON);
        mapBasedContentTypeBodyRepresentationMapping.addKeyValue("k2", Body.ContentType.XML);

        assertThat(mapBasedContentTypeBodyRepresentationMapping.valueAssociatedTo("k1"), is(Body.ContentType.JSON));
        assertThat(mapBasedContentTypeBodyRepresentationMapping.valueAssociatedTo("k2"), is(Body.ContentType.XML));
    }

    @Test
    public void givenANonExistingMappingThenValueAssociatedToReturnsText() {

        assertThat(mapBasedContentTypeBodyRepresentationMapping.valueAssociatedTo("k1"), is(Body.ContentType.TEXT));

    }
}