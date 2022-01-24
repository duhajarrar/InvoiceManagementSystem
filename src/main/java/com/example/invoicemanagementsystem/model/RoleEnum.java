package com.example.invoicemanagementsystem.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {

    ROLE_ADMIN(Code.ROLE_ADMIN),
    ROLE_USER(Code.ROLE_USER),
    ROLE_AUDIOTR(Code.ROLE_AUDIOTR);
    private final String authority;

    RoleEnum(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public class Code {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_AUDIOTR = "ROLE_AUDIOTR";
    }
}