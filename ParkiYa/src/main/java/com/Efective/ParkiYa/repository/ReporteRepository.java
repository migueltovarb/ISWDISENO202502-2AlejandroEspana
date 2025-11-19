package com.Efective.ParkiYa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Efective.ParkiYa.domain.entity.Reporte;
import com.Efective.ParkiYa.domain.entity.enums.TipoReporte;

public interface ReporteRepository extends MongoRepository<Reporte, String> {

    List<Reporte> findByTipo(TipoReporte tipo);
}
