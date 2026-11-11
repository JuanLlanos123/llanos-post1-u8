package com.example.auditoria.adapter.out.persistence;

import java.time.LocalDate;

import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.Severidad;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="hallazgos")
public class HallazgoJpaEntity {
    @Id 
    private String id;
    private String titulo;
    private String descripcion;
    private String areaResponsable;
    @Enumerated(EnumType.STRING)
    private Severidad severidad;
    @Enumerated(EnumType.STRING)
    private EstadoHallazgo estado;
    private LocalDate fechaDeteccion;
    private LocalDate fechaCierre;
    private String planResponsable;
    private LocalDate planFechaLimite;
    private String planNotas;
    public HallazgoJpaEntity(){};
    public String getId(){
        return id;
    }
    public String getTitulo(){
        return titulo;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public Severidad getSeveridad(){
        return severidad;
    }
    public String getAreaResponsable(){
        return areaResponsable;
    }
    public EstadoHallazgo getEstado(){
        return estado;
    }
    public LocalDate getFechaDeteccion(){
        return fechaDeteccion;
    }
    public LocalDate getFechaCierre(){
        return fechaCierre;
    }
    public String getPlanResponsable(){
        return planResponsable;
    }
    public LocalDate getPlanFechaLimite(){
        return planFechaLimite;
    }
    public String getPlanNotas(){
        return planNotas;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setTitulo(String titulo){
        this.titulo=titulo;
    }
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    public void setAreaResponsable(String areaResponsable){
        this.areaResponsable=areaResponsable;
    }
    public void setSeveridad(Severidad severidad){
        this.severidad=severidad;
    }
    public void setFechaDeteccion(LocalDate fechaDeteccion){
        this.fechaDeteccion=fechaDeteccion;
    }
    public void setFechaCierre(LocalDate fechaCierre){
        this.fechaCierre=fechaCierre;
    }
    public void setEstado(EstadoHallazgo estado){
        this.estado=estado;
    }
    public void setPlanResponsable(String planResponsable){
        this.planResponsable=planResponsable;
    }
    public void setPlanFechaLimite(LocalDate planFechaLimite){
        this.planFechaLimite=planFechaLimite;
    }
    public void setPlanNotas(String planNotas){
        this.planNotas=planNotas;
    }
}
