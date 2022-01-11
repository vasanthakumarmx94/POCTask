package com.neosoft.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 65)
	private String firstname;
	@NotNull
	@Size(max = 65)
	private String surname;
	
	@NotNull
	@Size(max = 15)
	private String phoneNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dob")
	private Date dob;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="joiningdate")
	private Date joiningdate;
	
	@NotNull
	private int age;
	
	@NotNull
	@Size(max = 65)
	private String userName;
	
	@NotNull
	@Size(max = 265)
	private String Address;
	
	@NotNull
	@Size(max = 10)
	private String pincode;
	
	@Column(name="is_active")
	private int active;
	
	
	public void validatePhoneNumber() {
		if(this.phoneNo.length() !=10)
			throw new RuntimeException("Phone number should be 10 digits long");
		if(!this.phoneNo.matches("\\d+"))
			throw new RuntimeException("Phone number should contain only digits");
		if(this.phoneNo.startsWith("0"))
			throw new RuntimeException("Phone number should not start with 0");
		if(this.phoneNo.length()>=13)
			throw new RuntimeException("Phone number should not greater than 13 digit");
	
	}


	
}
