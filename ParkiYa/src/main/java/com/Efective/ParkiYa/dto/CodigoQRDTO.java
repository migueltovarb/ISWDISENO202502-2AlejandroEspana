package com.Efective.ParkiYa.dto;

import java.time.LocalDateTime;

import com.Efective.ParkiYa.domain.entity.enums.EstadoCodigoQR;

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
public class CodigoQRDTO {

    private String id;

    private String codigo;

    private EstadoCodigoQR estado;

    private LocalDateTime fechaGeneracion;

    private LocalDateTime fechaExpiracion;

    @NotNull
    private String reservaId;
}
