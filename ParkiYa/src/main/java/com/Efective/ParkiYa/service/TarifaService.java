package com.Efective.ParkiYa.service;

import java.util.List;

import com.Efective.ParkiYa.dto.TarifaDTO;

public interface TarifaService {

    TarifaDTO crearTarifa(TarifaDTO tarifaDTO);

    TarifaDTO actualizarTarifa(String id, TarifaDTO tarifaDTO);

    List<TarifaDTO> obtenerTarifasVigentes();

    TarifaDTO obtenerTarifa(String id);

    double calcularCosto(String tarifaId, long minutos);
}
