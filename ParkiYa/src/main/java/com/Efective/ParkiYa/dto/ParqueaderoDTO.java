package com.Efective.ParkiYa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParqueaderoDTO {

    private String id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String ubicacion;

    @Min(1)
    private int capacidadTotal;
}
