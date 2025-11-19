package com.Efective.ParkiYa.service;

import java.util.List;

import com.Efective.ParkiYa.dto.NotificacionDTO;

public interface NotificacionService {

    NotificacionDTO enviarNotificacion(NotificacionDTO notificacionDTO);

    List<NotificacionDTO> obtenerNotificacionesPorUsuario(String usuarioId);

    List<NotificacionDTO> obtenerNotificacionesPorReserva(String reservaId);
}
