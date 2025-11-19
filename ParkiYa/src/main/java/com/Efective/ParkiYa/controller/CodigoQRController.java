package com.Efective.ParkiYa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Efective.ParkiYa.dto.ApiResponse;
import com.Efective.ParkiYa.dto.CodigoQRDTO;
import com.Efective.ParkiYa.service.CodigoQRService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/codigos-qr")
@RequiredArgsConstructor
@Validated
public class CodigoQRController {

    private final CodigoQRService codigoQRService;

    @PostMapping
    public ResponseEntity<ApiResponse<CodigoQRDTO>> generar(@RequestParam String reservaId) {
        return ResponseEntity.ok(ApiResponse.<CodigoQRDTO>builder()
                .success(true)
                .message("Codigo QR generado")
                .data(codigoQRService.generarCodigo(reservaId))
                .build());
    }

    @PostMapping("/{codigoId}/validar")
    public ResponseEntity<ApiResponse<CodigoQRDTO>> validar(@PathVariable String codigoId) {
        return ResponseEntity.ok(ApiResponse.<CodigoQRDTO>builder()
                .success(true)
                .message("Validacion ejecutada")
                .data(codigoQRService.validarCodigo(codigoId))
                .build());
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ApiResponse<CodigoQRDTO>> obtenerPorReserva(@PathVariable String reservaId) {
        return ResponseEntity.ok(ApiResponse.<CodigoQRDTO>builder()
                .success(true)
                .data(codigoQRService.obtenerPorReserva(reservaId))
                .build());
    }
}
