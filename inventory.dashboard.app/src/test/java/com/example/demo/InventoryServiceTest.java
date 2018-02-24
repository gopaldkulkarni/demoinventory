package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceTest {

	@Test
	public void getVehicles() {
		assertEquals(20, InvetoryService.getVehicles(false).size());
	}

	@Test
	public void getCustomers() {
		assertEquals(5, InvetoryService.getCustomers("CORPORATE").size());
	}

	@Test
	public void getRecentTrips() {
		assertEquals(4, InvetoryService.getRecentTrips(7).size());
	}
}
