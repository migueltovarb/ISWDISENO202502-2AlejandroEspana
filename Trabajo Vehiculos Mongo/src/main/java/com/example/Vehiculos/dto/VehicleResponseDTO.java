package com.example.Vehiculos.dto;

import com.example.Vehiculos.domain.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class VehicleResponseDTO {

    private Integer id;
    private String make;
    private String model;
    private Integer year;
    private String vin;
    private Double price;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;

    public static VehicleResponseDTO fromEntity(Vehicle v) {
        if (v == null) return null;
        return new VehicleResponseDTO(v.getId(), v.getMake(), v.getModel(), v.getYear(), v.getVin(), v.getPrice(), v.getCreatedAt(), v.getUpdatedAt());
    }

}
