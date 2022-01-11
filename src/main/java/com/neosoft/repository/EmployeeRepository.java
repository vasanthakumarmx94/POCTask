package com.neosoft.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.neosoft.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	List<Employee> findByFirstnameOrSurnameOrPincode(String firstname,String surname,String pincode);

	List<Employee> findAllByOrderByDobAsc();
	
	List<Employee> findByFirstname(String Firstname);
	List<Employee> findBySurname(String surname);
	List<Employee> findByPincode(String pincode);
	
	//Soft delete.
	@Query("update Employee e set e.active=1 where e.id=?1")
	@Modifying
	@Transactional
	public void softDelete(long id); 
	
	
	
}
