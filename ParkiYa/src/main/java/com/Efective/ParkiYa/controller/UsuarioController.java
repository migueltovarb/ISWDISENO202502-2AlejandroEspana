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
import org.springframework.web.bind.annotation.RestController;

import com.Efective.ParkiYa.dto.ApiResponse;
import com.Efective.ParkiYa.dto.AuthRequest;
import com.Efective.ParkiYa.dto.AuthResponse;
import com.Efective.ParkiYa.dto.UsuarioDTO;
import com.Efective.ParkiYa.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO registrado = usuarioService.registrarUsuario(usuarioDTO);
        return ResponseEntity.ok(ApiResponse.<UsuarioDTO>builder()
                .success(true)
                .message("Usuario registrado correctamente")
                .data(registrado)
                .build());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthResponse>> iniciarSesion(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = usuarioService.iniciarSesion(request);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .success(true)
                .message(response.getMensaje())
                .data(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listar() {
        return ResponseEntity.ok(ApiResponse.<List<UsuarioDTO>>builder()
                .success(true)
                .data(usuarioService.listarUsuarios())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> obtener(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<UsuarioDTO>builder()
                .success(true)
                .data(usuarioService.obtenerUsuarioPorId(id))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizar(@PathVariable String id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(ApiResponse.<UsuarioDTO>builder()
                .success(true)
                .message("Usuario actualizado")
                .data(usuarioService.actualizarUsuario(id, usuarioDTO))
                .build());
    }
}
