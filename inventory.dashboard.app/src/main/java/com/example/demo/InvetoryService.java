package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.web.client.RestTemplate;

public class InvetoryService {
	private static String customerPort = "9092";
	private static String tripPort = "9093";

	public static List<Vehicle> getVehicles(Boolean refreshCache) {
		if (refreshCache) {
			InventoryServiceCache.invalidateCache();
		}
		return InventoryServiceCache.getVehicles();
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
					if (customerType == null && vehicleType == null) {
						sales.add(s);
					} else if (matchFound(s, customerType, vehicleType)) {
						sales.add(s);
					}
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! " + e.toString());
				break;
			}
		}
		return sales;
	}

	private static boolean matchFound(Sales s, String customerType, String vehicleType) {
		System.out.println("Checking match for " + customerType + " " + vehicleType);
		System.out.println("Against : " + s);
		if (customerType != null && !customerType.trim().isEmpty()) {
			if (!s.getCustomerType().toLowerCase().startsWith(customerType.toLowerCase())) {
				return false;
			}
		}
		if (vehicleType == null) {
			return true;
		}
		if (s.getVehicleType().toLowerCase().startsWith(vehicleType))
			return true;

		return false;
	}

	public static Collection<Customer> getCustomers(String filterTxt) {
		String url = "http://localhost:" + customerPort + "/customer/";
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

	public static List<Trip> getAllTrips() {

		String url = "http://localhost:" + tripPort + "/trip/";
		RestTemplate restTemplate = new RestTemplate();
		List<Trip> trips = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Trip t = restTemplate.getForObject(url + i, Trip.class);
				System.out.println("Got the one Trip " + t);
				if (t != null) {
					trips.add(t);
				}
			} catch (Exception e) {
				System.out.println("May be no more trips! " + e.toString());
			}
		}
		return trips;
	}

	public static List<Trip> getRecentTrips(int days) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date currentTime = cal.getTime();

		cal.add(Calendar.DAY_OF_MONTH, days);
		Date time = cal.getTime();
		System.out.println("Date = " + time);

		String url = "http://localhost:" + tripPort + "/trip/";
		RestTemplate restTemplate = new RestTemplate();
		List<Trip> trips = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				Trip t = restTemplate.getForObject(url + i, Trip.class);
				System.out.println("Got the one Trip " + t);
				if (t != null) {
					if (t.getTripStart().getTime() >= currentTime.getTime()) {
						trips.add(t);
					} else
						System.out.println("Not within 7 days!" + t.getTripStart());
				}
			} catch (Exception e) {
				System.out.println("May be no more trips! " + e.toString());
				break;
			}
		}
		return trips;
	}

	/**
	 * to know if the given ID is in use
	 * 
	 * @param vehicleId
	 * @return
	 */
	public static boolean vehicleInUse(Long vehicleId) {
		List<Trip> trips = getAllTrips();
		for (Trip t : trips) {
			if (t.getVehicleId() == vehicleId) {
				if (t.getTripStatus().toLowerCase().indexOf("inprogress") >= 0) {
					return true;
				}
				break;
			}
		}
		return false;
	}

}
