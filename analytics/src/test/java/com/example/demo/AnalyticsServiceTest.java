package com.example.demo;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyticsServiceTest {
	@Autowired
	SalesRepository repo;

	@Test
	public void initializeSalesData() {
		new AnalyticsService().initializeTheSalesData(repo);
		List<Sales> findAll = repo.findAll();
		System.out.println("total data  = " + findAll.size());
		assertNotNull(findAll);
	}
}
