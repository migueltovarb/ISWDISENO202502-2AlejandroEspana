package com.Efective.ParkiYa.dto;

import java.time.LocalDateTime;

import com.Efective.ParkiYa.domain.entity.enums.EstadoPago;

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
public class PagoDTO {

    private String id;

    @NotBlank
    private String metodo;

    @Min(0)
    private double monto;

    private LocalDateTime fechaPago;

    private EstadoPago estado;

    @NotNull
    private String reservaId;

    private String referenciaTransaccion;
}
