package org.binqua.testing.csd.formatter;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.net.URL;

import cucumber.runtime.Utils;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Step;

import static org.hamcrest.MatcherAssert.assertThat;


@Ignore
public class HTMLFormatterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void formatterCreateRightFiles() {
//
//        final HTMLFormatter htmlFormatter = new HTMLFormatter(outputDir());
//
//        htmlFormatter.uri("some\\some.feature");
//
//        htmlFormatter.done();
//        htmlFormatter.close();

        assertThatAceFrameworkFileExist("ace.js");
        assertThatAceFrameworkFileExist("ext-beautify.js");
        assertThatAceFrameworkFileExist("ext-error_marker.js");
        assertThatAceFrameworkFileExist("ext-linking.js");
        assertThatAceFrameworkFileExist("ext-modelist.js");
        assertThatAceFrameworkFileExist("ext-searchbox.js");
        assertThatAceFrameworkFileExist("ext-settings_menu.js");
        assertThatAceFrameworkFileExist("ext-split.js");
        assertThatAceFrameworkFileExist("ext-static_highlight.js");
        assertThatAceFrameworkFileExist("ext-textarea.js");
        assertThatAceFrameworkFileExist("ext-themelist.js");
        assertThatAceFrameworkFileExist("ext-whitespace.js");
        assertThatAceFrameworkFileExist("mode-json.js");
        assertThatAceFrameworkFileExist("mode-xml.js");
        assertThatAceFrameworkFileExist("theme-eclipse.js");
        assertThatAceFrameworkFileExist("theme-github.js");
        assertThatAceFrameworkFileExist("worker-json.js");

        assertThatExist(css("jquery-ui.min"));
        assertThatExist(css("jquery-ui.structure.min"));
        assertThatExist(css("jquery-ui.theme.min"));
        assertThatExist(css("style"));

        assertThatExist(image("ui-bg_glass_65_ffffff_1x400"));
        assertThatExist(image("ui-bg_glass_100_f6f6f6_1x400"));
        assertThatExist(image("ui-bg_glass_100_fdf5ce_1x400"));
        assertThatExist(image("ui-bg_gloss-wave_35_f6a828_500x100"));
        assertThatExist(image("ui-bg_highlight-soft_100_eeeeee_1x100"));
        assertThatExist(image("ui-icons_222222_256x240"));
        assertThatExist(image("ui-icons_ef8c08_256x240"));

        assertThatExist(file("conversationRenderer.js"));
        assertThatExist(file("formatter.js"));
        assertThatExist(file("index.org.binqua.testing.csd.multiplereportwebapp.html"));
        assertThatExist(file("indexDocumentReadyFunction.js"));
        assertThatExist(file("jquery-1.11.2.min.js"));
        assertThatExist(file("jquery-ui.min.js"));
        assertThatExist(file("messagesSupport.js"));

        assertThatExist(file("testReport.js"));
    }

    @Test
    public void givenAResultAsBeenCreatedForAGivenStepThenNotifyContextNotifiesTheSameNameOfTheStep() throws Exception {
//
//        final ConversationContextNotifier conversationContextNotifier = mock(ConversationContextNotifier.class);
//        final ConversationContextsFactory conversationContextsFactory = mock(ConversationContextsFactory.class);
//
//        final HTMLFormatter htmlFormatter = new HTMLFormatter(outputDir(), conversationContextNotifier, conversationContextsFactory);
//
//        final ConversationContexts aConversationContexts = mock(ConversationContexts.class);
//        when(conversationContextsFactory.createConversationContexts()).thenReturn(aConversationContexts);
//
//        final String context1 = "step name 1";
//        final String context2 = "step name 2";
//        when(aConversationContexts.nextContext()).thenReturn(context1, context2);
//
//        htmlFormatter.uri("some\\some.feature");
//
//        htmlFormatter.scenario(mock(Scenario.class));
//
//        htmlFormatter.step(aStep(context1));
//        htmlFormatter.step(aStep(context2));
//
//        htmlFormatter.match(aMatch());
//        htmlFormatter.result(aResult());
//
//        htmlFormatter.match(aMatch());
//        htmlFormatter.result(aResult());
//
//        htmlFormatter.done();
//        htmlFormatter.close();
//
//        verify(aConversationContexts).step(context1);
//        verify(aConversationContexts).step(context2);
//
//        verify(aConversationContexts, times(2)).match();
//
//        verify(conversationContextNotifier).notifyContext(context1);
//        verify(conversationContextNotifier).notifyContext(context2);

    }

    private Match aMatch() {
        return new Match(null, "ClassSteps.thisMethod()");
    }

    private Result aResult() {
        return new Result("passed", 1234L, "error-message");
    }

    private Step aStep(String stepName) {
        return new Step(null, "when", stepName, 0, null, null);
    }

    private File image(String fileName) {
        return file("/css/images/" + fileName + ".png");
    }

    private File css(String fileName) {
        return file("/css/" + fileName + ".css");
    }

    private URL outputDir() {
        return Utils.toURL(tempFolder.getRoot().getAbsolutePath());
    }

    private void assertThatAceFrameworkFileExist(String fileSuffix) {
        assertThatExist(file("/ace-src/" + fileSuffix));
    }

    private void assertThatExist(File actualFile) {
        assertThat("file " + actualFile + " should exist", actualFile.exists());
    }

    private File file(String fileLocationSuffix) {
        return new File(tempFolder.getRoot().getAbsolutePath(), fileLocationSuffix);
    }

}