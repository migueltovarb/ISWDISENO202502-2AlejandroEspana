package com.Efective.ParkiYa.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.dto.ApiResponse;
import com.Efective.ParkiYa.dto.ReservaDTO;
import com.Efective.ParkiYa.service.ReservaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Validated
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservaDTO>> crear(@Valid @RequestBody ReservaDTO reservaDTO) {
        return ResponseEntity.ok(ApiResponse.<ReservaDTO>builder()
                .success(true)
                .message("Reserva creada")
                .data(reservaService.crearReserva(reservaDTO))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservaDTO>>> listar(
            @RequestParam(name = "fecha", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<ReservaDTO> reservas = fecha == null
                ? reservaService.obtenerReservas()
                : reservaService.obtenerReservasPorFecha(fecha);
        return ResponseEntity.ok(ApiResponse.<List<ReservaDTO>>builder()
                .success(true)
                .data(reservas)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservaDTO>> obtener(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<ReservaDTO>builder()
                .success(true)
                .data(reservaService.obtenerReserva(id))
                .build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<ReservaDTO>>> porCliente(@PathVariable String clienteId) {
        return ResponseEntity.ok(ApiResponse.<List<ReservaDTO>>builder()
                .success(true)
                .data(reservaService.obtenerReservasPorUsuario(clienteId))
                .build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<ReservaDTO>> actualizarEstado(@PathVariable String id,
            @RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(ApiResponse.<ReservaDTO>builder()
                .success(true)
                .message("Estado actualizado")
                .data(reservaService.actualizarEstado(id, estado))
                .build());
    }
}
