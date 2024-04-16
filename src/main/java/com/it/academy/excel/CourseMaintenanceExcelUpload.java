package com.it.academy.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.academy.model.CourseMaintenance;
import com.it.academy.model.CourseMaintenancePrimaryKey;
import com.it.academy.repository.CourseMaintenanceRepository;

@Component
public class CourseMaintenanceExcelUpload {
	
	@Autowired
	private CourseMaintenanceRepository courseMaintenanceRepository;
	
	
	private Logger logger=LoggerFactory.getLogger(CourseMaintenanceExcelUpload.class);
	
	ObjectMapper mapper= new ObjectMapper();
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	
	

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}
	
	
	public List<CourseMaintenance> saveCourseMaintenanceExcel(MultipartFile file,Map<String, String>allParams){
		Workbook workBook = null;
		
		List<CourseMaintenance> courseMaintenanceList= new ArrayList<>();
		List<CourseMaintenance> unSuccessFullList= new ArrayList<>();

		CourseMaintenancePrimaryKey courseMaintenancePrimaryKey=null;
		CourseMaintenance courseMaintenance= null;
		
		DataFormatter dataformatter=new DataFormatter();
		try {
			
			String academicYear=allParams.get("academicYear");
			String name=file.getOriginalFilename();
			String extension="";
			
			if(name.contains(".")) {
				extension=name.substring(name.lastIndexOf("."));
			}
			
			logger.info("extension=============>"+extension);
			
			if(extension.equalsIgnoreCase(".xls")) {
				workBook=new HSSFWorkbook(file.getInputStream());
				logger.info("Hssf workbookcreated........");
			}
			
			else if(extension.equalsIgnoreCase(".xlsx")) {
				workBook=new XSSFWorkbook(file.getInputStream());
				logger.info("Xssf workbook created....");
	
			}
			
			int startRowIndex=1;
			Sheet sheet = workBook.getSheetAt(0);

			for(int rowIndex=startRowIndex;rowIndex<=sheet.getLastRowNum();rowIndex++) {
				Row row = sheet.getRow(rowIndex);


				if (row != null) {
					Cell cell = row.getCell(1);

					if (cell != null && cell.getCellType() != CellType.BLANK) {
						
						courseMaintenancePrimaryKey= new CourseMaintenancePrimaryKey();
						courseMaintenance= new CourseMaintenance();
						
						courseMaintenancePrimaryKey.setAcademicYear(nullchecking(dataformatter.formatCellValue(row.getCell(0))));
						courseMaintenancePrimaryKey.setBatch(nullchecking(dataformatter.formatCellValue(row.getCell(1))));
						courseMaintenancePrimaryKey.setCourse(nullchecking(dataformatter.formatCellValue(row.getCell(2))));
						
						courseMaintenance.setCourseMaintenancePrimaryKey(courseMaintenancePrimaryKey);
						
						courseMaintenance.setFee(nullchecking(dataformatter.formatCellValue(row.getCell(3))));
						courseMaintenance.setIntake(nullchecking(dataformatter.formatCellValue(row.getCell(4))));
						
						if(!courseMaintenancePrimaryKey.getAcademicYear().equals(academicYear)) {
							
							unSuccessFullList.add(courseMaintenance);
							logger.info("unsuccesFullList==>"+mapper.writeValueAsString(unSuccessFullList));
						}else {
							courseMaintenanceList.add(courseMaintenance);
							logger.info("SuccesFullList==>"+mapper.writeValueAsString(courseMaintenanceList));
						}
						
				
			}
			
				}
			}
			courseMaintenanceRepository.saveAll(courseMaintenanceList);
			return unSuccessFullList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}finally {
			try {
				workBook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	}
	
	
	public String nullchecking(String value) {
		if (value != null && !("null".equalsIgnoreCase(value)) && !value.isEmpty()) {
			return value;
		}
		return "";

	}

	
	
	
	

}
