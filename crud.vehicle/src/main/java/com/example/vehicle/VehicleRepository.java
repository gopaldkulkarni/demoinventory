package com.example.vehicle;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "vehicle", path = "vehicle")
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {

}
