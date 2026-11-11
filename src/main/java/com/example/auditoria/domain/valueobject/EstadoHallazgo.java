package com.example.auditoria.domain.valueobject;

public enum EstadoHallazgo {
    ABIERTO,EN_REMEDIACION,CERRADO,REABIERTO;

    public boolean puedeTransicionarA(EstadoHallazgo destino){
        switch (this) {
            case ABIERTO:
               return destino==EN_REMEDIACION;
            case EN_REMEDIACION:
                return destino==CERRADO;
            case CERRADO:
                return destino==REABIERTO;
            case REABIERTO:
                return destino==EN_REMEDIACION;
        }
        return false;
    }
}
