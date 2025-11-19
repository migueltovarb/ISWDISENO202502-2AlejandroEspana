package com.Efective.ParkiYa.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.CodigoQR;
import com.Efective.ParkiYa.domain.entity.enums.EstadoCodigoQR;
import com.Efective.ParkiYa.dto.CodigoQRDTO;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.CodigoQRRepository;
import com.Efective.ParkiYa.repository.ReservaRepository;
import com.Efective.ParkiYa.service.CodigoQRService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodigoQRServiceImpl implements CodigoQRService {

    private final CodigoQRRepository codigoQRRepository;
    private final ReservaRepository reservaRepository;
    private final ParkiYaMapper mapper;

    @Override
    public CodigoQRDTO generarCodigo(String reservaId) {
        reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        CodigoQR codigoQR = CodigoQR.builder()
                .codigo(UUID.randomUUID().toString())
                .estado(EstadoCodigoQR.ACTIVO)
                .fechaGeneracion(LocalDateTime.now())
                .fechaExpiracion(LocalDateTime.now().plusHours(12))
                .reservaId(reservaId)
                .build();
        CodigoQR guardado = codigoQRRepository.save(codigoQR);
        reservaRepository.findById(reservaId).ifPresent(reserva -> {
            reserva.setCodigoQrId(guardado.getId());
            reservaRepository.save(reserva);
        });
        return mapper.toCodigoDto(guardado);
    }

    @Override
    public CodigoQRDTO validarCodigo(String codigoId) {
        CodigoQR codigoQR = codigoQRRepository.findById(codigoId)
                .orElseThrow(() -> new ResourceNotFoundException("Codigo QR no encontrado"));
        if (codigoQR.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            codigoQR.setEstado(EstadoCodigoQR.EXPIRADO);
        }
        return mapper.toCodigoDto(codigoQRRepository.save(codigoQR));
    }

    @Override
    public CodigoQRDTO obtenerPorReserva(String reservaId) {
        CodigoQR codigoQR = codigoQRRepository.findByReservaId(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Codigo QR no encontrado para la reserva"));
        return mapper.toCodigoDto(codigoQR);
    }
}
