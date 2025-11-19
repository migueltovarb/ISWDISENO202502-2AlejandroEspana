package com.Efective.ParkiYa.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.Pago;
import com.Efective.ParkiYa.domain.entity.Reserva;
import com.Efective.ParkiYa.domain.entity.enums.EstadoPago;
import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.dto.PagoDTO;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.PagoRepository;
import com.Efective.ParkiYa.repository.ReservaRepository;
import com.Efective.ParkiYa.service.PagoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final ReservaRepository reservaRepository;
    private final ParkiYaMapper mapper;

    @Override
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        Pago pago = mapper.toPagoEntity(pagoDTO);
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setFechaPago(LocalDateTime.now());
        Pago guardado = pagoRepository.save(pago);
        actualizarReservaConPago(guardado.getReservaId(), guardado.getId());
        return mapper.toPagoDto(guardado);
    }

    @Override
    public PagoDTO procesarPago(String pagoId) {
        Pago pago = obtenerPago(pagoId);
        pago.setEstado(EstadoPago.PROCESANDO);
        pago.setFechaPago(LocalDateTime.now());
        return mapper.toPagoDto(pagoRepository.save(pago));
    }

    @Override
    public PagoDTO confirmarPago(String pagoId) {
        Pago pago = obtenerPago(pagoId);
        pago.setEstado(EstadoPago.CONFIRMADO);
        pago.setFechaPago(LocalDateTime.now());
        Pago confirmado = pagoRepository.save(pago);
        reservaRepository.findById(pago.getReservaId()).ifPresent(reserva -> {
            reserva.setEstado(EstadoReserva.CONFIRMADA);
            reservaRepository.save(reserva);
        });
        return mapper.toPagoDto(confirmado);
    }

    @Override
    public List<PagoDTO> obtenerPagosPorReserva(String reservaId) {
        return pagoRepository.findByReservaId(reservaId).stream()
                .map(mapper::toPagoDto)
                .collect(Collectors.toList());
    }

    private Pago obtenerPago(String pagoId) {
        return pagoRepository.findById(pagoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
    }

    private void actualizarReservaConPago(String reservaId, String pagoId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada para el pago"));
        reserva.setPagoId(pagoId);
        reservaRepository.save(reserva);
    }
}
