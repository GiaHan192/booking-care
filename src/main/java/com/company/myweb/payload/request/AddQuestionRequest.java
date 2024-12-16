package com.company.myweb.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddQuestionRequest {
    private String question;
    private List<String> options = new ArrayList<>();
    private List<Float> answers = new ArrayList<>();
}
