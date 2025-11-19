package com.Efective.ParkiYa.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.Reporte;
import com.Efective.ParkiYa.domain.entity.enums.TipoReporte;
import com.Efective.ParkiYa.dto.ReporteDTO;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.ReporteRepository;
import com.Efective.ParkiYa.service.ReporteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final ParkiYaMapper mapper;

    @Override
    public ReporteDTO generarReporte(ReporteDTO reporteDTO) {
        Reporte reporte = mapper.toReporteEntity(reporteDTO);
        reporte.setFechaGeneracion(LocalDateTime.now());
        Reporte guardado = reporteRepository.save(reporte);
        return mapper.toReporteDto(guardado);
    }

    @Override
    public ReporteDTO obtenerReporte(String id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        return mapper.toReporteDto(reporte);
    }

    @Override
    public List<ReporteDTO> consultarReportes(Optional<TipoReporte> tipo) {
        List<Reporte> reportes = tipo
                .map(reporteRepository::findByTipo)
                .orElseGet(reporteRepository::findAll);
        return reportes.stream()
                .map(mapper::toReporteDto)
                .collect(Collectors.toList());
    }
}
