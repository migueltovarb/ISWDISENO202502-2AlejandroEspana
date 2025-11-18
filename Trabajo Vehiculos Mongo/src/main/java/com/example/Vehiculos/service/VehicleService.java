package com.example.Vehiculos.service;

import java.util.List;

import com.example.Vehiculos.dto.VehicleRequestDTO;
import com.example.Vehiculos.dto.VehicleResponseDTO;

public interface VehicleService {

    List<VehicleResponseDTO> findAll();

    VehicleResponseDTO findById(Integer id);

    VehicleResponseDTO create(VehicleRequestDTO dto);

    VehicleResponseDTO update(Integer id, VehicleRequestDTO dto);

    void delete(Integer id);
}
