package com.Efective.ParkiYa.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTO {

    private String id;

    @NotBlank
    private String tipo;

    @Min(0)
    private double valorHora;

    @NotNull
    private LocalTime horarioInicio;

    @NotNull
    private LocalTime horarioFin;

    private boolean vigente;
}
