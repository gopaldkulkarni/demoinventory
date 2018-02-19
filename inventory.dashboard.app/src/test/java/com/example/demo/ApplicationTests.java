package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:8081", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
