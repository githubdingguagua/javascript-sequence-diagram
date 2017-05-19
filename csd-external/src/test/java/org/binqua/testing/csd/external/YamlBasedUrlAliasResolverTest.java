package org.binqua.testing.csd.external;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class YamlBasedUrlAliasResolverTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private YamlBasedUrlAliasResolver urlAliasResolverBasedOnYamlFileOnly = new YamlBasedUrlAliasResolver("myYamlFileWith2Services.yml");

    @Test
    public void serviceNamesAreDetectedProperly() throws Exception {
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("http://localhost:8050/first/blabla"), is("FIRST"));
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("http://localhost:8070/second"), is("NICE_AND_COOL"));
    }

    @Test
    public void givenOnlyServerAndPortMatchThenServiceNamesAreDetectedProperly() throws Exception {
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("http://localhost:8050/doSomething"), is("FIRST"));
    }

    @Test
    public void niceMessageIfThereAreNoServicesMapped() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(containsString("There are not services mapped in myYamlFileWithNoServices.yml"));

        new YamlBasedUrlAliasResolver("myYamlFileWithNoServices.yml");
    }

    @Test
    public void illegalArgumentExceptionIsThrowIfUrlIsNotMapped() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(containsString("Cannot find system mapped to url ThisUrlIsNotMapped in\n"));
        expectedException.expectMessage(containsString("FIRST"));
        expectedException.expectMessage(containsString("NICE_AND_COOL"));

        urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("ThisUrlIsNotMapped");
    }

    @Test
    public void serviceNameIsCorrect() throws Exception {
        assertThat(urlAliasResolverBasedOnYamlFileOnly.serviceName(), is("MY_MICRO"));
    }

    @Test
    public void internalBrokerIsMapped() throws Exception {
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("bliblibli(tcp://localhost:61616)blablabla"), is("INTERNAL_BROKER"));
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("tcp://localhost:61616"), is("INTERNAL_BROKER"));
    }

    @Test
    public void externalBrokerIsMapped() throws Exception {
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("bliblibli(tcp://localhost:61617)blablabla"), is("EXTERNAL_BROKER"));
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("tcp://localhost:61617"), is("EXTERNAL_BROKER"));
    }

    @Test
    public void publicBrokerIsMapped() throws Exception {
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("bliblibli(tcp://localhost:61618)blablabla"), is("PUBLIC_BROKER"));
        assertThat(urlAliasResolverBasedOnYamlFileOnly.aliasFromUrl("tcp://localhost:61618"), is("PUBLIC_BROKER"));
    }

}