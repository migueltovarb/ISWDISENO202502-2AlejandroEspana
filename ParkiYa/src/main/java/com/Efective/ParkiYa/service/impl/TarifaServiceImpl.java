package com.Efective.ParkiYa.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.Tarifa;
import com.Efective.ParkiYa.dto.TarifaDTO;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.TarifaRepository;
import com.Efective.ParkiYa.service.TarifaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarifaServiceImpl implements TarifaService {

    private final TarifaRepository tarifaRepository;
    private final ParkiYaMapper mapper;

    @Override
    public TarifaDTO crearTarifa(TarifaDTO tarifaDTO) {
        Tarifa tarifa = mapper.toTarifaEntity(tarifaDTO);
        tarifa.setVigente(true);
        Tarifa guardada = tarifaRepository.save(tarifa);
        return mapper.toTarifaDto(guardada);
    }

    @Override
    public TarifaDTO actualizarTarifa(String id, TarifaDTO tarifaDTO) {
        Tarifa tarifa = obtenerTarifaEntity(id);
        tarifa.setTipo(tarifaDTO.getTipo());
        tarifa.setValorHora(tarifaDTO.getValorHora());
        tarifa.setHorarioInicio(tarifaDTO.getHorarioInicio());
        tarifa.setHorarioFin(tarifaDTO.getHorarioFin());
        tarifa.setVigente(tarifaDTO.isVigente());
        return mapper.toTarifaDto(tarifaRepository.save(tarifa));
    }

    @Override
    public List<TarifaDTO> obtenerTarifasVigentes() {
        return tarifaRepository.findByVigenteTrue().stream()
                .map(mapper::toTarifaDto)
                .collect(Collectors.toList());
    }

    @Override
    public TarifaDTO obtenerTarifa(String id) {
        return mapper.toTarifaDto(obtenerTarifaEntity(id));
    }

    @Override
    public double calcularCosto(String tarifaId, long minutos) {
        Tarifa tarifa = obtenerTarifaEntity(tarifaId);
        double horas = minutos / 60.0;
        return Math.round((horas * tarifa.getValorHora()) * 100.0) / 100.0;
    }

    private Tarifa obtenerTarifaEntity(String id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada"));
    }
}
