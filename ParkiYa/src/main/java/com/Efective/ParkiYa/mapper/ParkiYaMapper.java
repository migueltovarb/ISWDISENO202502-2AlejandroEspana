package com.Efective.ParkiYa.mapper;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.Efective.ParkiYa.domain.entity.Administrador;
import com.Efective.ParkiYa.domain.entity.Cliente;
import com.Efective.ParkiYa.domain.entity.CodigoQR;
import com.Efective.ParkiYa.domain.entity.Notificacion;
import com.Efective.ParkiYa.domain.entity.NotificacionApp;
import com.Efective.ParkiYa.domain.entity.NotificacionCorreo;
import com.Efective.ParkiYa.domain.entity.NotificacionSMS;
import com.Efective.ParkiYa.domain.entity.Operador;
import com.Efective.ParkiYa.domain.entity.Pago;
import com.Efective.ParkiYa.domain.entity.Parqueadero;
import com.Efective.ParkiYa.domain.entity.Reporte;
import com.Efective.ParkiYa.domain.entity.Reserva;
import com.Efective.ParkiYa.domain.entity.Rol;
import com.Efective.ParkiYa.domain.entity.Tarifa;
import com.Efective.ParkiYa.domain.entity.Usuario;
import com.Efective.ParkiYa.domain.entity.enums.TipoNotificacion;
import com.Efective.ParkiYa.dto.CodigoQRDTO;
import com.Efective.ParkiYa.dto.NotificacionDTO;
import com.Efective.ParkiYa.dto.PagoDTO;
import com.Efective.ParkiYa.dto.ParqueaderoDTO;
import com.Efective.ParkiYa.dto.ReporteDTO;
import com.Efective.ParkiYa.dto.ReservaDTO;
import com.Efective.ParkiYa.dto.TarifaDTO;
import com.Efective.ParkiYa.dto.UsuarioDTO;

@Component
public class ParkiYaMapper {

    public UsuarioDTO toUsuarioDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO.UsuarioDTOBuilder builder = UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .telefono(usuario.getTelefono())
                .tipoUsuario(usuario.getTipoUsuario());

        Optional.ofNullable(usuario.getRol()).ifPresent(rol -> {
            builder.rolNombre(rol.getNombreRol());
            builder.permisos(rol.getPermisos());
        });

