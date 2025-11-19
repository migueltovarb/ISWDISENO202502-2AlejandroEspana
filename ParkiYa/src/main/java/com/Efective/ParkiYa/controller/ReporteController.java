package com.Efective.ParkiYa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Efective.ParkiYa.domain.entity.enums.TipoReporte;
import com.Efective.ParkiYa.dto.ApiResponse;
import com.Efective.ParkiYa.dto.ReporteDTO;
import com.Efective.ParkiYa.service.ReporteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Validated
public class ReporteController {

    private final ReporteService reporteService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReporteDTO>> generar(@Valid @RequestBody ReporteDTO reporteDTO) {
        return ResponseEntity.ok(ApiResponse.<ReporteDTO>builder()
                .success(true)
                .message("Reporte generado")
                .data(reporteService.generarReporte(reporteDTO))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReporteDTO>> obtener(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<ReporteDTO>builder()
                .success(true)
                .data(reporteService.obtenerReporte(id))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> listar(@RequestParam(required = false) TipoReporte tipo) {
        return ResponseEntity.ok(ApiResponse.<List<ReporteDTO>>builder()
                .success(true)
                .data(reporteService.consultarReportes(Optional.ofNullable(tipo)))
                .build());
    }
}
