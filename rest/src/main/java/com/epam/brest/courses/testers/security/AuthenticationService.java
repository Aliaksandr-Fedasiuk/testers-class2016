package com.epam.brest.courses.testers.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by xalf on 30.12.15.
 */
public interface AuthenticationService {

    /**
     * Authenticates the user and returns valid token. If anything fails, {@code null} is returned instead.
     * Prepares {@link org.springframework.security.core.context.SecurityContext} if authentication succeeded.
     */
    TokenInfo authenticate(String login, String password);

    /**
     * Checks the authentication token and if it is valid prepares
     * {@link org.springframework.security.core.context.SecurityContext} and returns true.
     */
    boolean checkToken(String token);

    /**
     * Logouts the user - token is invalidated/forgotten.
     */
    void logout(String token);

    /**
     * Returns current user or {@code null} if there is no authentication or user is anonymous.
     */
    UserDetails currentUser();
}
