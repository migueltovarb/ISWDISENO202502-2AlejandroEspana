package com.Efective.ParkiYa.dto;

import java.util.List;

import com.Efective.ParkiYa.domain.entity.enums.TipoUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String id;

    @NotBlank
    private String nombre;

    @Email
    private String correo;

    private String telefono;

    @NotNull
    private TipoUsuario tipoUsuario;

    private String contrasena;

    private String rolNombre;

    private List<String> permisos;

    private String permisosEspeciales;

    private String areaControl;

    private String metodoPagoPreferido;
}
