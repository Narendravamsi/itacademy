package com.it.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="course_maintenance")
public class CourseMaintenance {
	
	@EmbeddedId
	private CourseMaintenancePrimaryKey courseMaintenancePrimaryKey;
	
	@Column(name="intake")
	private String intake;
	
	@Column(name="fee")
	private String fee;

	public CourseMaintenancePrimaryKey getCourseMaintenancePrimaryKey() {
		return courseMaintenancePrimaryKey;
	}

	public void setCourseMaintenancePrimaryKey(CourseMaintenancePrimaryKey courseMaintenancePrimaryKey) {
		this.courseMaintenancePrimaryKey = courseMaintenancePrimaryKey;
	}

	public String getIntake() {
		return intake;
	}

	public void setIntake(String intake) {
		this.intake = intake;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	
	
	
	
	
	
}
