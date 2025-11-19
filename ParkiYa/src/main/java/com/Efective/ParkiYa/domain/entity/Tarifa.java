package com.Efective.ParkiYa.domain.entity;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tarifas")
public class Tarifa {

    @Id
    private String id;
    private String tipo;
    private double valorHora;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private boolean vigente;
}
