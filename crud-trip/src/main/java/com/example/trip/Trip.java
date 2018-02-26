package com.example.trip;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Trip {
	@Id
	@GeneratedValue
	private Long id;

	private String tripType; // One of the OutStation, InStation

	private Date tripStart;
	private Date tripEnd;

	private String distanceCovered; // TODO String?
	private String tripStatus; // One of the Completed, InProgress, Cancelled
	// TODO what if the trip is for future date?

	private Long customerId;
	private Long vehicleId;

	public Long getId() {
		return id;
	}

	public Trip() {

	}

	public Trip(String distanceCovered, String tripStatus, Long customerId, Long vehicleId, Date start, Date end,
			String tripType) {
		super();
		this.distanceCovered = distanceCovered;
		this.tripStatus = tripStatus;
		this.customerId = customerId;
		this.vehicleId = vehicleId;
		this.tripStart = start;
		this.tripEnd = end;
		this.tripType = tripType;
	}

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
		return "Trip [id=" + id + ", tripStart=" + tripStart + ", tripEnd=" + tripEnd + ", distanceCovered="
				+ distanceCovered + ", tripStatus=" + tripStatus + ", customerId=" + customerId + ", vehicleId="
				+ vehicleId + "]";
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

}
