package com.example.auditoria.usecase.impl;

import java.time.LocalDate;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.Severidad;
import com.example.auditoria.usecase.RegistrarHallazgoUseCase;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;

public class RegistrarHallazgoService implements RegistrarHallazgoUseCase {

    private final HallazgoRepositoryPort repository;
   
    public RegistrarHallazgoService(HallazgoRepositoryPort repository){
        this.repository=repository;
    }

    @Override
    public HallazgoId ejecutar(String titulo, String descripcion, String areaResponsable, Severidad severidad,
            LocalDate fechaDeteccion) {
            HallazgoAuditoria hallazgo=new HallazgoAuditoria(HallazgoId.nuevo(), titulo, descripcion, areaResponsable, severidad, fechaDeteccion);
            repository.guardar(hallazgo);
            return hallazgo.getId();    
    }

    
}
