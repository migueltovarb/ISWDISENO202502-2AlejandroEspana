package com.Efective.ParkiYa.service;

import java.time.LocalDate;
import java.util.List;

import com.Efective.ParkiYa.dto.DisponibilidadParqueaderoDTO;
import com.Efective.ParkiYa.dto.ParqueaderoDTO;
import com.Efective.ParkiYa.dto.ReporteParqueaderoDTO;

public interface ParqueaderoService {

    ParqueaderoDTO crearParqueadero(ParqueaderoDTO dto);

    ParqueaderoDTO actualizarParqueadero(String id, ParqueaderoDTO dto);

    List<ParqueaderoDTO> obtenerParqueaderos();

    ParqueaderoDTO obtenerParqueadero(String id);

    DisponibilidadParqueaderoDTO consultarDisponibilidad(String id, LocalDate fecha);

    ReporteParqueaderoDTO generarReporteOcupacion(String id, LocalDate fecha);
}
