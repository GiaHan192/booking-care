package com.company.myweb.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum Role {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");
    private final String name;

    Role(String name) {
        this.name = name;
    }

}
