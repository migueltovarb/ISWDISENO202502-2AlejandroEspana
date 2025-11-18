package com.example.Vehiculos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Vehiculos.domain.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, Integer> {

}
