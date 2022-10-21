package com.example.vaadincourse1.todo.service;

import com.example.vaadincourse1.todo.model.Todo;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ExcelGeneratorService {

    public Workbook createExcelFile(Set<Todo> todos) {
        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("Todo Items");
        var row = sheet.createRow(0);
        var cell = row.createCell(0);
        cell.setCellValue("Todo's List ....#" + todos.size());

        var style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.index);

        var font = wb.createFont();
        font.setColor(IndexedColors.RED.index);
        font.setBold(true);

        style.setFont(font);
        cell.setCellStyle(style);

       var style2 = wb.createCellStyle();
       var font2 = wb.createFont();
       font2.setBold(true);
       style2.setFont(font2);





        int rowNumber = 2;

        for (Todo todo : todos) {
            row = sheet.createRow(rowNumber);
            cell = row.createCell(0);
            cell.setCellValue(todo.getAuthor());
            cell.setCellStyle(style2);

            cell = row.createCell(1);
            cell.setCellValue(todo.getTitle());
            cell.setCellStyle(style2);

            cell = row.createCell(2);
            cell.setCellValue(todo.getBody());


            cell = row.createCell(3);
            cell.setCellValue(todo.getCreatedAt());

            rowNumber++;
        }



        return wb;

    }

}
