package com.Efective.ParkiYa.service;

import com.Efective.ParkiYa.dto.CodigoQRDTO;

public interface CodigoQRService {

    CodigoQRDTO generarCodigo(String reservaId);

    CodigoQRDTO validarCodigo(String codigoId);

    CodigoQRDTO obtenerPorReserva(String reservaId);
}
