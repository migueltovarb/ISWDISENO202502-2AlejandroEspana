package com.Efective.ParkiYa.domain.entity;

import org.springframework.data.annotation.TypeAlias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TypeAlias("notificacion_correo")
public class NotificacionCorreo extends Notificacion {

    private String correoDestino;
}

