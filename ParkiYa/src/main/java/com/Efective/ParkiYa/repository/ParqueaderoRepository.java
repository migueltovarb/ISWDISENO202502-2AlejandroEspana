package com.Efective.ParkiYa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.Parqueadero;

public interface ParqueaderoRepository extends MongoRepository<Parqueadero, String> {
}
