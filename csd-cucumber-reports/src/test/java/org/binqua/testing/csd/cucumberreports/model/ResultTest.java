package org.binqua.testing.csd.cucumberreports.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ResultTest {

    @Test
    public void givenAFailedResultWithAnErrorMessageThenTheErrorMessageIsFormattedProperlyInHtml() throws Exception {
        final Result result = new Result(0L, Status.FAILED, "\tat something .... Caused by: something else");
        assertThat(result.errorMessage(),is("<br/>at something .... <br/>Caused by: something else"));
    }

    @Test
    public void givenAnUndefinedResultTheErrorMessageIsCorrect() throws Exception {
        final Result result = new Result(0L, Status.UNDEFINED, null);
        assertThat(result.errorMessage(),is("This step is not yet implemented"));
    }

    @Test
    public void givenAPassedResultTheErrorMessageIsEmpty() throws Exception {
        final Result result = new Result(0L, Status.PASSED, null);
        assertThat(result.errorMessage(),is(""));
    }

}