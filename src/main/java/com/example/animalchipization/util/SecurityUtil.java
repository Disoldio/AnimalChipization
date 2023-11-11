package com.example.animalchipization.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    public static Optional<String> getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> currentUserName = Optional.ofNullable(authentication.getName());
        return currentUserName;
    }
}
