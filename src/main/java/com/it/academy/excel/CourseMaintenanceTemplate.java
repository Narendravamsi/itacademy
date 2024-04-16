package com.it.academy.excel;

import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CourseMaintenanceTemplate {

	private Logger logger = LoggerFactory.getLogger(CourseMaintenanceTemplate.class);

	ObjectMapper mapper = new ObjectMapper();

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private XSSFSheet sheet;
	private XSSFWorkbook workbook = new XSSFWorkbook();

	public CourseMaintenanceTemplate() {
	}

	private void writeHeader() throws IOException {
		logger.info("enterd in to write header method==>");
		String[] excelHeaders = { "academicYear", "batch", "course", "fee", "intake" };
		logger.info("excelHeaders");
		sheet = workbook.createSheet("courses");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(15);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderTop(BorderStyle.THICK);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBorderLeft(BorderStyle.THICK);
		style.setBorderRight(BorderStyle.THICK);
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (int i = 0; i < excelHeaders.length; i++) {
			createCell(row, i, excelHeaders[i], style);
		}

	}

	public void courseMaintenance(HttpServletResponse response) throws IOException {
		writeHeader();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
