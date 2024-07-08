package uol.compass.calculate.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("PB Springboot April - Challenge 3")
                        .description("The third Compass challenge of the back-end journey (Spring Boot - AWS Cloud Context)")
                        .version("0.0.1")
                );
    }

}