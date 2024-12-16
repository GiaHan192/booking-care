package com.company.myweb.controller;

import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.SubmitTestRequest;
import com.company.myweb.service.interfaces.IQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final IQuestionService questionServiceImp;

    public QuestionController(IQuestionService questionServiceImp) {
        this.questionServiceImp = questionServiceImp;
    }

    @GetMapping("")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionServiceImp.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/import")
    public ResponseEntity<ApiResponse<String>> importQuestionTest(@ModelAttribute MultipartFile file) {

        Boolean importResult = questionServiceImp.importTest(file);
        if (importResult) {
            return ResponseEntity.ok(ApiResponse.success("Thêm bộ câu hỏi thành công "));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.failed("Thêm bộ câu hỏi thẩt bại"));
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<String>> submitTest(@RequestBody SubmitTestRequest submitTestRequest) {
        Boolean result = questionServiceImp.submitTest(submitTestRequest);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success("Nộp bài kiểm tra thành công"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.failed("Nộp bài kiểm tra thất bại"));
        }
    }


}
