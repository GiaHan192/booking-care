package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "submit_answer")
@Data
@NoArgsConstructor
public class SubmitAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "answers_id")
    private Answers answers;
    @ManyToOne
    @JoinColumn(name = "submition_id")
    private Submition submition;

    public SubmitAnswer(Answers answers, Submition submition) {
        this.answers = answers;
        this.submition = submition;
    }
}
