package com.Efective.ParkiYa.service;

import java.util.List;

import com.Efective.ParkiYa.dto.AuthRequest;
import com.Efective.ParkiYa.dto.AuthResponse;
import com.Efective.ParkiYa.dto.UsuarioDTO;

public interface UsuarioService {

    UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO);

    AuthResponse iniciarSesion(AuthRequest request);

    UsuarioDTO obtenerUsuarioPorId(String id);

    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO actualizarUsuario(String id, UsuarioDTO usuarioDTO);
}
