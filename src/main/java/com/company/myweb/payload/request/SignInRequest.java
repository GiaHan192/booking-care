package com.company.myweb.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
