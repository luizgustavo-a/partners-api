package ldelivery.api_partners.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Partners API")
                        .description("API Rest for L-Delivery, containing CRUD functionalities related to the Partners " +
                                "of the company and dealing with the GIS data of such Partners."));

    }
}
