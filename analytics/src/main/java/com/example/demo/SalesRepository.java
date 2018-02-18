package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SalesRepository extends JpaRepository<Sales, Long> {
	List<Sales> findByCustomerType(@RequestParam(value = "customerType") String customertype);
}
