package com.it.academy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.academy.model.CourseMaintenance;
import com.it.academy.service.CourseMaintenanceService;

@RestController
@CrossOrigin("*")
public class CourseMaintenanceController {

	@Autowired
	private CourseMaintenanceService courseMaintenanceService;
	
	@GetMapping("/getAllCourseDetails")
	public ResponseEntity<List<CourseMaintenance>> getAllCourseDetails() {

		try {
			List<CourseMaintenance> courseDetails = courseMaintenanceService.getAllCourseDetails();
			return ResponseEntity.status(HttpStatus.OK).body(courseDetails);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}

	}

}
