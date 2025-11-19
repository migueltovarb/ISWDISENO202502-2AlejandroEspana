package com.Efective.ParkiYa.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.Efective.ParkiYa.domain.entity.enums.TipoReporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reportes")
public class Reporte {

    @Id
    private String id;
    private TipoReporte tipo;
    private LocalDateTime fechaGeneracion;
    private String contenido;
}
