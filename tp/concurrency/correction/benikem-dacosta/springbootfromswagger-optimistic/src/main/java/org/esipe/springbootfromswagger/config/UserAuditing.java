package org.esipe.springbootfromswagger.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class UserAuditing implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(System.getProperty("user.name"));
    }
}
