package com.example.demo;

import java.util.Date;

public class Trip {
	private Long id;
	private Date tripStart;
	private Date tripEnd;

	private String distanceCovered;
	private String tripStatus; // One of the Completed, InProgress, Cancelled

	private Long customerId;
	private Long vehicleId;

	private String tripType;

	public String getDistanceCovered() {
		return distanceCovered;
	}

	public void setDistanceCovered(String distanceCovered) {
		this.distanceCovered = distanceCovered;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Date getTripStart() {
		return tripStart;
	}

	public void setTripStart(Date tripStart) {
		this.tripStart = tripStart;
	}

	public Date getTripEnd() {
		return tripEnd;
	}

	public void setTripEnd(Date tripEnd) {
		this.tripEnd = tripEnd;
	}

	@Override
	public String toString() {
		return "Trip [id=" + ", tripStart=" + tripStart + ", tripEnd=" + tripEnd + ", distanceCovered="
				+ distanceCovered + ", tripStatus=" + tripStatus + ", customerId=" + customerId + ", vehicleId="
				+ vehicleId + "]";
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

	public void setId(Long tripId) {
		this.id = tripId;
	}

}
