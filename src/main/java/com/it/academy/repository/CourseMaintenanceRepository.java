package com.it.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.it.academy.model.CourseMaintenance;
import com.it.academy.model.CourseMaintenancePrimaryKey;

@Repository
public interface CourseMaintenanceRepository extends JpaRepository<CourseMaintenance, CourseMaintenancePrimaryKey> {

}
