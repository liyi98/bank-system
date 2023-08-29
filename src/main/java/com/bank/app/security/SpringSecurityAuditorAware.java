package com.bank.app.security;

import com.bank.app.config.Constants;
import com.bank.app.service.exception.AuthenticationException;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.of(SecurityUtils.getCurrentUserLogin() == null ? Constants.SYSTEM : SecurityUtils.getCurrentUserLogin());
        } catch (AuthenticationException e) {}
        return Optional.of(Constants.SYSTEM);
    }
}
