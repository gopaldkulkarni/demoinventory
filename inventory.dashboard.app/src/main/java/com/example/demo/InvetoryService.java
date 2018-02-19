package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class InvetoryService {
	public static List<Vehicle> getVehicles() {
		String url = "http://localhost:8082/vehicle/";
		RestTemplate restTemplate = new RestTemplate();
		List<Vehicle> vehicles = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			try {
				Vehicle v = restTemplate.getForObject(url + i, Vehicle.class);
				System.out.println("Got the one " + v);
				if (v != null) {
					vehicles.add(v);
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! for id " + i);
			}
		}
		return vehicles;
	}

	public static List<Sales> getSalesItems(String customerType, String vehicleType) {
		String url = "http://localhost:9091/sales/";
		RestTemplate restTemplate = new RestTemplate();
		List<Sales> sales = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Sales s = restTemplate.getForObject(url + i, Sales.class);
				System.out.println("Got the one sale " + s);
				if (s != null) {
					// TODO write REST API to handle the customerType
					if (customerType != null && vehicleType != null) {
						if (s.getCustomerType().equalsIgnoreCase(customerType)
								&& s.getVehicleType().equalsIgnoreCase(vehicleType))
							sales.add(s);
					} else if (customerType != null) {
						if (s.getCustomerType().equalsIgnoreCase(customerType)) {
							sales.add(s);
						}
					} else if (vehicleType != null) {
						if (s.getVehicleType().equalsIgnoreCase(vehicleType)) {
							sales.add(s);
						}
					} else {
						sales.add(s);// add all
					}
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! " + e.toString());
				break;
			}
		}
		return sales;
	}

	public static Collection<Customer> getCustomers(String filterTxt) {
		String url = "http://localhost:8080/customer/";
		RestTemplate restTemplate = new RestTemplate();
		List<Customer> customers = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Customer customer = restTemplate.getForObject(url + i, Customer.class);
				System.out.println("Got the one " + customer);
				if (customer != null) {
					// TODO write REST API to handle the customerType
					if (filterTxt.trim().isEmpty())
						customers.add(customer);
					else if (customer.getCustomerType().equalsIgnoreCase(filterTxt))
						customers.add(customer);
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! " + e.toString());
				break;
			}
		}
		return customers;
	}

	public static List<Trip> getRecentTrips(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		Date time = cal.getTime();
		System.out.println("Date = " + time);

		String url = "http://localhost:9001/trip/";
		RestTemplate restTemplate = new RestTemplate();
		List<Trip> trips = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Trip t = restTemplate.getForObject(url + i, Trip.class);
				System.out.println("Got the one Trip " + t);
				if (t != null) {
					if (t.getTripStart().getTime() >= time.getTime())
						trips.add(t);
				}
			} catch (Exception e) {
				System.out.println("May be no more trips! " + e.toString());
				break;
			}
		}
		return trips;
	}

}
