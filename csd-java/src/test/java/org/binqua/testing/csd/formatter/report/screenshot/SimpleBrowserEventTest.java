package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleBrowserEventTest {

    @Test
    public void afterNavigateToReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.afterNavigateTo("there").reason(), is("after navigating to there"));
    }

    @Test
    public void beforeClickingOnLinkReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.beforeClickingOnLink("there").reason(), is("before clicking link: there"));
    }

    @Test
    public void afterClickingOnLinkReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.afterClickingOnLink("there").reason(), is("after clicking link: there"));
    }

    @Test
    public void beforeSubmitViaReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.beforeSubmitVia("this").reason(), is("before submit via this"));
    }

    @Test
    public void afterSubmitViaReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.afterSubmitVia("this").reason(), is("after submit via this"));
    }

    @Test
    public void justBeforeTestFinishedReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.justBeforeTestFinished().reason(), is("just before test finished"));
    }

    @Test
    public void navigatingBackReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.navigatingBack().reason(), is("before navigating back"));
    }

    @Test
    public void navigatingForwardReasonIsCorrect() {
        assertThat(SimpleBrowserEvent.navigatingForward().reason(), is("before navigating forward"));
    }

}