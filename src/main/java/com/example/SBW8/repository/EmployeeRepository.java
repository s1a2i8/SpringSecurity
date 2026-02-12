package com.example.SBW8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SBW8.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
	
	Employee findByEmail(String email);

}
