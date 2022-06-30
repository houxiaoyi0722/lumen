package com.sang.common.provider;

import io.ebean.config.CurrentUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Provides the current user to EbeanServer.
 */
@Component
public class CurrentUser implements CurrentUserProvider {

  @Override
  public Object currentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication != null ? (authentication.getPrincipal() != null) ? authentication.getPrincipal().toString() : "no_name" : "anonymous";
  }
}
