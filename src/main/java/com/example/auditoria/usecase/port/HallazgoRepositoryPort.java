package com.example.auditoria.usecase.port;

import java.util.List;
import java.util.Optional;

import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.HallazgoId;

public interface HallazgoRepositoryPort {
    void guardar(HallazgoAuditoria hallazgo);
    @SuppressWarnings("hiding")
    <Optional>HallazgoAuditoria buscarPorId(HallazgoId id);
    List<HallazgoAuditoria> buscarTodos();
    // Métodos añadidos en la Parte 2 — mismo puerto, sin stack de lectura separado
    List<ConteoCategoria> contarPorSeveridad();
    List<ConteoCategoria> contarPorEstado();
    List<PromedioCategoria> promedioDiasCierrePorArea();

}
