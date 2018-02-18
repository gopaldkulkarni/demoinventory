package com.example.vehicle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue
	private Long vehicleId;// TODO registration number can be used

	private String model;
	private Long milage;
	private Integer capacity;

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getMilage() {
		return milage;
	}

	public void setMilage(Long milage) {
		this.milage = milage;
	}

	public Vehicle() {

	}

	public Vehicle(String model, Long milage, Integer capacity) {
		super();
		this.model = model;
		this.milage = milage;
		this.capacity = capacity == null ? 5 : capacity;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", model=" + model + ", milage=" + milage + "]";
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}
