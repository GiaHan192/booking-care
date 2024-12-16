package com.company.myweb.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    private String fullName;
    @Email(message = "Email invalid")
    private String email;
    @NotBlank
    @Size(min=4, message = "Message must has at least 4 character")
    private String password;
    @NotBlank
    private String roleName;
}
