package com.company.myweb.payload.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddTestRequest {
    List<AddQuestionRequest> questions = new ArrayList<>();
}
