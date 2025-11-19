package com.Efective.ParkiYa.controller;

import java.util.List;

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
import com.Efective.ParkiYa.dto.TarifaDTO;
import com.Efective.ParkiYa.service.TarifaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tarifas")
@RequiredArgsConstructor
@Validated
public class TarifaController {

    private final TarifaService tarifaService;

    @PostMapping
    public ResponseEntity<ApiResponse<TarifaDTO>> crear(@Valid @RequestBody TarifaDTO tarifaDTO) {
        return ResponseEntity.ok(ApiResponse.<TarifaDTO>builder()
                .success(true)
                .message("Tarifa creada")
                .data(tarifaService.crearTarifa(tarifaDTO))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TarifaDTO>> actualizar(@PathVariable String id,
            @Valid @RequestBody TarifaDTO tarifaDTO) {
        return ResponseEntity.ok(ApiResponse.<TarifaDTO>builder()
                .success(true)
                .message("Tarifa actualizada")
                .data(tarifaService.actualizarTarifa(id, tarifaDTO))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TarifaDTO>>> listarVigentes() {
        return ResponseEntity.ok(ApiResponse.<List<TarifaDTO>>builder()
                .success(true)
                .data(tarifaService.obtenerTarifasVigentes())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TarifaDTO>> obtener(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<TarifaDTO>builder()
                .success(true)
                .data(tarifaService.obtenerTarifa(id))
                .build());
    }

    @GetMapping("/{id}/calcular")
    public ResponseEntity<ApiResponse<Double>> calcular(@PathVariable String id, @RequestParam long minutos) {
        return ResponseEntity.ok(ApiResponse.<Double>builder()
                .success(true)
                .message("Costo calculado")
                .data(tarifaService.calcularCosto(id, minutos))
                .build());
    }
}
