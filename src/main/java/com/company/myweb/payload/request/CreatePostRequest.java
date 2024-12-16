package com.company.myweb.payload.request;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title = "";
    private String brief = "";
    private String content = "";
    private Boolean isVisible = false;
    private String thumbnail;
}
