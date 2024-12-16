package com.company.myweb.service.interfaces;

import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.payload.request.SubmitTestRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IQuestionService {
    List<QuestionDTO> getAllQuestions();
    Boolean importTest(MultipartFile testFile);
    Boolean submitTest(SubmitTestRequest submitTestRequest);
}
