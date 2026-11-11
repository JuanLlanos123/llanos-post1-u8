package com.example.auditoria.usecase.impl;

import java.util.List;

import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.usecase.ConsultarHistorialUseCase;
import com.example.auditoria.usecase.port.CambioEstadoView;
import com.example.auditoria.usecase.port.HistorialAuditoriaPort;

public class ConsultarHistorialService implements ConsultarHistorialUseCase {

    private final HistorialAuditoriaPort historial;

    public ConsultarHistorialService(HistorialAuditoriaPort historial) {
        this.historial = historial;
    }

    @Override
    public List<CambioEstadoView> ejecutar(HallazgoId id) {
        return historial.listarPorHallazgo(id);
    }
}
