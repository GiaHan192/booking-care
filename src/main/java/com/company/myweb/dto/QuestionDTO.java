package com.company.myweb.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String questionTitle;
    private List<AnswersDTO> answers;
}
