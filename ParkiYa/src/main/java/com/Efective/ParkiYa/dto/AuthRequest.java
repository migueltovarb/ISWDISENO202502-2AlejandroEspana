package com.Efective.ParkiYa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

    @Email
    private String correo;

    @NotBlank
    private String contrasena;
}
