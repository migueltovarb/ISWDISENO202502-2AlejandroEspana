package com.Efective.ParkiYa.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Efective.ParkiYa.domain.entity.Parqueadero;
import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;
import com.Efective.ParkiYa.dto.DisponibilidadParqueaderoDTO;
import com.Efective.ParkiYa.dto.ParqueaderoDTO;
import com.Efective.ParkiYa.dto.ReporteParqueaderoDTO;
import com.Efective.ParkiYa.exception.ResourceNotFoundException;
import com.Efective.ParkiYa.mapper.ParkiYaMapper;
import com.Efective.ParkiYa.repository.ParqueaderoRepository;
import com.Efective.ParkiYa.repository.ReservaRepository;
import com.Efective.ParkiYa.service.ParqueaderoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParqueaderoServiceImpl implements ParqueaderoService {

    private final ParqueaderoRepository parqueaderoRepository;
    private final ReservaRepository reservaRepository;
    private final ParkiYaMapper mapper;

    @Override
    public ParqueaderoDTO crearParqueadero(ParqueaderoDTO dto) {
        Parqueadero parqueadero = mapper.toParqueaderoEntity(dto);
        Parqueadero guardado = parqueaderoRepository.save(parqueadero);
        return mapper.toParqueaderoDto(guardado);
    }

    @Override
    public ParqueaderoDTO actualizarParqueadero(String id, ParqueaderoDTO dto) {
        Parqueadero existente = obtenerParqueaderoEntity(id);
        existente.setNombre(dto.getNombre());
        existente.setUbicacion(dto.getUbicacion());
        existente.setCapacidadTotal(dto.getCapacidadTotal());
        return mapper.toParqueaderoDto(parqueaderoRepository.save(existente));
    }

    @Override
    public List<ParqueaderoDTO> obtenerParqueaderos() {
        return parqueaderoRepository.findAll().stream()
                .map(mapper::toParqueaderoDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParqueaderoDTO obtenerParqueadero(String id) {
        return mapper.toParqueaderoDto(obtenerParqueaderoEntity(id));
    }

    @Override
    public DisponibilidadParqueaderoDTO consultarDisponibilidad(String id, LocalDate fecha) {
        Parqueadero parqueadero = obtenerParqueaderoEntity(id);
        LocalDate fechaConsulta = fecha == null ? LocalDate.now() : fecha;
        long reservas = reservaRepository.findByParqueaderoIdAndFecha(id, fechaConsulta).stream()
                .filter(reserva -> reserva.getEstado() != EstadoReserva.CANCELADA)
                .count();
        long cupos = Math.max(parqueadero.getCapacidadTotal() - reservas, 0);
        return DisponibilidadParqueaderoDTO.builder()
                .parqueaderoId(id)
                .fecha(fechaConsulta)
                .capacidadTotal(parqueadero.getCapacidadTotal())
                .reservasProgramadas(reservas)
                .cuposDisponibles(cupos)
                .build();
    }

    @Override
    public ReporteParqueaderoDTO generarReporteOcupacion(String id, LocalDate fecha) {
        Parqueadero parqueadero = obtenerParqueaderoEntity(id);
        LocalDate fechaConsulta = fecha == null ? LocalDate.now() : fecha;
        List<com.Efective.ParkiYa.domain.entity.Reserva> reservas = reservaRepository
                .findByParqueaderoIdAndFecha(id, fechaConsulta);
        long reservasValidas = reservas.stream()
                .filter(reserva -> reserva.getEstado() != EstadoReserva.CANCELADA)
                .count();
        double facturacion = reservas.stream()
                .filter(reserva -> reserva.getEstado() != EstadoReserva.CANCELADA)
                .mapToDouble(reserva -> reserva.getTotal())
                .sum();
        double porcentaje = parqueadero.getCapacidadTotal() == 0 ? 0
                : (reservasValidas * 100.0) / parqueadero.getCapacidadTotal();
        return ReporteParqueaderoDTO.builder()
                .parqueaderoId(id)
                .fecha(fechaConsulta)
                .capacidadTotal(parqueadero.getCapacidadTotal())
                .reservasConfirmadas(reservasValidas)
                .porcentajeOcupacion(Math.min(100.0, porcentaje))
                .facturacionTotal(Math.round(facturacion * 100.0) / 100.0)
                .build();
    }

    private Parqueadero obtenerParqueaderoEntity(String id) {
        return parqueaderoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parqueadero no encontrado"));
    }
}
