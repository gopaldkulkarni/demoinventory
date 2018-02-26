package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.client.RestTemplate;

public class InventoryServiceCache {

	private static final String VEHICLE = "Vehicle";// TODO can be an ENUM
	static private Map<String, List<Vehicle>> cachedItems = new ConcurrentHashMap<>();
	private static String vehiclePort = "9094";

	public static List<Vehicle> getVehicles() {
		if (cachedItems.isEmpty()) {
			cachedItems.put(VEHICLE, _getVehicles());
		}
		return cachedItems.get(VEHICLE);
	}

	public static void invalidateCache() {
		cachedItems.put(VEHICLE, _getVehicles());
	}

	private static List<Vehicle> _getVehicles() {
		String url = "http://localhost:" + vehiclePort + "/vehicle/";
		RestTemplate restTemplate = new RestTemplate();
		List<Vehicle> vehicles = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			try {
				Vehicle v = restTemplate.getForObject(url + i, Vehicle.class);
				System.out.println("Got the one trips" + v);
				if (v != null) {
					vehicles.add(v);
					v.setInuse(InvetoryService.vehicleInUse(v.getVehicleId()));
				}
			} catch (Exception e) {
				System.out.println("May be no more elements! for id " + i);
			}
		}
		return vehicles;
	}

	public static void loadVehicle(List<Vehicle> vehicles) {
		cachedItems.put(VEHICLE, vehicles);
	}

}
