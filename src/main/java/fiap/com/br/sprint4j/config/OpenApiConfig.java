package fiap.com.br.sprint4j.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
@Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Invest API")
                        .description("API de exemplo com Auth JWT, Persons e Investments")
                        .version("v1"));
    }
}
