package com.neosoft.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.neosoft.model.Employee;

import com.neosoft.repository.EmployeeRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class EmployeeControllerTest {

	@Autowired
	private TestEntityManager entityManager;
	

	@Autowired
	EmployeeRepository employeeRepo;
	


	@Test
	@Order(1)
	public void testRegisterEmployee() throws ParseException {
		Employee emp=new Employee();
		emp.setFirstname("Raju");
		emp.setSurname("kumar");
		emp.setPhoneNo("8787878755");
		String date="Sat Jun 01 12:53:10 IST 1998";
	    SimpleDateFormat sdf=new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
	    Date dob;
	    dob=sdf.parse(date);
		emp.setDob(dob);
		
		String date1="Sat Jun 02 12:53:10 IST 2021";
	    SimpleDateFormat sdf1=new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
	    Date jdate;
	    jdate=sdf1.parse(date1);
		emp.setJoiningdate(jdate);
		emp.setAge(24);
		emp.setUserName("raju123");
		emp.setAddress("Tumakur");
		emp.setPincode("572121");
		emp.setActive(0);
		Employee savedempoloyee=employeeRepo.save(emp);
		Employee existingemployee=entityManager.find(Employee.class, savedempoloyee.getId());
		assertThat(emp.getUserName()).isEqualTo(existingemployee.getUserName());
		
		
	}
	
	@Test
	@Order(4)
	public void TestUpdateEmployee() throws ParseException {
		Employee emp=employeeRepo.findByFirstname("vasu").get(0);
		emp.setFirstname("vasu");
		emp.setSurname("kumar");
		emp.setPhoneNo("8787666755");	
		emp.setAge(23);
		emp.setUserName("vasuraam123");
		emp.setAddress("mysore");
		emp.setPincode("572127");
		emp.setActive(0);
	
		Employee savedempoloyee=employeeRepo.save(emp);
		Employee existingemployee=entityManager.find(Employee.class, savedempoloyee.getId());
		assertThat(emp.getUserName()).isEqualTo(existingemployee.getUserName());
		
	}
	
	
	@Test
	@Order(2)
	public void testFindByFirstname() {
		String firstname = "vasu";
		Employee employee = employeeRepo.findByFirstname(firstname).get(0);
		assertThat( employee.getFirstname()).isEqualTo(firstname);
		
	}
	
	@Test
	@Order(3)
	public void testgetAllEmployee() {
	
		List<Employee> employee = employeeRepo.findAll().stream().filter(el -> el.getActive() == 0).collect(Collectors.toList());
		 assertThat(employee.size()).isGreaterThan(0);
		
	}
	
	@Test
	@Order(5)
	public void testgetEmployeesByfirstnameOrlastnameOrpincode() {
	
		List<Employee> employee = employeeRepo.findByFirstnameOrSurnameOrPincode("vasu", "raam", "572127").stream()
				.filter(el -> el.getActive() == 0).collect(Collectors.toList());
		 assertThat(employee.size()).isGreaterThan(0);
		
	}
	
	@Test
	@Order(6)
	public void testSoftDeleteEmployee() {
		Employee employee = employeeRepo.findById((long) 4).get();
		employee.setActive(1);
		employeeRepo.save(employee);
		assertThat(employee.getActive()).isEqualTo(1);
		
	}
	
	 @Test
	 @Order(7)
	public void testHardDeleteEmployee() {
		 Employee employee = employeeRepo.findById(1L).get();
		employeeRepo.delete(employee);
		Employee employee1=null;
		Optional<Employee> optionalemployee =employeeRepo.findById(1L);
		if(optionalemployee.isPresent()) {
			employee1=optionalemployee.get();
		}
		assertThat(employee1).isNull();
	}
	
	
	
	
	
	
}
