package org.example.conf;

import org.example.exception.AuthenticationException;

public class AuthenticationContext {
    private static final ThreadLocal<String> authenticatedUser = new ThreadLocal<>();

    public static void authenticate(String username) {
        authenticatedUser.set(username);
    }

    public static void clear() {
        authenticatedUser.remove();
    }

    public static String getAuthenticatedUser() {
        return authenticatedUser.get();
    }

    public static void requireAuthentication() {
        if (authenticatedUser.get() == null) {
            throw new AuthenticationException("User is not authenticated!");
        }
    }
}
