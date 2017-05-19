package org.binqua.testing.csd.httpclient;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpClientParametersTest {

    @Test
    public void aNullBodyHasAnEmptyValue() throws Exception {
        assertThat(HttpClientParameters.HttpBody.aBodyWith(null).value(), is(""));
        assertThat(HttpClientParameters.HttpBody.aBodyWith("null").value(), is(""));
    }

}