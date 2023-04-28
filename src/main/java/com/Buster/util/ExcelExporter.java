package com.Buster.util;

import com.Buster.entity.User;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
public class ExcelExporter {
     public static InputStreamResource exportUsersToExcel(List<User> users)
             throws IOException {
          String[] columns = {"ID", "First Name", "Last Name", "Email",
                  "Phone Number", "Created At", "Updated At"};
          try (Workbook workbook = new XSSFWorkbook();
               ByteArrayOutputStream out = new ByteArrayOutputStream()) {
               CreationHelper createHelper = workbook.getCreationHelper();
               Sheet sheet = workbook.createSheet("Users");

// Create header row
               Font headerFont = workbook.createFont();
               headerFont.setBold(true);
               CellStyle headerCellStyle = workbook.createCellStyle();
               headerCellStyle.setFont(headerFont);
               Row headerRow = sheet.createRow(0);
               for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerCellStyle);
               }
               // Create data rows
               int rowNum = 1;
               for (User user : users) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0).setCellValue(user.getId());
                    row.createCell(1).setCellValue(user.getName());
                    row.createCell(2).setCellValue(user.getEmail());
                    row.createCell(3).setCellValue(user.getBody());
               }
               workbook.write(out);
               ByteArrayInputStream in = new
                       ByteArrayInputStream(out.toByteArray());
               return new InputStreamResource(in);
          }
     }
}
