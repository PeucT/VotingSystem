package com.anel.vote.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by PeucT on 13.01.2018.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
