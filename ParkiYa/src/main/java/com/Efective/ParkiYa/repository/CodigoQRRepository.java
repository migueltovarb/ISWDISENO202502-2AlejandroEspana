package com.Efective.ParkiYa.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.CodigoQR;

public interface CodigoQRRepository extends MongoRepository<CodigoQR, String> {

    Optional<CodigoQR> findByReservaId(String reservaId);
}
