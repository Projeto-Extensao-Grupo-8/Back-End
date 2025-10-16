package flor_de_lotus.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

    @Configuration
    @OpenAPIDefinition(
        info = @Info(
                title = "Projeto Flor de lótus",
                description = "API Back-end do consultório Flor de lótus",
                contact = @Contact(
                        name = "Gustavo AMorim",
                        url = "https://github.com/Projeto-Extensao-Grupo-8/Back-End/tree/main/flor-de-lotus",
                        email = "gustavo.famorim@sptech.school"
                ),
                license = @License(name = "UNLICENSED"),
                version = "1.0.0"
        )
    )

@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )

public class OpenApiConfig {

}
