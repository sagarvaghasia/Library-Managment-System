package com.project.Main.Models.GeneratePDF.Factories;

import com.project.Main.Models.GeneratePDF.GeneratePDF;
import com.project.Main.Models.GeneratePDF.IGeneratePDF;

public class GeneratePDFFactory implements IGeneratePDFFactory {

    private static IGeneratePDFFactory generatePDFFactory = null;

    private GeneratePDFFactory() {
    }

    public static IGeneratePDFFactory instance() {
        if (generatePDFFactory == null) {
            generatePDFFactory = new GeneratePDFFactory();
        }
        return generatePDFFactory;
    }

    @Override
    public IGeneratePDF createGeneratePDF() {
        return new GeneratePDF();
    }
}
