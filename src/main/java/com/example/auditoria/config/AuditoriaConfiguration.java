package com.example.auditoria.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.auditoria.usecase.CerrarHallazgoUseCase;
import com.example.auditoria.usecase.ConsultarHallazgoUseCase;
import com.example.auditoria.usecase.ConsultarHistorialUseCase;
import com.example.auditoria.usecase.IniciarRemediacionUseCase;
import com.example.auditoria.usecase.ObtenerDashboardAuditoriaUseCase;
import com.example.auditoria.usecase.ReabrirHallazgoUseCase;
import com.example.auditoria.usecase.RegistrarHallazgoUseCase;
import com.example.auditoria.usecase.impl.CerrarHallazgoService;
import com.example.auditoria.usecase.impl.ConsultarHistorialService;
import com.example.auditoria.usecase.impl.ConsutarHallazgoService;
import com.example.auditoria.usecase.impl.IniciarRemediacionService;
import com.example.auditoria.usecase.impl.ObtenerDashboardAuditoriaService;
import com.example.auditoria.usecase.impl.ReabrirHallazgoService;
import com.example.auditoria.usecase.impl.RegistrarHallazgoService;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;
import com.example.auditoria.usecase.port.HistorialAuditoriaPort;

@Configuration
public class AuditoriaConfiguration {
    @Bean
    public RegistrarHallazgoUseCase registrarHallazgoUseCase(HallazgoRepositoryPort repo){
        return new RegistrarHallazgoService(repo);
    }

    @Bean
    public IniciarRemediacionUseCase iniciarRemediacionUseCase(HallazgoRepositoryPort repo,HistorialAuditoriaPort historial){
        return new IniciarRemediacionService(repo,historial);
    }
    
    @Bean
    public CerrarHallazgoUseCase cerrarHallazgoUseCase(HallazgoRepositoryPort repo,HistorialAuditoriaPort historial){
        return new CerrarHallazgoService(repo,historial);
    }
    @Bean
    public ConsultarHallazgoUseCase consultarHallazgoUseCase(HallazgoRepositoryPort repo){
        return new ConsutarHallazgoService(repo);
    }
    @Bean
    public ReabrirHallazgoUseCase reabrirHallazgoUseCase(HallazgoRepositoryPort repo,HistorialAuditoriaPort historial){
        return new ReabrirHallazgoService(repo,historial);
    }
    @Bean
    public ConsultarHistorialUseCase consultarHistorialUseCase(HistorialAuditoriaPort historial){
        return new ConsultarHistorialService(historial);
    }
    @Bean
    public ObtenerDashboardAuditoriaUseCase obtenerDasboardAuditriaUseCase(HallazgoRepositoryPort repo){
        return new ObtenerDashboardAuditoriaService(repo);
    }
}
