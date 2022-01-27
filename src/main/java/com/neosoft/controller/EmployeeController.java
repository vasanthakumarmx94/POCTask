package com.neosoft.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	// http://localhost:8080/employees
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public Object getAllEmployees() {
		return employeeRepository.findAll().stream().filter(el -> el.getActive() == 0);
	}
	// http://localhost:8080/employees/register
		@CrossOrigin(origins = "http://localhost:4200")
		@PostMapping("/employees/register")
		public void registerEmployee(@RequestBody Employee employee) {
			employeeRepository.save(employee);
		}

		// http://localhost:8080/employees
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/employees/{id}")
		public ResponseEntity<Employee> getEmployeesbyId(@PathVariable Long id) {
			Employee employee= employeeRepository.findById(id).filter(el -> el.getActive() == 0)
					.orElseThrow(()->new ResourceNotFoundException("EmployeeNot Found with id:"+id));
			return ResponseEntity.ok(employee);
		}
		
		
//      http://localhost:8080/emloyees/1
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/emloyees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeReq) {
			Employee employee=employeeRepository.findById(id)
					.orElseThrow(()->new ResourceNotFoundException("EmployeeNot Found with id:"+id));
			
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
			
			Employee updatedEmployee=employeeRepository.save(employee);
			return ResponseEntity.ok(updatedEmployee);
					
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
		

		
	// http://localhost:8080/employees/sortByDOBAndJDate"
	@GetMapping("/employees/sortByDOBAndJDate")
	public Object getAllEmployeesSortByDobAndJDate() {
		return employeeRepository.findAllByOrderByDobAsc().stream().filter(el -> el.getActive() == 0);
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
	


	

}
