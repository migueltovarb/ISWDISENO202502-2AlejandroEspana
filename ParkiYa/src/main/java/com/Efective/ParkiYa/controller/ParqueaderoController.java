package com.Efective.ParkiYa.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Efective.ParkiYa.dto.ApiResponse;
import com.Efective.ParkiYa.dto.DisponibilidadParqueaderoDTO;
import com.Efective.ParkiYa.dto.ParqueaderoDTO;
import com.Efective.ParkiYa.dto.ReporteParqueaderoDTO;
import com.Efective.ParkiYa.service.ParqueaderoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parqueaderos")
@RequiredArgsConstructor
@Validated
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;

    @PostMapping
    public ResponseEntity<ApiResponse<ParqueaderoDTO>> crear(@Valid @RequestBody ParqueaderoDTO dto) {
        return ResponseEntity.ok(ApiResponse.<ParqueaderoDTO>builder()
                .success(true)
                .message("Parqueadero creado")
                .data(parqueaderoService.crearParqueadero(dto))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ParqueaderoDTO>> actualizar(@PathVariable String id,
            @Valid @RequestBody ParqueaderoDTO dto) {
        return ResponseEntity.ok(ApiResponse.<ParqueaderoDTO>builder()
                .success(true)
                .message("Parqueadero actualizado")
                .data(parqueaderoService.actualizarParqueadero(id, dto))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ParqueaderoDTO>>> listar() {
        return ResponseEntity.ok(ApiResponse.<List<ParqueaderoDTO>>builder()
                .success(true)
                .data(parqueaderoService.obtenerParqueaderos())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParqueaderoDTO>> obtener(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<ParqueaderoDTO>builder()
                .success(true)
                .data(parqueaderoService.obtenerParqueadero(id))
                .build());
    }

    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<ApiResponse<DisponibilidadParqueaderoDTO>> disponibilidad(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.<DisponibilidadParqueaderoDTO>builder()
                .success(true)
                .data(parqueaderoService.consultarDisponibilidad(id, fecha))
                .build());
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<ApiResponse<ReporteParqueaderoDTO>> reporte(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.<ReporteParqueaderoDTO>builder()
                .success(true)
                .data(parqueaderoService.generarReporteOcupacion(id, fecha))
                .build());
    }
}
