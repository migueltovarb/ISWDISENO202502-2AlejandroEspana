package com.Efective.ParkiYa.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.Efective.ParkiYa.domain.entity.Rol;
import com.Efective.ParkiYa.domain.entity.Usuario;
import com.Efective.ParkiYa.dto.AuthRequest;
import com.Efective.ParkiYa.dto.AuthResponse;
import com.Efective.ParkiYa.dto.UsuarioDTO;
import com.Efective.ParkiYa.exception.BusinessException;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.UsuarioRepository;
import com.Efective.ParkiYa.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ParkiYaMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new BusinessException("El correo ya esta registrado");
        }
        Usuario usuario = mapper.toUsuarioEntity(usuarioDTO);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.builder()
                    .nombreRol(usuarioDTO.getTipoUsuario().name())
                    .permisos(usuarioDTO.getPermisos())
                    .build());
        }
        Usuario guardado = usuarioRepository.save(usuario);
        return mapper.toUsuarioDto(guardado);
    }

    @Override
    public AuthResponse iniciarSesion(AuthRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new BusinessException("Credenciales invalidas");
        }
        return new AuthResponse(mapper.toUsuarioDto(usuario), "Autenticacion exitosa");
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return mapper.toUsuarioDto(usuario);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(mapper::toUsuarioDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO actualizarUsuario(String id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (StringUtils.hasText(usuarioDTO.getNombre())) {
            usuario.setNombre(usuarioDTO.getNombre());
        }
        if (StringUtils.hasText(usuarioDTO.getTelefono())) {
            usuario.setTelefono(usuarioDTO.getTelefono());
        }
        if (StringUtils.hasText(usuarioDTO.getContrasena())) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        }
        if (usuarioDTO.getPermisos() != null && usuario.getRol() != null) {
            usuario.getRol().setPermisos(usuarioDTO.getPermisos());
        }
        if (usuarioDTO.getRolNombre() != null) {
            if (usuario.getRol() == null) {
                usuario.setRol(new Rol());
            }
            usuario.getRol().setNombreRol(usuarioDTO.getRolNombre());
        }
        Usuario actualizado = usuarioRepository.save(usuario);
        return mapper.toUsuarioDto(actualizado);
    }
}

