package com.Efective.ParkiYa.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private String id;

    private LocalDate fecha;

    @NotNull
    private LocalDateTime horaEntrada;

    @NotNull
    private LocalDateTime horaSalida;

    private EstadoReserva estado;

    private long duracionMinutos;

    private double total;

    @NotNull
    private String clienteId;

    private String tarifaId;

    private String codigoQrId;

    private String pagoId;
}
