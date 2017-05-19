package org.binqua.testing.csd.formatter.svg;

import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;

import static net.sourceforge.plantuml.FileFormat.SVG;

class SimpleFromPlantumSyntaxToSvg implements FromPlantumSyntaxToSvg {

    @Override
    public String generateSvg(String plantUmlMarkup) {
        final SourceStringReader reader = new SourceStringReader(plantUmlMarkup);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            reader.generateImage(os, new FileFormatOption(SVG));
            os.close();
        } catch (Exception e) {
            System.out.println("Problem creating svg with content:\n" + plantUmlMarkup);
            e.printStackTrace();
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return "<svg>Exception try to create the svg!</svg>";
        }
        return new String(os.toByteArray());
    }
}
