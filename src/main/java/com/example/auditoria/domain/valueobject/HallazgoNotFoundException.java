package com.example.auditoria.domain.valueobject;

public class HallazgoNotFoundException extends RuntimeException {
        public HallazgoNotFoundException(HallazgoId id){
            super("No se encuentar el hallazgo con id "+id);
        }
    
}
