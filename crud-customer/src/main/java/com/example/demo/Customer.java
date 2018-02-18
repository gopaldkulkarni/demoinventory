package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String email;
	private String license;
	private String customerType;
	
	public Customer() {
		
	}
	
	public Customer(String name, String email, String license, String customerType) {
		super();
		this.name = name;
		this.email = email;
		this.license = license;
		this.customerType = customerType;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", license=" + license + ", customerType="
				+ customerType + "]";
	}
	

}
