package com.example.vaadincourse1.todo.service;

import com.itextpdf.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTMLToPDFService {


    public static ByteArrayOutputStream createPDF(String html) {
        var render = new ITextRenderer();

        render.setDocumentFromString(html);
        render.layout();

      var fs = new ByteArrayOutputStream();
        try {
            render.createPDF(fs);
            return fs;

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
