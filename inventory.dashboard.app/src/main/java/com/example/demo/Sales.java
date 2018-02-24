package com.example.demo;

public class Sales {
	private Long id;
	private String customerType;
	private Double billedAmount;
	private String vehicleType;

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getId() {
		return id;
	}

	public void setTripId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Sales [id=" + id + ", customerType=" + customerType + ", billedAmount=" + billedAmount
				+ ", vehicleType=" + vehicleType + "]";
	}

}
