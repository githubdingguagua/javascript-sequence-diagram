package org.binqua.testing.csd.formatter.external;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;

public class UrlConverterTest {

    @Test
    public void aValidUrlCanBeParsed() throws Exception {

        final UrlConverter urlConverter = new UrlConverter(urlWithFile("/a/dir/cucumber-csd-report?microservice1=8081&microservice2=8082/"));

        assertThat(urlConverter.file(), is(new File("/a/dir/cucumber-csd-report")));

        final Map<String, Integer> actualMap = urlConverter.clusterNamePortMap();

        assertThat(actualMap, hasEntry("microservice1", 8081));
        assertThat(actualMap, hasEntry("microservice2", 8082));
    }

    @Test
    public void givenAnUrlThatEndsWithSlashThenItCanBeParsed() throws Exception {

        final UrlConverter urlConverter = new UrlConverter(urlWithFile("/a/dir/cucumber-csd-report?microservice1=8081/"));

        assertThat(urlConverter.file(), is(new File("/a/dir/cucumber-csd-report")));
        assertThat(urlConverter.clusterNamePortMap(), hasEntry("microservice1", 8081));
    }

    @Test
    public void givenAnUrlThatDoesNotEndWithSlashThenItCanBeParsed() throws Exception {

        final UrlConverter urlConverter = new UrlConverter(urlWithFile("/a/dir/cucumber-csd-report?microservice1=8081"));

        assertThat(urlConverter.file(), is(new File("/a/dir/cucumber-csd-report")));
        assertThat(urlConverter.clusterNamePortMap(), hasEntry("microservice1", 8081));
    }

    private URL urlWithFile(String file) throws MalformedURLException {
        return new URL("file", "localhost", 80, file);
    }
}