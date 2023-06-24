package com.recipe.api.config;

import com.recipe.api.util.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** This class does the configuration for JPA auditing. */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAudit {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }
}
