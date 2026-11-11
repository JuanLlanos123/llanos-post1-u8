package com.example.auditoria.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.PlanRemediacion;
import com.example.auditoria.domain.valueobject.Severidad;
import com.example.auditoria.domain.valueobject.TransicionInvalidaException;

public class HallazgoAuditoria {
    private final HallazgoId id;
    private final String titulo;
    private final String descripcion;
    private final String areaResponsable;
    private final Severidad severidad;
    private final LocalDate fechaDeteccion;
    private EstadoHallazgo estado;
    private PlanRemediacion planRemediacion;
    private LocalDate fechaCierre;

    public HallazgoAuditoria(HallazgoId id,String titulo,String descripcion,String areaResponsable,Severidad severidad,LocalDate fechaDeteccion){
        Objects.requireNonNull(id);
        if(titulo==null||titulo.isBlank()){
            throw new IllegalArgumentException("El titulo es obligatorio");
        }
        if(areaResponsable==null||areaResponsable.isBlank()){
            throw new IllegalArgumentException("El area Responsable es obligatoria");
        }
        this.id=id;
        this.titulo=titulo;
        this.descripcion=descripcion;
        this.areaResponsable=areaResponsable;
        this.severidad=severidad;
        this.fechaDeteccion=fechaDeteccion;
        this.estado=EstadoHallazgo.ABIERTO;
    }

    public static HallazgoAuditoria rehidratar(
        HallazgoId id,
        String titulo,
        String descripcion,
        String areaResponsable,
        Severidad severidad,
        LocalDate fechaDeteccion,
        EstadoHallazgo estado,
        PlanRemediacion planRemediacion,
        LocalDate fechaCierre) {
        HallazgoAuditoria hallazgo = new HallazgoAuditoria(
        id, titulo, descripcion, areaResponsable, severidad, fechaDeteccion);

        hallazgo.estado = Objects.requireNonNull(estado);
        hallazgo.planRemediacion = planRemediacion;
        hallazgo.fechaCierre = fechaCierre;
        return hallazgo;
    }


    public EstadoHallazgo iniciarRemediacion(PlanRemediacion plan){
        EstadoHallazgo anterior=transicionar(EstadoHallazgo.EN_REMEDIACION);
        this.planRemediacion=Objects.requireNonNull(plan,"El plan de remediacion es obligatorio");
        return anterior;
    }

    public EstadoHallazgo cerrar(){
        if(planRemediacion==null){
            throw new IllegalStateException("No se puede cerrar un hallazgo sin plan de remediacion");
        }
        EstadoHallazgo anterior=transicionar(EstadoHallazgo.CERRADO);
        this.fechaCierre=LocalDate.now();
        return anterior;
    }

    public EstadoHallazgo reabrir(){
        EstadoHallazgo anterior=transicionar(EstadoHallazgo.REABIERTO);
        this.fechaCierre=null;
        return anterior;
    }

    public EstadoHallazgo transicionar(EstadoHallazgo destino){
        if(!estado.puedeTransicionarA(destino)){
            throw new TransicionInvalidaException(estado,destino);
        }
        EstadoHallazgo anterior=this.estado;
        this.estado=destino;
        return anterior;
    }

    public HallazgoId getId(){
        return id;
    }
    public String getTitulo(){
        return titulo;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public String getAreaResponsable(){
        return areaResponsable;
    }
    public Severidad getSeveridad(){
        return severidad;
    }
    public LocalDate getFechaDeteccion(){
        return fechaDeteccion;
    }
    public LocalDate getFechaCierre(){
        return fechaCierre;
    }
    public EstadoHallazgo getEstado(){
        return estado;
    }
    public PlanRemediacion getPlanRemediacion(){
        return planRemediacion;
    }
   
    
}
