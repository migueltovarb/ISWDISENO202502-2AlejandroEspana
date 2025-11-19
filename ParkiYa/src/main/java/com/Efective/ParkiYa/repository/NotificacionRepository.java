package com.Efective.ParkiYa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.Notificacion;

public interface NotificacionRepository extends MongoRepository<Notificacion, String> {

    List<Notificacion> findByUsuarioId(String usuarioId);

    List<Notificacion> findByReservaId(String reservaId);
}
