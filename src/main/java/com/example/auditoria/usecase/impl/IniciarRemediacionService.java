package com.example.auditoria.usecase.impl;

import java.time.LocalDate;


import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.PlanRemediacion;
import com.example.auditoria.usecase.IniciarRemediacionUseCase;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;

public class IniciarRemediacionService implements IniciarRemediacionUseCase{
    public final HallazgoRepositoryPort repository;
    public IniciarRemediacionService(HallazgoRepositoryPort repository){
        this.repository=repository;
    }
    @Override
    public void ejecutar(HallazgoId id, String responsable, LocalDate fechaLimite, String notas) {
        HallazgoAuditoria hallazgo=repository.buscarPorId(id)
        ;//.orElseThrow(()->new NotFoundException());
        hallazgo.iniciarRemediacion(new PlanRemediacion(responsable, fechaLimite, notas));
        repository.guardar(hallazgo);
    }
    
    
}
