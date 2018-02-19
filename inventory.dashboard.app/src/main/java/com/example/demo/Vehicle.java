package com.example.demo;

public class Vehicle {
	private Long vehicleId;
	private String model;
	private Long milage;
	private Integer capacity;
	private Boolean inuse;

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

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Boolean getInuse() {
		return inuse;
	}

	public void setInuse(Boolean inuse) {
		this.inuse = inuse;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

}
