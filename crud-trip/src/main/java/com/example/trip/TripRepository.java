package com.example.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trip", path = "trip")
public interface TripRepository extends JpaRepository<Trip, Long> {
}
