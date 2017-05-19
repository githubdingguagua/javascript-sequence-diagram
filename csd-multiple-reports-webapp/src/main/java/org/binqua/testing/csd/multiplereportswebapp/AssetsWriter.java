package org.binqua.testing.csd.multiplereportswebapp;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.regex.Pattern;

public class AssetsWriter {

    private static final String ASSETS_FILE_CLASSPATH_LOCATION = "/org.binqua/testing/csd/multiplereportswebapp/html/";

    private static final String PACKAGE_TO_ANALYSED = "org.binqua.testing.csd.multiplereportswebapp.html";

    private File destinationDirectory;

    public AssetsWriter(File destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public void write() {
        destinationDirectory.mkdirs();
        moveFiles(destinationDirectory);
    }

    private void moveFiles(File destinationDirectory) {
        final Set<String> allFilesToBeMoved = findAllFilesToBeMoved();

        allFilesToBeMoved.stream().forEach(qualifiedClasspathName -> {
            final String amendedQualifiedClasspathName = new StringBuilder("/").append(qualifiedClasspathName).toString();
            final String newSuffix = drop(ASSETS_FILE_CLASSPATH_LOCATION, amendedQualifiedClasspathName);
            writeStreamAndClose(toInputStream(amendedQualifiedClasspathName), toOutputStream(newSuffix, destinationDirectory));
        });

    }

    private Set<String> findAllFilesToBeMoved() {
        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                                                            .filterInputsBy(new FilterBuilder().includePackage(PACKAGE_TO_ANALYSED))
                                                            .setUrls(ClasspathHelper.forPackage(PACKAGE_TO_ANALYSED))
                                                            .setScanners(new ResourcesScanner())
        );

        return reflections.getResources(Pattern.compile(".*"));
    }

    private String drop(String assetsFileClasspathLocation, String qualifiedClasspathName) {
        return qualifiedClasspathName.replace(assetsFileClasspathLocation,"");
    }

    private OutputStream toOutputStream(String fileName, File destinationDirectory) {
        try {
            final File destinationFile = new File(destinationDirectory, fileName);
            destinationFile.getParentFile().mkdirs();

            return new FileOutputStream(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream toInputStream(String classpathFileLocation) {
        final InputStream assetStream = getClass().getResourceAsStream(classpathFileLocation);
        if (assetStream == null) {
            throw new RuntimeException("Couldn't find " + classpathFileLocation + ". Is cucumber-html on your classpath? Make sure you have the right version.");
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
            throw new RuntimeException("Unable to write to report file item: ", e);
        }
    }

}
