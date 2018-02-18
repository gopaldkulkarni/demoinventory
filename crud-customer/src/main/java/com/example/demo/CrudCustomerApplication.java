package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudCustomerApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			// save a couple of trips
			repository.save(new Customer("John", "j@some.com", "Lic111", "CORPORATE"));
			repository.save(new Customer("Jill", "jill@some.com", "Lic222", "CORPORATE"));
			repository.save(new Customer("Mahesh", "mahesh@some.com", "Lic333", "INDIVIDUAL"));
			repository.save(new Customer("Samual", "sam@some.com", "Lic444", "INDIVIDUAL"));
			repository.save(new Customer("Akash", "aka@some.com", "Lic555", "CORPORATE"));
			repository.save(new Customer("Appu", "app@some.com", "Lic666", "CORPORATE"));
			repository.save(new Customer("John", "j@some.com", "Lic777", "CORPORATE"));
			repository.save(new Customer("John", "j@some.com", "Lic888", "INDIVIDUAL"));
		};
	}
}
