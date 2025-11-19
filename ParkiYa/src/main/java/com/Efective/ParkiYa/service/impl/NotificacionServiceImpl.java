package com.Efective.ParkiYa.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.Notificacion;
import com.Efective.ParkiYa.domain.entity.enums.EstadoNotificacion;
import com.Efective.ParkiYa.dto.NotificacionDTO;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.NotificacionRepository;
import com.Efective.ParkiYa.service.NotificacionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final ParkiYaMapper mapper;

    @Override
    public NotificacionDTO enviarNotificacion(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = mapper.toNotificacionEntity(notificacionDTO);
        notificacion.setEstado(EstadoNotificacion.PENDIENTE);
        notificacion.setFechaEnvio(LocalDateTime.now());
        Notificacion guardada = notificacionRepository.save(notificacion);
        guardada.setEstado(EstadoNotificacion.ENVIADA);
        Notificacion enviada = notificacionRepository.save(guardada);
        return mapper.toNotificacionDto(enviada);
    }

    @Override
    public List<NotificacionDTO> obtenerNotificacionesPorUsuario(String usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toNotificacionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionDTO> obtenerNotificacionesPorReserva(String reservaId) {
        return notificacionRepository.findByReservaId(reservaId).stream()
                .map(mapper::toNotificacionDto)
                .collect(Collectors.toList());
    }
}
