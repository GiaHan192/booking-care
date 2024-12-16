package com.company.myweb.payload.request;

import lombok.Data;

@Data
public class EditUserStateRequest {
    private Boolean activate = false;
}
