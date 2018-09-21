package org.binqua.testing.csd.external;

import org.junit.Ignore;
import org.junit.Test;

public class UrlBasedMicroserviceAliasResolverTest {

    private final MicroserviceAliasResolver microserviceAliasResolver = new UrlBasedMicroserviceAliasResolver();

    @Test
    @Ignore
    public void name() {
        microserviceAliasResolver.aliasOf("");
    }

}
