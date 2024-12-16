package com.company.myweb.controller.advice.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class ErrorDetail {
    @Setter
    private HttpStatus status;
    @Setter
    private String message;
    @Setter
    private List<String> errors;
    private final Date date = new Date(System.currentTimeMillis());

    public ErrorDetail(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorDetail(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

}
