package org.binqua.testing.csd.common;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestReader {

    public Optional<String> buildNumberOfManifestContainingAttributeValue(String valueToFind) {
        Enumeration<URL> resources = null;
        try {
            resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
        } catch (IOException e) {
            return Optional.empty();
        }
        while (resources.hasMoreElements()) {
            try {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                final Attributes mainAttributes = manifest.getMainAttributes();
                if (implementationTitleIs(valueToFind, mainAttributes)) {
                    return Optional.of(mainAttributes.getValue("Implementation-Version"));
                }
            } catch (IOException E) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private boolean implementationTitleIs(String s, Attributes mainAttributes) {
        return s.equals(mainAttributes.getValue("Implementation-Title"));
    }

}
