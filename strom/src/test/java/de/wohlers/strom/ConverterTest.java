package de.wohlers.strom;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.assertTrue;

public class ConverterTest {

    @Test
    public void converter_html_pdf() throws Exception {

        File pdfDest = new File("output.pdf");
        pdfDest.deleteOnExit();

        if(pdfDest.exists()) {
            pdfDest.delete();
        }

        ConverterProperties properties = new ConverterProperties();

        HtmlConverter.convertToPdf("<html><body><h1>Hello World &mdash; <span style='color: red;'>red Text</span></h1></body></html>", new FileOutputStream(pdfDest), properties);

        assertTrue(pdfDest.exists());
    }

}
