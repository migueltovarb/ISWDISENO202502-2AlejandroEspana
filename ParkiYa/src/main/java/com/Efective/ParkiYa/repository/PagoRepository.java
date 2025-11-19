package com.Efective.ParkiYa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.Pago;

public interface PagoRepository extends MongoRepository<Pago, String> {

    List<Pago> findByReservaId(String reservaId);
}
