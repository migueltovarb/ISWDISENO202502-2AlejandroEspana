package com.Efective.ParkiYa.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.Efective.ParkiYa.domain.entity.enums.EstadoNotificacion;
import com.Efective.ParkiYa.domain.entity.enums.TipoNotificacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notificaciones")
public abstract class Notificacion {

    @Id
    private String id;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private TipoNotificacion tipo;
    private EstadoNotificacion estado;
    private String usuarioId;
    private String reservaId;
}
