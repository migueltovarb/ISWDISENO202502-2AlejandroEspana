package com.Efective.ParkiYa.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Efective.ParkiYa.domain.entity.CodigoQR;
import com.Efective.ParkiYa.domain.entity.Pago;
import com.Efective.ParkiYa.domain.entity.Reserva;
import com.Efective.ParkiYa.domain.entity.enums.EstadoCodigoQR;
import com.Efective.ParkiYa.domain.entity.enums.EstadoPago;
import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.dto.CodigoQRDTO;
import com.Efective.ParkiYa.exception.BusinessException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.CodigoQRRepository;
import com.Efective.ParkiYa.repository.PagoRepository;
import com.Efective.ParkiYa.repository.ReservaRepository;
import com.Efective.ParkiYa.service.TarifaService;

@ExtendWith(MockitoExtension.class)
class CodigoQRServiceImplTest {

    @Mock
    private CodigoQRRepository codigoQRRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private TarifaService tarifaService;

    private CodigoQRServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CodigoQRServiceImpl(codigoQRRepository, reservaRepository, pagoRepository, new ParkiYaMapper(),
                tarifaService);
        lenient().when(codigoQRRepository.save(any(CodigoQR.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void validarCodigo_registraEntradaDentroDeVentana() {
        LocalDateTime horaEntrada = LocalDateTime.now().minusMinutes(1);
        Reserva reserva = Reserva.builder()
                .id("res-1")
                .horaEntrada(horaEntrada)
                .horaSalida(horaEntrada.plusHours(2))
                .duracionMinutos(120)
                .total(50)
                .estado(EstadoReserva.CONFIRMADA)
                .pagoId("pago-1")
                .build();

        CodigoQR codigoQR = CodigoQR.builder()
                .id("qr-1")
                .codigo("codigo")
                .estado(EstadoCodigoQR.ACTIVO)
                .fechaExpiracion(horaEntrada.plusMinutes(15))
                .reservaId("res-1")
                .usosRegistrados(0)
                .build();

        when(codigoQRRepository.findById("qr-1")).thenReturn(Optional.of(codigoQR));
        when(reservaRepository.findById("res-1")).thenReturn(Optional.of(reserva));
        when(pagoRepository.findById("pago-1")).thenReturn(
                Optional.of(Pago.builder().id("pago-1").estado(EstadoPago.CONFIRMADO).build()));

        CodigoQRDTO dto = service.validarCodigo("qr-1");

        assertEquals(EstadoCodigoQR.ENTRADA_VALIDADA, dto.getEstado());
        assertNotNull(dto.getFechaEntradaValidada());
        verify(codigoQRRepository, times(1)).save(argThat(actual -> actual.getFechaEntradaValidada() != null
                && actual.getEstado() == EstadoCodigoQR.ENTRADA_VALIDADA
                && actual.getUsosRegistrados() == 1));
    }

    @Test
    void validarCodigo_rechazaEntradaSiReservaNoPagada() {
        LocalDateTime horaEntrada = LocalDateTime.now().minusMinutes(1);
        Reserva reserva = Reserva.builder()
                .id("res-1")
                .horaEntrada(horaEntrada)
                .horaSalida(horaEntrada.plusHours(2))
                .duracionMinutos(120)
                .total(50)
                .estado(EstadoReserva.PENDIENTE)
                .build();

        CodigoQR codigoQR = CodigoQR.builder()
                .id("qr-1")
                .estado(EstadoCodigoQR.ACTIVO)
                .fechaExpiracion(horaEntrada.plusMinutes(15))
                .reservaId("res-1")
                .usosRegistrados(0)
                .build();

        when(codigoQRRepository.findById("qr-1")).thenReturn(Optional.of(codigoQR));
        when(reservaRepository.findById("res-1")).thenReturn(Optional.of(reserva));

        assertThrows(BusinessException.class, () -> service.validarCodigo("qr-1"));
        verify(codigoQRRepository, times(0)).save(argThat(actual -> actual.getUsosRegistrados() > 0));
    }

    @Test
    void validarCodigo_expiraCodigoDespuesDeVentana() {
        LocalDateTime horaEntrada = LocalDateTime.now().minusHours(2);
        Reserva reserva = Reserva.builder()
                .id("res-2")
                .horaEntrada(horaEntrada)
                .horaSalida(horaEntrada.plusHours(2))
                .duracionMinutos(120)
                .total(50)
                .build();

        CodigoQR codigoQR = CodigoQR.builder()
                .id("qr-2")
                .estado(EstadoCodigoQR.ACTIVO)
                .fechaExpiracion(horaEntrada.plusMinutes(15))
                .reservaId("res-2")
                .usosRegistrados(0)
                .build();

        when(codigoQRRepository.findById("qr-2")).thenReturn(Optional.of(codigoQR));
        when(reservaRepository.findById("res-2")).thenReturn(Optional.of(reserva));

        assertThrows(BusinessException.class, () -> service.validarCodigo("qr-2"));

        verify(codigoQRRepository, times(1))
                .save(argThat(actual -> actual.getEstado() == EstadoCodigoQR.EXPIRADO));
    }

    @Test
    void validarCodigo_registraSalidaYAplciaTarifaExtra() {
        LocalDateTime entradaReal = LocalDateTime.now().minusHours(3);
        LocalDateTime horaEntradaProgramada = entradaReal.minusMinutes(5);
        LocalDateTime horaSalidaProgramada = horaEntradaProgramada.plusHours(2);

        Reserva reserva = Reserva.builder()
                .id("res-3")
                .horaEntrada(horaEntradaProgramada)
                .horaSalida(horaSalidaProgramada)
                .duracionMinutos(120)
                .total(100)
                .tarifaId("tarifa-1")
                .pagoId("pago-1")
                .estado(EstadoReserva.CONFIRMADA)
                .build();

        CodigoQR codigoQR = CodigoQR.builder()
                .id("qr-3")
                .estado(EstadoCodigoQR.ENTRADA_VALIDADA)
                .fechaEntradaValidada(entradaReal)
                .reservaId("res-3")
                .usosRegistrados(1)
                .build();

        when(codigoQRRepository.findById("qr-3")).thenReturn(Optional.of(codigoQR));
        when(reservaRepository.findById("res-3")).thenReturn(Optional.of(reserva));
        when(tarifaService.calcularCosto("tarifa-1", 120)).thenReturn(100.0);
        when(tarifaService.calcularCosto("tarifa-1", 180)).thenReturn(150.0);
        CodigoQRDTO dto = service.validarCodigo("qr-3");

        assertEquals(EstadoCodigoQR.DESACTIVADO, dto.getEstado());
        assertNotNull(dto.getFechaSalidaValidada());
        assertEquals(2, dto.getUsosRegistrados());

        ArgumentCaptor<Reserva> reservaCaptor = ArgumentCaptor.forClass(Reserva.class);
        verify(reservaRepository).save(reservaCaptor.capture());
        Reserva actualizada = reservaCaptor.getValue();
        assertEquals(180, actualizada.getMinutosConsumidos());
        assertEquals(50.0, actualizada.getCargoAdicional());
        assertEquals(150.0, actualizada.getTotal());
        assertEquals(EstadoReserva.COMPLETADA, actualizada.getEstado());
    }
}