        if (usuario instanceof Cliente cliente) {
            builder.metodoPagoPreferido(cliente.getMetodoPagoPreferido());
        }
        if (usuario instanceof Administrador administrador) {
            builder.permisosEspeciales(administrador.getPermisosEspeciales());
        }
        if (usuario instanceof Operador operador) {
            builder.areaControl(operador.getAreaControl());
        }
        return builder.build();
    }

    public Usuario toUsuarioEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario;
        switch (dto.getTipoUsuario()) {
            case CLIENTE -> usuario = new Cliente();
            case ADMINISTRADOR -> usuario = new Administrador();
            case OPERADOR -> usuario = new Operador();
            default -> usuario = new Usuario();
        }
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setContrasena(dto.getContrasena());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        if (dto.getRolNombre() != null) {
            usuario.setRol(Rol.builder()
                    .nombreRol(dto.getRolNombre())
                    .permisos(dto.getPermisos() == null ? Collections.emptyList() : dto.getPermisos())
                    .build());
        }
        if (usuario instanceof Cliente cliente) {
            cliente.setMetodoPagoPreferido(dto.getMetodoPagoPreferido());
        }
        if (usuario instanceof Administrador administrador) {
            administrador.setPermisosEspeciales(dto.getPermisosEspeciales());
        }
        if (usuario instanceof Operador operador) {
            operador.setAreaControl(dto.getAreaControl());
        }
        return usuario;
    }

    public ReservaDTO toReservaDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return ReservaDTO.builder()
                .id(reserva.getId())
                .fecha(reserva.getFecha())
                .horaEntrada(reserva.getHoraEntrada())
                .horaSalida(reserva.getHoraSalida())
                .estado(reserva.getEstado())
                .duracionMinutos(reserva.getDuracionMinutos())
                .total(reserva.getTotal())
                .clienteId(reserva.getClienteId())
                .tarifaId(reserva.getTarifaId())
                .parqueaderoId(reserva.getParqueaderoId())
                .codigoQrId(reserva.getCodigoQrId())
                .pagoId(reserva.getPagoId())
                .minutosConsumidos(reserva.getMinutosConsumidos())
                .cargoAdicional(reserva.getCargoAdicional())
                .build();
    }

    public Reserva toReservaEntity(ReservaDTO dto) {
        if (dto == null) {
            return null;
        }
        return Reserva.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .horaEntrada(dto.getHoraEntrada())
                .horaSalida(dto.getHoraSalida())
                .estado(dto.getEstado())
                .duracionMinutos(dto.getDuracionMinutos())
                .total(dto.getTotal())
                .clienteId(dto.getClienteId())
                .tarifaId(dto.getTarifaId())
                .parqueaderoId(dto.getParqueaderoId())
                .codigoQrId(dto.getCodigoQrId())
                .pagoId(dto.getPagoId())
                .minutosConsumidos(dto.getMinutosConsumidos())
                .cargoAdicional(dto.getCargoAdicional())
                .build();
    }

    public PagoDTO toPagoDto(Pago pago) {
        if (pago == null) {
            return null;
        }
        return PagoDTO.builder()
                .id(pago.getId())
                .metodo(pago.getMetodo())
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .estado(pago.getEstado())
                .reservaId(pago.getReservaId())
                .referenciaTransaccion(pago.getReferenciaTransaccion())
                .build();
    }

    public Pago toPagoEntity(PagoDTO dto) {
        if (dto == null) {
            return null;
        }
        return Pago.builder()
                .id(dto.getId())
                .metodo(dto.getMetodo())
                .monto(dto.getMonto())
                .fechaPago(dto.getFechaPago())
                .estado(dto.getEstado())
                .reservaId(dto.getReservaId())
                .referenciaTransaccion(dto.getReferenciaTransaccion())
                .build();
    }

    public CodigoQRDTO toCodigoDto(CodigoQR codigoQR) {
        if (codigoQR == null) {
            return null;
        }
        return CodigoQRDTO.builder()
                .id(codigoQR.getId())
                .codigo(codigoQR.getCodigo())
                .estado(codigoQR.getEstado())
                .fechaGeneracion(codigoQR.getFechaGeneracion())
                .fechaExpiracion(codigoQR.getFechaExpiracion())
                .reservaId(codigoQR.getReservaId())
                .fechaEntradaValidada(codigoQR.getFechaEntradaValidada())
                .fechaSalidaValidada(codigoQR.getFechaSalidaValidada())
                .usosRegistrados(codigoQR.getUsosRegistrados())
                .build();
    }

    public CodigoQR toCodigoEntity(CodigoQRDTO dto) {
        if (dto == null) {
            return null;
        }
        return CodigoQR.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .estado(dto.getEstado())
                .fechaGeneracion(dto.getFechaGeneracion())
                .fechaExpiracion(dto.getFechaExpiracion())
                .reservaId(dto.getReservaId())
                .fechaEntradaValidada(dto.getFechaEntradaValidada())
                .fechaSalidaValidada(dto.getFechaSalidaValidada())
                .usosRegistrados(dto.getUsosRegistrados())
                .build();
    }

    public TarifaDTO toTarifaDto(Tarifa tarifa) {
        if (tarifa == null) {
            return null;
        }
        return TarifaDTO.builder()
                .id(tarifa.getId())
                .tipo(tarifa.getTipo())
                .valorHora(tarifa.getValorHora())
                .horarioInicio(tarifa.getHorarioInicio())
                .horarioFin(tarifa.getHorarioFin())
                .vigente(tarifa.isVigente())
                .build();
    }

    public Tarifa toTarifaEntity(TarifaDTO dto) {
        if (dto == null) {
            return null;
        }
        return Tarifa.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .valorHora(dto.getValorHora())
                .horarioInicio(dto.getHorarioInicio())
                .horarioFin(dto.getHorarioFin())
                .vigente(dto.isVigente())
                .build();
    }

    public ReporteDTO toReporteDto(Reporte reporte) {
        if (reporte == null) {
            return null;
        }
        return ReporteDTO.builder()
                .id(reporte.getId())
                .tipo(reporte.getTipo())
                .fechaGeneracion(reporte.getFechaGeneracion())
                .contenido(reporte.getContenido())
                .build();
    }

    public Reporte toReporteEntity(ReporteDTO dto) {
        if (dto == null) {
            return null;
        }
        return Reporte.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .fechaGeneracion(dto.getFechaGeneracion())
                .contenido(dto.getContenido())
                .build();
    }

    public ParqueaderoDTO toParqueaderoDto(Parqueadero parqueadero) {
        if (parqueadero == null) {
            return null;
        }
        return ParqueaderoDTO.builder()
                .id(parqueadero.getId())
                .nombre(parqueadero.getNombre())
                .ubicacion(parqueadero.getUbicacion())
                .capacidadTotal(parqueadero.getCapacidadTotal())
                .build();
    }

    public Parqueadero toParqueaderoEntity(ParqueaderoDTO dto) {
        if (dto == null) {
            return null;
        }
        return Parqueadero.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .ubicacion(dto.getUbicacion())
                .capacidadTotal(dto.getCapacidadTotal())
                .build();
    }

    public NotificacionDTO toNotificacionDto(Notificacion notificacion) {
        if (notificacion == null) {
            return null;
        }
        NotificacionDTO.NotificacionDTOBuilder builder = NotificacionDTO.builder()
                .id(notificacion.getId())
                .mensaje(notificacion.getMensaje())
                .fechaEnvio(notificacion.getFechaEnvio())
                .estado(notificacion.getEstado())
                .tipo(notificacion.getTipo())
                .usuarioId(notificacion.getUsuarioId())
                .reservaId(notificacion.getReservaId());

        if (notificacion instanceof NotificacionCorreo correo) {
            builder.correoDestino(correo.getCorreoDestino());
        }
        if (notificacion instanceof NotificacionApp app) {
            builder.idUsuarioDestino(app.getIdUsuarioDestino());
        }
        if (notificacion instanceof NotificacionSMS sms) {
            builder.numeroTelefono(sms.getNumeroTelefono());
        }
        return builder.build();
    }

    public Notificacion toNotificacionEntity(NotificacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Notificacion notificacion;
        TipoNotificacion tipo = Objects.requireNonNull(dto.getTipo(), "Tipo de notificacion requerido");
        switch (tipo) {
            case CORREO -> {
                NotificacionCorreo correo = new NotificacionCorreo();
                correo.setCorreoDestino(dto.getCorreoDestino());
                notificacion = correo;
            }
            case APP -> {
                NotificacionApp app = new NotificacionApp();
                app.setIdUsuarioDestino(dto.getIdUsuarioDestino());
                notificacion = app;
            }
            case SMS -> {
                NotificacionSMS sms = new NotificacionSMS();
                sms.setNumeroTelefono(dto.getNumeroTelefono());
                notificacion = sms;
            }
            default -> throw new IllegalStateException("Tipo de notificacion no soportado");
        }
        notificacion.setId(dto.getId());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setFechaEnvio(dto.getFechaEnvio());
        notificacion.setEstado(dto.getEstado());
        notificacion.setTipo(tipo);
        notificacion.setUsuarioId(dto.getUsuarioId());
        notificacion.setReservaId(dto.getReservaId());
        return notificacion;
    }
}
