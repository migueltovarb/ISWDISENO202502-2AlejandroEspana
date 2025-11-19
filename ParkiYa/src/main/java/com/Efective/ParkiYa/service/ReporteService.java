package com.Efective.ParkiYa.service;

import java.util.List;
import java.util.Optional;

import com.Efective.ParkiYa.domain.entity.enums.TipoReporte;
import com.Efective.ParkiYa.dto.ReporteDTO;

public interface ReporteService {

    ReporteDTO generarReporte(ReporteDTO reporteDTO);

    ReporteDTO obtenerReporte(String id);

    List<ReporteDTO> consultarReportes(Optional<TipoReporte> tipo);
}
