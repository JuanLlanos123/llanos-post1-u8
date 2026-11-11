package com.example.auditoria.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.auditoria.domain.valueobject.EstadoHallazgo;
import com.example.auditoria.domain.valueobject.HallazgoId;
import com.example.auditoria.domain.valueobject.PlanRemediacion;
import com.example.auditoria.domain.valueobject.Severidad;

class HallazgoAuditoriaTest {

    @Test
    void deberiaIniciarRemediacionCorrectamente() {
        HallazgoAuditoria hallazgo = new HallazgoAuditoria(
            new HallazgoId(UUID.randomUUID()),
            "Falta de control de acceso",
            "Descripción del hallazgo",
            "Seguridad TI",
            Severidad.ALTA,
            LocalDate.now()
        );
        PlanRemediacion plan = new PlanRemediacion(
            "Ana Gómez",
            LocalDate.now().plusDays(15),
            "Implementar MFA"
        );

        hallazgo.iniciarRemediacion(plan);

        assertEquals(EstadoHallazgo.EN_REMEDIACION, hallazgo.getEstado());
        assertNotNull(hallazgo.getPlanRemediacion());
    }
}