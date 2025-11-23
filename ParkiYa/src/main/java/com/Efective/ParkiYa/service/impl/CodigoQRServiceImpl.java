package com.Efective.ParkiYa.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.CodigoQR;
import com.Efective.ParkiYa.domain.entity.Pago;
import com.Efective.ParkiYa.domain.entity.Reserva;
import com.Efective.ParkiYa.domain.entity.enums.EstadoCodigoQR;
import com.Efective.ParkiYa.domain.entity.enums.EstadoPago;
import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.dto.CodigoQRDTO;
import com.Efective.ParkiYa.exception.BusinessException;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.CodigoQRRepository;
import com.Efective.ParkiYa.repository.PagoRepository;
import com.Efective.ParkiYa.repository.ReservaRepository;
import com.Efective.ParkiYa.service.CodigoQRService;
import com.Efective.ParkiYa.service.TarifaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodigoQRServiceImpl implements CodigoQRService {

    private static final long MINUTOS_ANTICIPACION_ENTRADA = 15;
    private static final long MINUTOS_GRACIA_ENTRADA = 15;
    private static final long MINUTOS_GRACIA_SALIDA = 10;

    private final CodigoQRRepository codigoQRRepository;
    private final ReservaRepository reservaRepository;
    private final PagoRepository pagoRepository;
    private final ParkiYaMapper mapper;
    private final TarifaService tarifaService;

    @Override
    public CodigoQRDTO generarCodigo(String reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        LocalDateTime horaEntrada = reserva.getHoraEntrada();
        if (horaEntrada == null) {
            throw new BusinessException("La reserva no tiene una hora de entrada definida");
        }
        LocalDateTime ahora = LocalDateTime.now();
        CodigoQR codigoQR = CodigoQR.builder()
                .codigo(UUID.randomUUID().toString())
                .estado(EstadoCodigoQR.ACTIVO)
                .fechaGeneracion(ahora)
                .fechaExpiracion(horaEntrada.plusMinutes(MINUTOS_GRACIA_ENTRADA))
                .reservaId(reservaId)
                .usosRegistrados(0)
                .build();
        CodigoQR guardado = codigoQRRepository.save(codigoQR);
        reserva.setCodigoQrId(guardado.getId());
        reservaRepository.save(reserva);
        return mapper.toCodigoDto(guardado);
    }

    @Override
    public CodigoQRDTO validarCodigo(String codigoId) {
        CodigoQR codigoQR = obtenerCodigo(codigoId);
        Reserva reserva = reservaRepository.findById(codigoQR.getReservaId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva asociada no encontrada"));

        LocalDateTime ahora = LocalDateTime.now();
        expirarSiNoSeIngresoATiempo(codigoQR, reserva, ahora);

        if (codigoQR.getEstado() == EstadoCodigoQR.EXPIRADO || codigoQR.getEstado() == EstadoCodigoQR.DESACTIVADO) {
            throw new BusinessException("El codigo QR ya no se encuentra activo");
        }

        CodigoQR actualizado;
        if (codigoQR.getFechaEntradaValidada() == null) {
            validarVentanaEntrada(reserva, codigoQR, ahora);
            validarPagoReserva(reserva);
            registrarEntrada(codigoQR, reserva, ahora);
            actualizado = codigoQRRepository.save(codigoQR);
        } else if (codigoQR.getFechaSalidaValidada() == null) {
            registrarSalida(reserva, codigoQR, ahora);
            actualizado = codigoQRRepository.save(codigoQR);
        } else {
            throw new BusinessException("El codigo QR ya se utilizo para entrada y salida");
        }

        return mapper.toCodigoDto(actualizado);
    }

    @Override
    public CodigoQRDTO obtenerPorReserva(String reservaId) {
        CodigoQR codigoQR = codigoQRRepository.findByReservaId(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Codigo QR no encontrado para la reserva"));
        reservaRepository.findById(reservaId).ifPresent(reserva -> expirarSiNoSeIngresoATiempo(codigoQR, reserva, LocalDateTime.now()));
        return mapper.toCodigoDto(codigoQR);
    }

    private CodigoQR obtenerCodigo(String codigoId) {
        return codigoQRRepository.findById(codigoId)
                .orElseThrow(() -> new ResourceNotFoundException("Codigo QR no encontrado"));
    }

    private void expirarSiNoSeIngresoATiempo(CodigoQR codigoQR, Reserva reserva, LocalDateTime ahora) {
        if (codigoQR.getFechaEntradaValidada() != null) {
            return;
        }
        LocalDateTime limite = obtenerLimiteEntrada(reserva);
        if (limite != null && ahora.isAfter(limite)) {
            codigoQR.setEstado(EstadoCodigoQR.EXPIRADO);
            codigoQR.setFechaExpiracion(limite);
            codigoQRRepository.save(codigoQR);
        }
    }

    private void validarVentanaEntrada(Reserva reserva, CodigoQR codigoQR, LocalDateTime ahora) {
        LocalDateTime horaEntrada = reserva.getHoraEntrada();
        if (horaEntrada == null) {
            throw new BusinessException("No hay hora de entrada configurada para la reserva");
        }
        LocalDateTime inicioPermitido = horaEntrada.minusMinutes(MINUTOS_ANTICIPACION_ENTRADA);
        LocalDateTime limite = horaEntrada.plusMinutes(MINUTOS_GRACIA_ENTRADA);
        if (ahora.isBefore(inicioPermitido)) {
            throw new BusinessException("El ingreso se intento antes de la ventana permitida");
        }
        if (ahora.isAfter(limite)) {
            codigoQR.setEstado(EstadoCodigoQR.EXPIRADO);
            codigoQR.setFechaExpiracion(limite);
            codigoQRRepository.save(codigoQR);
            throw new BusinessException("La ventana de ingreso ya expiro");
        }
    }

    private void registrarEntrada(CodigoQR codigoQR, Reserva reserva, LocalDateTime ahora) {
        codigoQR.setFechaEntradaValidada(ahora);
        codigoQR.setEstado(EstadoCodigoQR.ENTRADA_VALIDADA);
        codigoQR.setUsosRegistrados(codigoQR.getUsosRegistrados() + 1);
        LocalDateTime horaSalida = reserva.getHoraSalida();
        LocalDateTime nuevaExpiracion = horaSalida == null
                ? ahora.plusMinutes(MINUTOS_GRACIA_SALIDA)
                : horaSalida.plusMinutes(MINUTOS_GRACIA_SALIDA);
        codigoQR.setFechaExpiracion(nuevaExpiracion);
        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            reserva.setEstado(EstadoReserva.CONFIRMADA);
            reservaRepository.save(reserva);
        }
    }

    private void registrarSalida(Reserva reserva, CodigoQR codigoQR, LocalDateTime ahora) {
        if (codigoQR.getFechaEntradaValidada() == null) {
            throw new BusinessException("El ingreso no ha sido validado para este codigo");
        }
        codigoQR.setFechaSalidaValidada(ahora);
        codigoQR.setEstado(EstadoCodigoQR.DESACTIVADO);
        codigoQR.setUsosRegistrados(codigoQR.getUsosRegistrados() + 1);
        codigoQR.setFechaExpiracion(ahora);
        reserva.setEstado(EstadoReserva.COMPLETADA);
        actualizarConsumosYTarifa(reserva, codigoQR.getFechaEntradaValidada(), ahora);
    }

    private void actualizarConsumosYTarifa(Reserva reserva, LocalDateTime entradaReal, LocalDateTime salidaReal) {
        long minutosConsumidos = Math.max(Duration.between(entradaReal, salidaReal).toMinutes(), 0);
        long minutosReservados = obtenerDuracionReservada(reserva);
        reserva.setMinutosConsumidos(minutosConsumidos);

        double cargoExtra = 0;
        double base = reserva.getTotal();
        if (minutosReservados > 0 && minutosConsumidos > minutosReservados) {
            if (reserva.getTarifaId() != null) {
                double costoReservado = tarifaService.calcularCosto(reserva.getTarifaId(), minutosReservados);
                double costoReal = tarifaService.calcularCosto(reserva.getTarifaId(), minutosConsumidos);
                cargoExtra = Math.max(0, costoReal - costoReservado);
                base = costoReservado;
            } else if (base > 0) {
                double costoMinuto = base / minutosReservados;
                cargoExtra = costoMinuto * (minutosConsumidos - minutosReservados);
            }
        }

        cargoExtra = redondear(cargoExtra);
        reserva.setCargoAdicional(cargoExtra);
        if (cargoExtra > 0) {
            reserva.setTotal(redondear(base + cargoExtra));
        }
        reservaRepository.save(reserva);
    }

    private long obtenerDuracionReservada(Reserva reserva) {
        if (reserva.getDuracionMinutos() > 0) {
            return reserva.getDuracionMinutos();
        }
        if (reserva.getHoraEntrada() != null && reserva.getHoraSalida() != null) {
            return Math.max(Duration.between(reserva.getHoraEntrada(), reserva.getHoraSalida()).toMinutes(), 0);
        }
        return 0;
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    private LocalDateTime obtenerLimiteEntrada(Reserva reserva) {
        return reserva.getHoraEntrada() == null ? null : reserva.getHoraEntrada().plusMinutes(MINUTOS_GRACIA_ENTRADA);
    }

    private void validarPagoReserva(Reserva reserva) {
        if (reserva.getPagoId() == null) {
            throw new BusinessException("La reserva aun no esta pagada");
        }
        Pago pago = pagoRepository.findById(reserva.getPagoId())
                .orElseThrow(() -> new BusinessException("Pago no encontrado para la reserva"));
        if (pago.getEstado() != EstadoPago.CONFIRMADO) {
            throw new BusinessException("La reserva aun no esta pagada");
        }
        if (reserva.getEstado() != EstadoReserva.CONFIRMADA && reserva.getEstado() != EstadoReserva.COMPLETADA) {
            reserva.setEstado(EstadoReserva.CONFIRMADA);
            reservaRepository.save(reserva);
        }
    }
}
