package com.recipe.api.util;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/**
 * This class is responsible for enabling JPA auditing to inform Spring Data about the changes.
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Recipe Auditor");
    }
}
