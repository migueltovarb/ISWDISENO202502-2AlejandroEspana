package com.Efective.ParkiYa.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.Efective.ParkiYa.domain.entity.enums.EstadoPago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pagos")
public class Pago {

    @Id
    private String id;
    private String metodo;
    private double monto;
    private LocalDateTime fechaPago;
    private EstadoPago estado;
    private String reservaId;
    private String referenciaTransaccion;
}
