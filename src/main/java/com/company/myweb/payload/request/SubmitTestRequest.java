package com.company.myweb.payload.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubmitTestRequest {
    private String email;
    private String fullName;
    private List<SubmitQuestion> content = new ArrayList<>();

    @Data
    public static class SubmitQuestion {
        private Long questionId;
        private Long answerId;
    }
}

