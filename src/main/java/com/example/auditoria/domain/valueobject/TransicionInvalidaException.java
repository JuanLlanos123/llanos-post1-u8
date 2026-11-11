package com.example.auditoria.domain.valueobject;

public class TransicionInvalidaException extends RuntimeException {
    
    public TransicionInvalidaException(EstadoHallazgo actual,EstadoHallazgo destino){
         super("no se puede transicionar de "+actual+" a "+destino);
    }
}
