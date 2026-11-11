package com.example.auditoria.usecase.impl;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.usecase.CerrarHallazgoUseCase;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;

public class CerrarHallazgoService implements CerrarHallazgoUseCase {



    private final HallazgoRepositoryPort repository;
    public CerrarHallazgoService(HallazgoRepositoryPort repository){
        this.repository=repository;
    }

    @Override
    public void ejecutar(HallazgoId id) {
     HallazgoAuditoria hallazgo=repository.buscarPorId(id);
     hallazgo.cerrar();
     repository.guardar(hallazgo);
    }
    
}
