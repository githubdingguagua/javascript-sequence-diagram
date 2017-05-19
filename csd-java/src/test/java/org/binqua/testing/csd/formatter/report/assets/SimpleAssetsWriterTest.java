package org.binqua.testing.csd.formatter.report.assets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleAssetsWriterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void allFilesAreCopied() throws Exception {

        final ReportFileNames reportFileNames = mock(ReportFileNames.class);

        when(reportFileNames.reportDirectory()).thenReturn(tempFolder.getRoot());

        new SimpleAssetsWriter(reportFileNames).write();

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

        assertThatExist(css("cucumber-table"));
        assertThatExist(css("jquery.mThumbnailScroller"));
        assertThatExist(css("jquery-ui.min"));
        assertThatExist(css("jquery-ui.structure.min"));
        assertThatExist(css("jquery-ui.theme.min"));
        assertThatExist(css("jquery.spin"));
        assertThatExist(css("style"));
        assertThatExist(css("w2ui-1.4.2.min"));

        assertThatExist(image("ui-bg_glass_65_ffffff_1x400"));
        assertThatExist(image("ui-bg_glass_100_f6f6f6_1x400"));
        assertThatExist(image("ui-bg_glass_100_fdf5ce_1x400"));
        assertThatExist(image("ui-bg_gloss-wave_35_f6a828_500x100"));
        assertThatExist(image("ui-bg_highlight-soft_100_eeeeee_1x100"));
        assertThatExist(image("ui-icons_222222_256x240"));
        assertThatExist(image("ui-icons_ef8c08_256x240"));
        assertThatExist(image("thin_left_arrow_333"));
        assertThatExist(image("thin_right_arrow_333"));

        assertThatExist(javascript("artifactLoader"));
        assertThatExist(javascript("bodyIndexer"));
        assertThatExist(javascript("bodySearchResultFormatter"));
        assertThatExist(javascript("bodySearchResultHighlighter"));
        assertThatExist(javascript("conversationAdapter"));
        assertThatExist(javascript("conversationMessagesRenderer"));
        assertThatExist(javascript("conversationRenderer"));
        assertThatExist(javascript("csdUtil"));
        assertThatExist(javascript("featureView"));
        assertThatExist(javascript("featureHtmlReportBuilder"));
        assertThatExist(javascript("jquery.layout-latest"));
        assertThatExist(javascript("jquery.mThumbnailScroller"));
        assertThatExist(javascript("jquery.spin"));
        assertThatExist(javascript("jquery-2.1.4"));
        assertThatExist(javascript("jquery-ui.min"));
        assertThatExist(javascript("jquery-ui-1.11.4.min"));
        assertThatExist(javascript("htmlIdManager"));
        assertThatExist(javascript("loader"));
        assertThatExist(javascript("lunr"));
        assertThatExist(javascript("menuRenderer"));
        assertThatExist(javascript("messagesSupport"));
        assertThatExist(javascript("namingUtil"));
        assertThatExist(javascript("screenshotsIndexer"));
        assertThatExist(javascript("searcher"));
        assertThatExist(javascript("searchResultFormatter"));
        assertThatExist(javascript("searchResultHighlighter"));
        assertThatExist(javascript("searchResultLabelUtil"));
        assertThatExist(javascript("searchResultSorter"));
        assertThatExist(javascript("screenshotNavigationRenderer"));
        assertThatExist(javascript("screenshotSearchResultFormatter"));
        assertThatExist(javascript("screenshotSearchResultHighlighter"));
        assertThatExist(javascript("screenshotThumbnailNavigator"));
        assertThatExist(javascript("stepConversationAdapter"));
        assertThatExist(javascript("smartBodyIndexer"));
        assertThatExist(javascript("urlFormatter"));
        assertThatExist(javascript("w2ui-1.4.2.min"));

        assertThatExist(file("index.html"));

        assertThatExist(file("blue/css/skin/images/btn-big-left.jpg"));
        assertThatExist(file("blue/images/bg_image.png"));

    }

    private File image(String fileName) {
        return file("/css/images/" + fileName + ".png");
    }

    private File javascript(String fileName) {
        return file("/" + fileName + ".js");
    }
    private File css(String fileName) {
        return file("/css/" + fileName + ".css");
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