package com.Efective.ParkiYa.dto;

import java.time.LocalDateTime;

import com.Efective.ParkiYa.domain.entity.enums.EstadoNotificacion;
import com.Efective.ParkiYa.domain.entity.enums.TipoNotificacion;

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
public class NotificacionDTO {

    private String id;

    @NotBlank
    private String mensaje;

    @NotNull
    private TipoNotificacion tipo;

    private EstadoNotificacion estado;

    private LocalDateTime fechaEnvio;

    private String usuarioId;

    private String reservaId;

    private String correoDestino;

    private String idUsuarioDestino;

    private String numeroTelefono;
}
