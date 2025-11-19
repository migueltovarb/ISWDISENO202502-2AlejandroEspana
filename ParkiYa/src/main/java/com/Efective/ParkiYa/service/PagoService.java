package com.Efective.ParkiYa.service;

import java.util.List;

import com.Efective.ParkiYa.dto.PagoDTO;

public interface PagoService {

    PagoDTO registrarPago(PagoDTO pagoDTO);

    PagoDTO procesarPago(String pagoId);

    PagoDTO confirmarPago(String pagoId);

    List<PagoDTO> obtenerPagosPorReserva(String reservaId);
}
