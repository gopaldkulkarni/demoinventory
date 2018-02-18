package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(SalesRepository repository) {
		return (args) -> {
			String url = "http://localhost:8080/customer/";
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

			url = "http://localhost:9001/trip/";
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

		};
	}

	private Sales constructSalesObject(Trip t, List<Customer> customers) {
		String url = "http://localhost:8082/vehicle/";
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

	private static void initializeTheSalesData() {
		String url = "http://localhost:8080/customer/";
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
	}
}
