package com.Efective.ParkiYa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.Tarifa;

public interface TarifaRepository extends MongoRepository<Tarifa, String> {

    List<Tarifa> findByVigenteTrue();

    Optional<Tarifa> findByTipo(String tipo);
}
