package com.Efective.ParkiYa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.Reserva;
import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;

public interface ReservaRepository extends MongoRepository<Reserva, String> {

    List<Reserva> findByClienteId(String clienteId);

    List<Reserva> findByEstado(EstadoReserva estado);

    List<Reserva> findByFecha(LocalDate fecha);
}
