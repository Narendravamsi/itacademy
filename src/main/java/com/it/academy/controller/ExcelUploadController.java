package com.it.academy.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.academy.excel.CourseMaintenanceExcelUpload;
import com.it.academy.excel.CourseMaintenanceTemplate;
import com.it.academy.model.CourseMaintenance;

import jakarta.servlet.http.HttpServletResponse;
@RestController
@CrossOrigin("*")
public class ExcelUploadController {
	
	@Autowired
	private  CourseMaintenanceTemplate courseMaintenanceTemplate;
	
	@Autowired
	private CourseMaintenanceExcelUpload courseMaintenanceExcelUpload;
	
	private Logger logger=LoggerFactory.getLogger(ExcelUploadController.class);
	
	ObjectMapper mapper= new ObjectMapper();
	

	@GetMapping("/courseMaintenanceTemplate")
	public void courseMaintenanceTemplate(HttpServletResponse response) throws Throwable {

		try {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());
			String fileName = "CourseMaintenanceTemplate" + currentDateTime + "_.xlsx";
			logger.info("file nameis ===>{}", fileName);
			String extension = "_.xlsx";

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=" + fileName;
			logger.info("headerValue====>" + headerValue);
			response.setHeader(headerKey, headerValue);
				courseMaintenanceTemplate.courseMaintenance(response);

		} catch (Exception e) {
			logger.error("Error generatingPractical marks Excel", e);
		}
	}
	
	@PostMapping(value = "/savecourseMiantenanceData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<CourseMaintenance>> saveCourseMaintenanceExcel(
			@RequestPart(name = "courseMaintenanceUpload") MultipartFile file, @RequestParam Map<String, String> allParams) {
		List<CourseMaintenance> courseMaintenanceList = new ArrayList<>();
		try {
			courseMaintenanceList = courseMaintenanceExcelUpload.saveCourseMaintenanceExcel(file, allParams);
			return ResponseEntity.status(HttpStatus.OK).body(courseMaintenanceList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

}
