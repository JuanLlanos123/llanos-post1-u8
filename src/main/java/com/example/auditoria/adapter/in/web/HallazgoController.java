package com.example.auditoria.adapter.in.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.usecase.CerrarHallazgoUseCase;
import com.example.auditoria.usecase.ConsultarHallazgoUseCase;
import com.example.auditoria.usecase.IniciarRemediacionUseCase;
import com.example.auditoria.usecase.ReabrirHallazgoUseCase;
import com.example.auditoria.usecase.RegistrarHallazgoUseCase;
import com.example.auditoria.adapter.in.dto.*;
@RestController
@RequestMapping("/api/hallazgos")
public class HallazgoController {
    private final RegistrarHallazgoUseCase registrarUseCase;
    private final IniciarRemediacionUseCase iniciarRemediacionUseCase;
    private final CerrarHallazgoUseCase cerrarUseCase;
    private final ReabrirHallazgoUseCase reabrirUseCase;
    private final ConsultarHallazgoUseCase consultarUseCase;

    public HallazgoController(RegistrarHallazgoUseCase registrarUseCase,IniciarRemediacionUseCase iniciarRemediacionUseCase,
        CerrarHallazgoUseCase cerrarUseCase,ReabrirHallazgoUseCase reabrirUseCase, ConsultarHallazgoUseCase consultarUseCase){
            this.registrarUseCase=registrarUseCase;
            this.iniciarRemediacionUseCase=iniciarRemediacionUseCase;
            this.reabrirUseCase=reabrirUseCase;
            this.cerrarUseCase=cerrarUseCase;
            this.consultarUseCase=consultarUseCase;
    }
    

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String,String> registrar(@RequestBody RegistrarHallazgoRequest req){
        HallazgoId id=registrarUseCase.ejecutar(req.titulo(),req.descripcion(),req.areaResponsable(),req.severidad(),req.fechaDeteccion());
        return Map.of("hallazgoId",id.toString());
    }

    @PatchMapping("/{id}/iniciar-remediacion")
    public Map<String,String> iniciarRemediacion(@PathVariable String id,@RequestBody IniciarRemediacionRequest req){
        HallazgoId hallazgoId=new HallazgoId(UUID.fromString(id));
        iniciarRemediacionUseCase.ejecutar(hallazgoId,req.responsable(),req.fechaLimite(),req.notas());
        return Map.of("estado","EN_REMEDIACION");
    }


    @PatchMapping("/{id}/cerrar")
    public Map<String,String> cerrar(@PathVariable String id){
        cerrarUseCase.ejecutar(new HallazgoId(UUID.fromString(id)));
        return Map.of("estado","CERRADO");
    }


    @PatchMapping("/{id}/reabrir")
    public Map<String,String> reabrir(@PathVariable String id,@RequestBody ReabrirRequest req){
        HallazgoId hallazgoId=new HallazgoId(UUID.fromString(id));
        reabrirUseCase.ejecutar(hallazgoId,req.motivo());
        return Map.of("estado","REABIERTO");
    }

    @GetMapping("/{id}")
    public HallazgoResponse buscar(@PathVariable String id){
        return consultarUseCase.buscarPorId(new HallazgoId(UUID.fromString(id)));
    }

    @GetMapping 
    public List<HallazgoResponse> listar(){
        return consultarUseCase.listarTodos();
    }
     
}
