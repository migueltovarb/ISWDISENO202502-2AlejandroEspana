package com.Efective.ParkiYa.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteParqueaderoDTO {

    private String parqueaderoId;
    private LocalDate fecha;
    private int capacidadTotal;
    private long reservasConfirmadas;
    private double porcentajeOcupacion;
    private double facturacionTotal;
}
