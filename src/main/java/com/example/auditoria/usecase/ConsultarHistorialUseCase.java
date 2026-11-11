package com.example.auditoria.usecase;

import java.util.List;

import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.usecase.port.CambioEstadoView;

public interface ConsultarHistorialUseCase {
    List<CambioEstadoView> ejecutar(HallazgoId id);
}
