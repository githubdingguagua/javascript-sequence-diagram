package org.binqua.testing.csd.formatter.report.assets;

import com.google.common.collect.Lists;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import org.binqua.testing.csd.formatter.report.ReportFileNames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import cucumber.runtime.CucumberException;

import static java.util.Arrays.asList;

public class SimpleAssetsWriter implements AssetsWriter {

    private static final String ASSETS_FILE_CLASSPATH_LOCATION = "/org.binqua/testing/csd/html/";
    private static final String INDEX_HTML = "index.html";

    private ReportFileNames reportFileNames;

    public SimpleAssetsWriter(ReportFileNames reportFileNames) {
        this.reportFileNames = reportFileNames;
    }

    @Override
    public void write() {
        copyIndexHtml();
        copyJavascriptFiles();
        copyAceFrameworkFiles();
        copyCssFiles();
        copyImagesFiles();
        copyBlueFiles();
    }

    private void copyBlueFiles() {
        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                                                            .filterInputsBy(new FilterBuilder().includePackage("org.binqua.testing.csd.html.blue"))
                                                            .setUrls(ClasspathHelper.forPackage("org.binqua.testing.csd.html.blue"))
                                                            .setScanners(new ResourcesScanner())
        );

        final Set<String> resources = reflections.getResources(Pattern.compile(".*"));

        resources.stream().forEach(qualifiedClasspathName -> {
            final String amendedQualifiedClasspathName = new StringBuilder("/").append(qualifiedClasspathName).toString();
            final String newSuffix = drop(ASSETS_FILE_CLASSPATH_LOCATION, amendedQualifiedClasspathName);
            writeStreamAndClose(toInputStream(amendedQualifiedClasspathName), toOutputStream(newSuffix));
        });

    }

    private String drop(String assetsFileClasspathLocation, String qualifiedClasspathName) {
        return qualifiedClasspathName.replace(assetsFileClasspathLocation,"");
    }

    private void copyIndexHtml() {
        final File indexSource = new File(ASSETS_FILE_CLASSPATH_LOCATION, INDEX_HTML);
        writeStreamAndClose(toInputStream(indexSource.getPath()), toOutputStream(INDEX_HTML));
    }

    private void copyJavascriptFiles() {
        for (File textAsset : new JavascriptAssets().assets()) {
            writeStreamAndClose(toInputStream(textAsset.getPath()), toOutputStream(textAsset.getName()));
        }
    }

    private void copyCssFiles() {
        moveFiles(new CssAssets().assets());
    }

    private void copyImagesFiles() {
        moveFiles("css" + File.separator, new ImagesAssets().assets());
    }

    private void copyAceFrameworkFiles() {
        moveFiles(new AceFrameworkAssets().assets());
    }

    private void moveFiles(List<File> imagesAssets) {
        moveFiles("", imagesAssets);
    }

    private void moveFiles(String parentDirName, List<File> imagesAssets) {
        imagesAssets.forEach(f -> writeStreamAndClose(toInputStream(f.getAbsolutePath()), toOutputStream(parentDirName + f.getParentFile().getName() + File.separator + f.getName())));
    }

    private OutputStream toOutputStream(String fileName) {
        try {
            final File destinationFile = new File(reportFileNames.reportDirectory(), fileName);
            destinationFile.getParentFile().mkdirs();

            return new FileOutputStream(destinationFile);
        } catch (IOException e) {
            throw new CucumberException(e);
        }
    }

    private InputStream toInputStream(String classpathFileLocation) {
        final InputStream assetStream = getClass().getResourceAsStream(classpathFileLocation);
        if (assetStream == null) {
            throw new CucumberException("Couldn't find " + classpathFileLocation + ". Is cucumber-html on your classpath? Make sure you have the right version.");
        }
        return assetStream;
    }

    private void writeStreamAndClose(InputStream in, OutputStream out) {
        byte[] buffer = new byte[16 * 1024];
        try {
            int len = in.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = in.read(buffer);
            }
            out.close();
        } catch (IOException e) {
            throw new CucumberException("Unable to write to report file item: ", e);
        }
    }

    private class CssAssets {

        private final List<String> ASSETS = asList(
            "cucumber-table",
            "jquery.mThumbnailScroller",
            "jquery-ui.min",
            "jquery-ui.structure.min",
            "jquery-ui.theme.min",
            "jquery.spin",
            "style",
            "w2ui-1.4.2.min"
        );

        public List<File> assets() {
            return prefixWith(ASSETS_FILE_CLASSPATH_LOCATION + "css/", ASSETS, "css");
        }

    }

    private class JavascriptAssets {

        private final List<String> ASSETS = asList(
                "artifactLoader",
                "bodyIndexer",
                "bodySearchResultFormatter",
                "bodySearchResultHighlighter",
                "conversationMessagesRenderer",
                "conversationRenderer",
                "conversationAdapter",
                "csdUtil",
                "featureView",
                "featureHtmlReportBuilder",
                "jquery.layout-latest",
                "jquery.spin",
                "jquery-2.1.4",
                "jquery-ui.min",
                "jquery-ui-1.11.4.min",
                "jquery.mThumbnailScroller",
                "htmlIdManager",
                "lunr",
                "loader",
                "menuRenderer",
                "messagesSupport",
                "namingUtil",
                "screenshotsIndexer",
                "screenshotNavigationRenderer",
                "screenshotSearchResultFormatter",
                "screenshotSearchResultHighlighter",
                "searcher",
                "searchResultFormatter",
                "searchResultHighlighter",
                "searchResultLabelUtil",
                "screenshotThumbnailNavigator",
                "searchResultSorter",
                "smartBodyIndexer",
                "stepConversationAdapter",
                "urlFormatter",
                "w2ui-1.4.2.min"
                );

        public List<File> assets() {
            return prefixWith(ASSETS_FILE_CLASSPATH_LOCATION, ASSETS, "js");
        }

    }

    private class AceFrameworkAssets {

        private final List<String> ASSETS = asList(
            "ace",
            "ext-beautify",
            "ext-error_marker",
            "ext-linking",
            "ext-modelist",
            "ext-searchbox",
            "ext-settings_menu",
            "ext-split",
            "ext-static_highlight",
            "ext-textarea",
            "ext-themelist",
            "ext-whitespace",
            "mode-json",
            "mode-xml",
            "theme-eclipse",
            "theme-github",
            "worker-json"
        );

        public List<File> assets() {
            return prefixWith(ASSETS_FILE_CLASSPATH_LOCATION + "ace-src/", ASSETS, "js");
        }

    }

    private List<File> prefixWith(final String prefix, List<String> asset, String extension) {
        return Lists.transform(asset, suffix -> new File(prefix + suffix + "." + extension));
    }

    private class ImagesAssets {

        private final List<String> ASSETS = asList(
            "ui-icons_ef8c08_256x240",
            "ui-bg_glass_65_ffffff_1x400",
            "ui-bg_glass_100_f6f6f6_1x400",
            "ui-bg_glass_100_fdf5ce_1x400",
            "ui-bg_gloss-wave_35_f6a828_500x100",
            "ui-bg_highlight-soft_100_eeeeee_1x100",
            "ui-icons_222222_256x240",
            "ui-icons_ef8c08_256x240",
            "thin_left_arrow_333",
            "thin_right_arrow_333"
        );

        public List<File> assets() {
            return prefixWith(ASSETS_FILE_CLASSPATH_LOCATION + "css/images/", ASSETS, "png");
        }

    }
}
