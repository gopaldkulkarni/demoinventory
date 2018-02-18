package com.example.vehicle;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(VehicleRepository repository) {
		return (args) -> {
			// save a couple of trips
			repository.save(new Vehicle("Innova", 12000L, 7));
			repository.save(new Vehicle("Indica", 5000L, 5));
			repository.save(new Vehicle("Innova", 500L, 7));
			repository.save(new Vehicle("Mercedece E2", 12000L, 5));
			repository.save(new Vehicle("BMW 320", 1000L, 5));
			repository.save(new Vehicle("BMW X6", 2000L, 5));
			repository.save(new Vehicle("XUV 500", 4000L, 7));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
			repository.save(new Vehicle("BMW 520", 1000L, 5));
		};
	}
}
