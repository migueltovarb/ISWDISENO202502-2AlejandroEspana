package com.Efective.ParkiYa.service;

import java.time.LocalDate;
import java.util.List;

import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.dto.ReservaDTO;

public interface ReservaService {

    ReservaDTO crearReserva(ReservaDTO reservaDTO);

    ReservaDTO actualizarEstado(String reservaId, EstadoReserva estado);

    List<ReservaDTO> obtenerReservas();

    List<ReservaDTO> obtenerReservasPorUsuario(String clienteId);

    ReservaDTO obtenerReserva(String id);

    List<ReservaDTO> obtenerReservasPorFecha(LocalDate fecha);
}
