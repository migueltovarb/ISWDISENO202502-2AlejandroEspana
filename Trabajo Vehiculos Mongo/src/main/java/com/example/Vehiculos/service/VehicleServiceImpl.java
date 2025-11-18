package com.example.Vehiculos.service;

import com.example.Vehiculos.domain.Vehicle;
import com.example.Vehiculos.dto.VehicleRequestDTO;
import com.example.Vehiculos.dto.VehicleResponseDTO;
import com.example.Vehiculos.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public List<VehicleResponseDTO> findAll() {
        return repository.findAll().stream().map(VehicleResponseDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public VehicleResponseDTO findById(Integer id) {
        Vehicle v = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return VehicleResponseDTO.fromEntity(v);
    }

    @Override
    public VehicleResponseDTO create(VehicleRequestDTO dto) {
        Vehicle v = new Vehicle();
        // generate integer id
        v.setId(sequenceGenerator.generateSequence(Vehicle.SEQUENCE_NAME));
        v.setMake(dto.getMake());
        v.setModel(dto.getModel());
        v.setYear(dto.getYear());
        v.setVin(dto.getVin());
        v.setPrice(dto.getPrice());
        Vehicle saved = repository.save(v);
        return VehicleResponseDTO.fromEntity(saved);
    }
    @Override
    public VehicleResponseDTO update(Integer id, VehicleRequestDTO dto) {
        Vehicle existing = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        existing.setMake(dto.getMake());
        existing.setModel(dto.getModel());
        existing.setYear(dto.getYear());
        existing.setVin(dto.getVin());
        existing.setPrice(dto.getPrice());
        Vehicle updated = repository.save(existing);
        return VehicleResponseDTO.fromEntity(updated);
    }
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        repository.deleteById(id);
    }
}
