package com.Efective.ParkiYa.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.Reserva;
import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.domain.entity.enums.TipoNotificacion;
import com.Efective.ParkiYa.dto.CodigoQRDTO;
import com.Efective.ParkiYa.dto.NotificacionDTO;
import com.Efective.ParkiYa.dto.ReservaDTO;
import com.Efective.ParkiYa.exception.BusinessException;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.ReservaRepository;
import com.Efective.ParkiYa.service.CodigoQRService;
import com.Efective.ParkiYa.service.NotificacionService;
import com.Efective.ParkiYa.service.ReservaService;
import com.Efective.ParkiYa.service.TarifaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ParkiYaMapper mapper;
    private final TarifaService tarifaService;
    private final CodigoQRService codigoQRService;
    private final NotificacionService notificacionService;

    @Override
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        if (reservaDTO.getHoraSalida().isBefore(reservaDTO.getHoraEntrada())) {
            throw new BusinessException("La hora de salida debe ser posterior a la hora de entrada");
        }
        Reserva reserva = mapper.toReservaEntity(reservaDTO);
        reserva.setFecha(reservaDTO.getFecha() == null ? reserva.getHoraEntrada().toLocalDate() : reservaDTO.getFecha());
        reserva.setEstado(reserva.getEstado() == null ? EstadoReserva.PENDIENTE : reserva.getEstado());
        long minutos = Duration.between(reserva.getHoraEntrada(), reserva.getHoraSalida()).toMinutes();
        reserva.setDuracionMinutos(Math.max(minutos, 0));
        if (reserva.getTarifaId() != null) {
            reserva.setTotal(tarifaService.calcularCosto(reserva.getTarifaId(), reserva.getDuracionMinutos()));
        }
        Reserva guardada = reservaRepository.save(reserva);
        CodigoQRDTO codigoQRDTO = codigoQRService.generarCodigo(guardada.getId());
        guardada.setCodigoQrId(codigoQRDTO.getId());
        Reserva actualizada = reservaRepository.save(guardada);
        notificacionService.enviarNotificacion(NotificacionDTO.builder()
                .mensaje("Reserva creada con codigo QR " + codigoQRDTO.getCodigo())
                .tipo(TipoNotificacion.APP)
                .usuarioId(guardada.getClienteId())
                .reservaId(guardada.getId())
                .build());
        return mapper.toReservaDto(actualizada);
    }

    @Override
    public ReservaDTO actualizarEstado(String reservaId, EstadoReserva estado) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        reserva.setEstado(estado);
        Reserva actualizada = reservaRepository.save(reserva);
        return mapper.toReservaDto(actualizada);
    }

    @Override
    public List<ReservaDTO> obtenerReservas() {
        return reservaRepository.findAll().stream()
                .map(mapper::toReservaDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorUsuario(String clienteId) {
        return reservaRepository.findByClienteId(clienteId).stream()
                .map(mapper::toReservaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO obtenerReserva(String id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return mapper.toReservaDto(reserva);
    }

    @Override
    public List<ReservaDTO> obtenerReservasPorFecha(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha).stream()
                .map(mapper::toReservaDto)
                .collect(Collectors.toList());
    }
}
