package com.den.service;

import com.den.entity._student;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.UnKnowError;
import com.den.model.ObjectResultExcel;
import com.den.utils._enum;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.InputStreamResource;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelUploadService {

     final int ID = 0, NAME = 1, PHONE = 2, ADDRESS = 3, CLAZZ = 4, STATUS = 5, BIRTHDAY = 6, EMAIL = 7, IMAGE = 8;
    private final String[] HEADERS = {"Stt", "Status", "Error"};


    @Autowired
    private SimpleDateFormat simpleDateFormat;
    @Autowired
    private StudentServiceV2 studentServiceV2;

    public boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<ObjectResultExcel> getListResultFromExcel(InputStream inputStream) {
        List<ObjectResultExcel> result = new ArrayList<>();

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new UnKnowError("fail to read excel file");
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        int length = sheet.getPhysicalNumberOfRows();

        int rowIndex = 0;
        for (Row row : sheet) {
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }

            int index = row.getRowNum();

//            check id
            Long id = getCellValue(row.getCell(ID), Long.class);
            String email = getCellValue(row.getCell(EMAIL), String.class);
            String name = getCellValue(row.getCell(NAME), String.class);
            int phone = getCellValue(row.getCell(PHONE), Integer.class);
            String address = getCellValue(row.getCell(ADDRESS), String.class);
            Long clazzId = getCellValue(row.getCell(CLAZZ), Long.class);
            String status = getCellValue(row.getCell(STATUS), String.class);
            String birthdaySTR = getCellValue(row.getCell(BIRTHDAY), String.class);
            String image = getCellValue(row.getCell(IMAGE), String.class);
            Date birthday = null;

            if (address == null || address.isEmpty()) {
                result.add(new ObjectResultExcel(index, ObjectResultExcel.FAIL, "address must require"));
                continue;
            } else if (status == null || status.isEmpty()) {
                result.add(new ObjectResultExcel(index, ObjectResultExcel.FAIL, "status must require"));
                continue;
            }
            if(!(phone+"").matches("^[1-9]\\d{8,11}$")){
                result.add(new ObjectResultExcel(index, ObjectResultExcel.FAIL, "phone is must number and length to 9->12"));
                continue;
            }

            try {
                birthday = simpleDateFormat.parse(birthdaySTR);
            } catch (Exception e) {
                result.add(new ObjectResultExcel(index, ObjectResultExcel.FAIL, "birthday format error (dd/MM/yyyy)"));
                continue;
            }

            Optional<_student> optionalStudent =studentServiceV2.findByIdOptional(id);
            System.out.println("is has student id :"+id+":::::::"+optionalStudent.isPresent());

//            insert
            if (id == null || optionalStudent.isEmpty()) {
                    try {
                        _student std = new _student(name, clazzId, birthday, address, email, "0" + phone, _enum.getEnumFromString(status).value, image);
                        studentServiceV2.add(std);
                        result.add(new ObjectResultExcel(index, ObjectResultExcel.INSERTED, ""));
                    } catch (BabRequestError e) {
                        e.printStackTrace();
                        result.add(new ObjectResultExcel(index, ObjectResultExcel.FAIL, "insert fail: "+ e.getMessage()));
                    }
                    continue;
//                update
            } else {
                try {
                    _student std = new _student(id, name, clazzId, birthday, address, email, "0" + phone, _enum.getEnumFromString(status).value, image);
                    studentServiceV2.update(std);
                    result.add(new ObjectResultExcel(index, ObjectResultExcel.UPDATED, ""));
                } catch (BabRequestError e) {
                    result.add(new ObjectResultExcel(index, ObjectResultExcel.FAIL, "update fail: "+e.getMessage()));
                }
                continue;
            }


        }
        return result;
    }

    private <T> T getCellValue(Cell cell, Class<T> clazz) {

        if (cell != null) {
            if (clazz == Long.class && cell.getCellType() == CellType.NUMERIC) {
                return clazz.cast(Double.valueOf(cell.getNumericCellValue()).longValue());
            } else if (clazz == String.class && cell.getCellType() == CellType.STRING) {
                return clazz.cast(cell.getStringCellValue());
            }
            if (clazz == Integer.class && cell.getCellType() == CellType.NUMERIC) {
                return clazz.cast(Double.valueOf(cell.getNumericCellValue()).intValue());
            }
        }

        return null;
    }

    public InputStreamResource writeExcel(List<ObjectResultExcel> list) {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("result");

        // Header
        Row headerRow = sheet.createRow(0);

        for (int col = 0; col < HEADERS.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(HEADERS[col]);
        }

        int rowIndex = 1;
        for (ObjectResultExcel result : list) {
            Row row = sheet.createRow(rowIndex++);
            System.out.println(result);
            row.createCell(0).setCellValue(result.getStt());
            row.createCell(1).setCellValue(result.getStatus());
            row.createCell(2).setCellValue(result.getError());
        }

        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnKnowError("fail to import data to Excel file: " + e.getMessage());
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        return new InputStreamResource(inputStream);
    }

    public InputStreamResource exportStudent(List<_student> list){
        final String[] header={"stt","id","code","name","class_id","birthday","address","phone","email","status","image"};
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("result");
        Row headerRow = sheet.createRow(0);

        for (int col = 0; col < header.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(header[col]);
        }

        int rowIndex = 1;
        for (_student result : list) {
            Row row = sheet.createRow(rowIndex++);
            System.out.println(result);
            row.createCell(0).setCellValue(rowIndex-1);
            row.createCell(1).setCellValue(result.getId());
            row.createCell(2).setCellValue(result.getCode());
            row.createCell(3).setCellValue(result.getName());
            row.createCell(4).setCellValue(result.getClazzId());
            row.createCell(5).setCellValue(simpleDateFormat.format(result.getBirthday()));
            row.createCell(6).setCellValue(result.getAddress());
            row.createCell(7).setCellValue(result.getPhone());
            row.createCell(8).setCellValue(result.getEmail());
            row.createCell(9).setCellValue(_enum.StatusStudentEnum.fromValue(result.getStatus()).name());
            row.createCell(10).setCellValue(result.getImage());
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnKnowError("fail to import data to Excel file: " + e.getMessage());
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        return new InputStreamResource(inputStream);
    }

}
