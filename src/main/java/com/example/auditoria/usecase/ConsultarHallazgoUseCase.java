package com.example.auditoria.usecase;

import java.util.List;


import com.example.auditoria.adapter.in.dto.HallazgoResponse;
import com.example.auditoria.domain.valueobject.HallazgoId;

public interface ConsultarHallazgoUseCase {
    HallazgoResponse buscarPorId(HallazgoId id);
    List<HallazgoResponse> listarTodos();
}
