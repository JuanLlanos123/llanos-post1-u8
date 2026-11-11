package com.example.auditoria.usecase.impl;

import java.time.LocalDate;


import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.PlanRemediacion;
import com.example.auditoria.usecase.IniciarRemediacionUseCase;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;
import com.example.auditoria.usecase.port.HistorialAuditoriaPort;

public class IniciarRemediacionService implements IniciarRemediacionUseCase{
    public final HallazgoRepositoryPort repository;
    private final HistorialAuditoriaPort historial;
    public IniciarRemediacionService(HallazgoRepositoryPort repository,HistorialAuditoriaPort historial){
        this.repository=repository;
        this.historial=historial;
    }
    @Override
    public void ejecutar(HallazgoId id, String responsable, LocalDate fechaLimite, String notas) {
        HallazgoAuditoria hallazgo=repository.buscarPorId(id)
        ;//.orElseThrow(()->new NotFoundException());
        EstadoHallazgo anterior=hallazgo.iniciarRemediacion(new PlanRemediacion(responsable, fechaLimite, notas));
        repository.guardar(hallazgo);
        historial.registrar(id,anterior, EstadoHallazgo.EN_REMEDIACION, "Se inicia la remediacion");
    }
    
    
}
