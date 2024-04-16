package com.it.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CourseMaintenancePrimaryKey {
@Column(name="academicyear")
private String academicYear;

@Column(name="batch")
private String batch;

@Column(name="course")
private String course;

public String getAcademicYear() {
	return academicYear;
}

public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}

public String getBatch() {
	return batch;
}

public void setBatch(String batch) {
	this.batch = batch;
}

public String getCourse() {
	return course;
}

public void setCourse(String course) {
	this.course = course;
}






}
