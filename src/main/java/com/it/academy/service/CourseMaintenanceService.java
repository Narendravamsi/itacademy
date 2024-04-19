package com.it.academy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.academy.model.CourseMaintenance;
import com.it.academy.repository.CourseMaintenanceRepository;

@Service
public class CourseMaintenanceService {

	@Autowired
	private CourseMaintenanceRepository courseMaintenanceRepository;
	
	private Logger logger=LoggerFactory.getLogger(CourseMaintenanceService.class);
	ObjectMapper mapper= new ObjectMapper();

	public List<CourseMaintenance> getAllCourseDetails() {

		try {
			List<CourseMaintenance> coursesList = courseMaintenanceRepository.findAll();
			logger.info("coursesList from db==>"+mapper.writeValueAsString(coursesList));
			return coursesList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

}
