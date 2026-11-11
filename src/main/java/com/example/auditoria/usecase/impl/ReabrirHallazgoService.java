package com.example.auditoria.usecase.impl;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.usecase.ReabrirHallazgoUseCase;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;
import com.example.auditoria.usecase.port.HistorialAuditoriaPort;

public class ReabrirHallazgoService implements ReabrirHallazgoUseCase {


    private final HallazgoRepositoryPort repository;
    private final HistorialAuditoriaPort historial;
    public ReabrirHallazgoService(HallazgoRepositoryPort repository,HistorialAuditoriaPort historial){
        this.repository=repository;
        this.historial=historial;
    }
    
    @Override
    public void ejecutar(HallazgoId id, String motivo) {
       HallazgoAuditoria hallazgo=repository.buscarPorId(id);
       EstadoHallazgo anterior=hallazgo.reabrir();
       repository.guardar(hallazgo);
       historial.registrar(id, anterior, EstadoHallazgo.REABIERTO, motivo);
    }
    
}
