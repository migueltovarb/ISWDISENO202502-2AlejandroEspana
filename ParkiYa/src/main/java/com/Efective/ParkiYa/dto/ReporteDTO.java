package com.Efective.ParkiYa.dto;

import java.time.LocalDateTime;

import com.Efective.ParkiYa.domain.entity.enums.TipoReporte;

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
public class ReporteDTO {

    private String id;

    @NotNull
    private TipoReporte tipo;

    private LocalDateTime fechaGeneracion;

    @NotBlank
    private String contenido;
}
