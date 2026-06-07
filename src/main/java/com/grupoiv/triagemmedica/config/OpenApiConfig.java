package com.grupoiv.triagemmedica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI triagemMedicaOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Avaliação de Sintomas e Encaminhamento Médico API")
                        .description("API REST para gestão de pacientes, médicos, avaliação de sintomas e encaminhamentos médicos.")
                        .version("1.0.0"));
    }
}
