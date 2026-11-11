package com.example.auditoria.usecase.impl;

import java.util.List;

import com.example.auditoria.adapter.in.dto.HallazgoResponse;
import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.usecase.ConsultarHallazgoUseCase;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;

public class ConsutarHallazgoService implements ConsultarHallazgoUseCase {

    public final HallazgoRepositoryPort repository;
    public ConsutarHallazgoService(HallazgoRepositoryPort repository){
            this.repository=repository;
    }

    @Override
    public HallazgoResponse buscarPorId(HallazgoId id) {
    return toResponse(repository.buscarPorId(id));
    }

    @Override
    public List<HallazgoResponse> listarTodos() {
    return repository.buscarTodos().stream()
        .map(this::toResponse)
        .collect(java.util.stream.Collectors.toList());
    }

    private HallazgoResponse toResponse(HallazgoAuditoria hallazgo) {
    HallazgoResponse response = new HallazgoResponse();
    response.setId(hallazgo.getId().toString());
    response.setTitulo(hallazgo.getTitulo());
    response.setDescripcion(hallazgo.getDescripcion());
    response.setAreaResponsable(hallazgo.getAreaResponsable());
    response.setSeveridad(hallazgo.getSeveridad());
    response.setFechaDeteccion(hallazgo.getFechaDeteccion());
    response.setEstado(hallazgo.getEstado());
    response.setPlanRemediacion(hallazgo.getPlanRemediacion());
    response.setFechaCierre(hallazgo.getFechaCierre());
    return response;
}
    
}
