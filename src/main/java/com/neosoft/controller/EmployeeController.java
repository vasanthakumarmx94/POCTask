package com.neosoft.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.myexception.ResourceNotFoundException;
import com.neosoft.model.Employee;
import com.neosoft.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	// http://localhost:8080/employees
	@GetMapping("/employees")
	public Object getAllEmployees() {
		return employeeRepository.findAll().stream().filter(el -> el.getActive() == 0);
	}

	
	// http://localhost:8080/employees/sortByDOBAndJDate"
	@GetMapping("/employees/sortByDOBAndJDate")
	public Object getAllEmployeesSortByDobAndJDate() {
		return employeeRepository.findAllByOrderByDobAsc().stream().filter(el -> el.getActive() == 0);
	}

	// http://localhost:8080/employees/register
	@PostMapping("/employees/register")
	public void registerEmployee(@RequestBody Employee employee) {
		employeeRepository.save(employee);
	}

	// http://localhost:8080/employees/Raju/Kumar/572127
	@GetMapping("/employees/and/{firstname}/{surname}/{pincode}")
	public Object getEmployeesByfirstnameOrlastnameOrpincode(@PathVariable String firstname,
			@PathVariable String surname, @PathVariable String pincode) {
		return employeeRepository.findByFirstnameOrSurnameOrPincode(firstname, surname, pincode).stream()
				.filter(el -> el.getActive() == 0);
	}

	// http://localhost:8080/employees/Raju
	@GetMapping("/employees/firstname/{firstname}")
	public Object getEmployeesByfirstname(@PathVariable String firstname) {
		return employeeRepository.findByFirstname(firstname).stream().filter(el -> el.getActive() == 0);
	}

	// http://localhost:8080/employees/and/Raju/Kumar/572127
	@GetMapping("/employees/surname/{surname}")
	public Object getEmployeesBysurname(@PathVariable String surname) {
		return employeeRepository.findBySurname(surname).stream().filter(el -> el.getActive() == 0);
	}

	// http://localhost:8080/employees/pincode/572127
		@GetMapping("/employees/pincode/{pincode}")
		public Object getEmployeesBypincode(@PathVariable String pincode) {
			return employeeRepository.findByPincode(pincode).stream().filter(el -> el.getActive() == 0);
		}
	
//      http://localhost:8080/emloyees/1
	@PutMapping("/emloyees/{id}")
	public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeReq) {
		return employeeRepository.findById(id).map(employee -> {
			employee.setFirstname(employeeReq.getFirstname());
			employee.setSurname(employeeReq.getSurname());
			employee.setPhoneNo(employeeReq.getPhoneNo());
			employee.setDob(employeeReq.getDob());
			employee.setAge(employeeReq.getAge());
			employee.setUserName(employeeReq.getUserName());
			employee.setJoiningdate(employeeReq.getJoiningdate());
			employee.setAddress(employeeReq.getAddress());
			employee.setPincode(employeeReq.getPincode());
			employee.setActive(employeeReq.getActive());

			return employeeRepository.save(employee);
		}).orElseThrow(() -> new ResourceNotFoundException("Employee", "EmployeeId", id));
	}

	// http://localhost:8080/emloyees/soft/{id}
	@DeleteMapping("/emloyees/soft/{id}")
	public void softdeleteEmployee(@PathVariable long id) {
		employeeRepository.softDelete(id);
	}

	// http://localhost:8080/emloyees/hard/{id}
	@DeleteMapping("/emloyees/hard/{id}")
	public void harddeleteEmployee(@PathVariable long id) {
		employeeRepository.deleteById(id);
	}

}
