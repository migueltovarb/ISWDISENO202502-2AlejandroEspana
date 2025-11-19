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
import com.Efective.ParkiYa.dto.NotificacionDTO;
import com.Efective.ParkiYa.service.NotificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Validated
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificacionDTO>> enviar(@Valid @RequestBody NotificacionDTO notificacionDTO) {
        return ResponseEntity.ok(ApiResponse.<NotificacionDTO>builder()
                .success(true)
                .message("Notificacion enviada")
                .data(notificacionService.enviarNotificacion(notificacionDTO))
                .build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse<List<NotificacionDTO>>> porUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(ApiResponse.<List<NotificacionDTO>>builder()
                .success(true)
                .data(notificacionService.obtenerNotificacionesPorUsuario(usuarioId))
                .build());
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<ApiResponse<List<NotificacionDTO>>> porReserva(@PathVariable String reservaId) {
        return ResponseEntity.ok(ApiResponse.<List<NotificacionDTO>>builder()
                .success(true)
                .data(notificacionService.obtenerNotificacionesPorReserva(reservaId))
                .build());
    }
}
