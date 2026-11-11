package com.example.auditoria.adapter.out.persistence;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import java.util.Optional;
import com.example.auditoria.domain.entity.HallazgoAuditoria;
import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.PlanRemediacion;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;
@Component 
public class HallazgoRepositoryAdapter implements HallazgoRepositoryPort{
    private final HallazgoJpaRepository jpa;
    public HallazgoRepositoryAdapter(HallazgoJpaRepository jpa){
        this.jpa=jpa;

    }


    @Override
    public void guardar(HallazgoAuditoria hallazgo) {
       jpa.save(toEntity(hallazgo));
    }
   @Override
    public HallazgoAuditoria buscarPorId(HallazgoId id) {
    return jpa.findById(id.toString())
        .map(this::toDomain)
        .orElseThrow();
    }




    @Override
    public List<HallazgoAuditoria> buscarTodos() {
        return StreamSupport.stream(jpa.findAll().spliterator(), false)
        .map(this::toDomain)
        .collect(Collectors.toList());
    }



    private HallazgoAuditoria toDomain(HallazgoJpaEntity e) {
    PlanRemediacion plan = null;

    if (e.getPlanResponsable() != null) {
        plan = new PlanRemediacion(
            e.getPlanResponsable(),
            e.getPlanFechaLimite(),
            e.getPlanNotas()
        );
    }

    return HallazgoAuditoria.rehidratar(
        new HallazgoId(UUID.fromString(e.getId())),
        e.getTitulo(),
        e.getDescripcion(),
        e.getAreaResponsable(),
        e.getSeveridad(),
        e.getFechaDeteccion(),
        e.getEstado(),
        plan,
        e.getFechaCierre()
    );
    }

     private HallazgoJpaEntity toEntity(HallazgoAuditoria h) {
        HallazgoJpaEntity e = new HallazgoJpaEntity();
        e.setId(h.getId().toString());
        e.setTitulo(h.getTitulo());
        e.setDescripcion(h.getDescripcion());
        e.setAreaResponsable(h.getAreaResponsable());
        e.setSeveridad(h.getSeveridad());
        e.setEstado(h.getEstado());
        e.setFechaDeteccion(h.getFechaDeteccion());
        e.setFechaCierre(h.getFechaCierre());
        if (h.getPlanRemediacion() != null) {
            e.setPlanResponsable(h.getPlanRemediacion().responsable());
            e.setPlanFechaLimite(h.getPlanRemediacion().fechaLimite());
            e.setPlanNotas(h.getPlanRemediacion().notas());
        }
        return e;
    }
    
}
