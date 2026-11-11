package com.example.auditoria.adapter.in.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record IniciarRemediacionRequest(
      @NotBlank(message = "El responsable del plan es obligatorio")
    String responsable,
    LocalDate fechaLimite,
    String notas
) {
  
}
