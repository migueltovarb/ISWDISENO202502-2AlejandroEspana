package com.Efective.ParkiYa.domain.entity;

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
@Document(collection = "parqueaderos")
public class Parqueadero {

    @Id
    private String id;
    private String nombre;
    private String ubicacion;
    private int capacidadTotal;
}
