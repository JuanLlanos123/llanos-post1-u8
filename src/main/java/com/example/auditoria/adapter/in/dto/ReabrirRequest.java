package com.example.auditoria.adapter.in.dto;


public record ReabrirRequest(String motivo){


    public ReabrirRequest{
        
        if(motivo==null||motivo.isBlank()){
           throw new IllegalArgumentException("el motivo no puede estar vacio");
        }
        motivo=motivo.trim();

    }
    
}
