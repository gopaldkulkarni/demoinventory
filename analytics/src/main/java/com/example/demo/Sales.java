package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sales {
	// TripId, CustID, BillAmoount, vModel, cType, tripType
	@Id
	@GeneratedValue
	private Long id;

	private Double billedAmount;
	private String customerType;

	private String vehicleType;

	private String tripType;// one of the Outstation or Instation

	private String vehicleModel;

	private Long tripId;

	public Sales() {

	}

	public Sales(Double billedAmount, String customerType, String vehicleType, String tripType, String vehicleModel,
			Long tripId) {
		super();
		this.billedAmount = billedAmount;
		this.customerType = customerType;
		this.vehicleType = vehicleType;
		this.tripType = tripType;
		this.vehicleModel = vehicleModel;
		this.tripId = tripId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public Long getId() {
		return id;
	}

	public Double getBilledAmount() {
		return billedAmount;
	}

	public void setBilledAmount(Double billedAmount) {
		this.billedAmount = billedAmount;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		vehicleModel = vehicleModel;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

}
