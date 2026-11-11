package com.example.auditoria.usecase.port;

import java.util.List;

import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;

public interface HistorialAuditoriaPort {
    void registrar(HallazgoId hallazgoId, EstadoHallazgo anterior, EstadoHallazgo nuevo, String motivo);
    List<CambioEstadoView> listarPorHallazgo(HallazgoId hallazgoId);
}


