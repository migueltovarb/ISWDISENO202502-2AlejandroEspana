package com.Efective.ParkiYa.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.Efective.ParkiYa.domain.entity.enums.EstadoReserva;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reservas")
public class Reserva {

    @Id
    private String id;
    private LocalDate fecha;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private EstadoReserva estado;
    private long duracionMinutos;
    private double total;
    private String clienteId;
    private String tarifaId;
    private String parqueaderoId;
    private String codigoQrId;
    private String pagoId;
    private long minutosConsumidos;
    private double cargoAdicional;
}
