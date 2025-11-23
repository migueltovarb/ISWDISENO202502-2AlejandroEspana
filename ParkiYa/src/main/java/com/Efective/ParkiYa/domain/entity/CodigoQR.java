package com.Efective.ParkiYa.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.Efective.ParkiYa.domain.entity.enums.EstadoCodigoQR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "codigos_qr")
public class CodigoQR {

    @Id
    private String id;
    private String codigo;
    private EstadoCodigoQR estado;
    private LocalDateTime fechaGeneracion;
    private LocalDateTime fechaExpiracion;
    private String reservaId;
    private LocalDateTime fechaEntradaValidada;
    private LocalDateTime fechaSalidaValidada;
    private int usosRegistrados;
}
