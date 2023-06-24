package com.recipe.api.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The configuration class for openAPI. */
@Configuration
public class OpenAPI {

  @Value("${recipe.openapi.dev-url}")
  private String devUrl;

  @Bean
  public io.swagger.v3.oas.models.OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Contact contact = new Contact();
    contact.setEmail("libu.mathew@gmail.com");
    contact.setName("Libu Mathew");
    contact.setUrl("https://www.nlrecipes.com");

    License mitLicense =
        new License().name("Food License").url("https://choosealicense.com/licenses/food/");

    Info info =
        new Info()
            .title("Recipe Management API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage recipes.")
            .termsOfService("https://www.nlrecipes.com/terms")
            .license(mitLicense);

    return new io.swagger.v3.oas.models.OpenAPI().info(info).servers(List.of(devServer));
  }
}
