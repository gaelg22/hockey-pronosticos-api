package com.upiiz.hockey.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hockey League API",
                version = "1.0.0",
                description = "API REST para la gestión de equipos, jugadores y resultados de hockey.",
                termsOfService = "https://upiiz.com/terms",
                contact = @Contact(
                        name = "Equipo de desarrollo UPIIZ",
                        email = "soporte@upiiz.com",
                        url = "https://upiiz.com"
                ),
                license = @License(
                        name = "Licencia MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor local de desarrollo")
        }
)
public class SwaggerConfig {
    // Configuración general de OpenAPI
}
