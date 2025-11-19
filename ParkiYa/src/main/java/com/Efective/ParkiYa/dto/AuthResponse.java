package com.Efective.ParkiYa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private UsuarioDTO usuario;
    private String mensaje;
}
