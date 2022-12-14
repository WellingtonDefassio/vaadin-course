package com.example.vaadincourse1.todo.service;

import com.example.vaadincourse1.todo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.Set;

@Service
public class PDFGeneratorService {
    @Autowired
    SpringTemplateEngine springTemplateEngine;

    public ByteArrayOutputStream createPdf(Set<Todo> todos) {

        return HTMLToPDFService.createPDF(renderHtmlForPDF(todos));
    }

    private String renderHtmlForPDF(Set<Todo> todos){

        Context context = new Context();

        context.setVariable("items", todos);
        context.setVariable("title", "todo's list: " + todos.size());
        context.setVariable("image1", ImageUtils.convertImageToBase64("pdf/images/image1.png"));

        context.setVariable("barcode1",BarCodeService.getBarCodeAsBase64("Todo-List-123456789"));


    return springTemplateEngine.process("pdf/todos.html",context);

    }


}
