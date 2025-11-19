package com.Efective.ParkiYa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Efective.ParkiYa.dto.ApiResponse;
import com.Efective.ParkiYa.dto.PagoDTO;
import com.Efective.ParkiYa.service.PagoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Validated
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<ApiResponse<PagoDTO>> registrar(@Valid @RequestBody PagoDTO pagoDTO) {
        return ResponseEntity.ok(ApiResponse.<PagoDTO>builder()
                .success(true)
                .message("Pago registrado")
                .data(pagoService.registrarPago(pagoDTO))
                .build());
    }

    @PostMapping("/{id}/procesar")
    public ResponseEntity<ApiResponse<PagoDTO>> procesar(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<PagoDTO>builder()
                .success(true)
                .message("Pago en proceso")
                .data(pagoService.procesarPago(id))
                .build());
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<ApiResponse<PagoDTO>> confirmar(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<PagoDTO>builder()
                .success(true)
                .message("Pago confirmado")
                .data(pagoService.confirmarPago(id))
                .build());
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ApiResponse<List<PagoDTO>>> pagosPorReserva(@PathVariable String reservaId) {
        return ResponseEntity.ok(ApiResponse.<List<PagoDTO>>builder()
                .success(true)
                .data(pagoService.obtenerPagosPorReserva(reservaId))
                .build());
    }
}
