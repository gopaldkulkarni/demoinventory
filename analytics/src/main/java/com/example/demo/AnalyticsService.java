package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class AnalyticsService {
	private static String customerPort = "9092";
	private static String tripPort = "9093";
	private static String vehiclePort = "9094";

	private static List<Customer> loadCustomers() {
		String url = "http://localhost:" + customerPort + "/customer/";
		RestTemplate restTemplate = new RestTemplate();
		List<Customer> customers = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Customer customer = restTemplate.getForObject(url + i, Customer.class);
				System.out.println("Got the one " + customer);
				if (customer != null) {
					customers.add(customer);
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! " + e.toString());
			}
		}
		return customers;
	}

	private Sales constructSalesObject(Trip t, List<Customer> customers) {
		String url = "http://localhost:" + vehiclePort + "/vehicle/";
		RestTemplate restTemplate = new RestTemplate();
		Long vehicleId = t.getVehicleId();
		String model = null;
		try {
			Vehicle vehicle = restTemplate.getForObject(url + vehicleId, Vehicle.class);
			model = vehicle.getModel();
		} catch (Exception e) {
			System.out.println("Probably no vehicle with vehicle ID " + vehicleId);
		}
		String customerType = null;
		for (Customer c : customers) {
			if (c.getId().equals(t.getCustomerId())) {
				customerType = c.getCustomerType();
			}
		}
		Double price = getBilledAmount(t.getDistanceCovered(), model);
		Sales sales = new Sales(price, customerType, model, t.getTripType(), model);

		return sales;
	}

	private Double getBilledAmount(String distanceCovered, String model) {
		if (model.toLowerCase().indexOf("innova") >= 0 || model.toLowerCase().indexOf("xuv") >= 0) {
			int value = Integer.parseInt(distanceCovered) * 12;
			return new Double(value);
		} else if (model.toLowerCase().indexOf("indica") >= 0) {
			int value = Integer.parseInt(distanceCovered) * 9;
			return new Double(value);
		}
		int value = Integer.parseInt(distanceCovered) * 20;
		return new Double(value);
	}

	public void initializeTheSalesData(SalesRepository repository) {
		String url;
		RestTemplate restTemplate;
		List<Customer> customers = loadCustomers();

		url = "http://localhost:" + tripPort + "/trip/";
		restTemplate = new RestTemplate();
		List<Trip> trips = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Trip t = restTemplate.getForObject(url + i, Trip.class);
				System.out.println("Got the one Trip " + t);
				if (t != null) {
					trips.add(t);
					repository.save(constructSalesObject(t, customers));
				}
			} catch (Exception e) {
				System.out.println("May be no more trips! " + e.toString());
			}
		}
	}
}
