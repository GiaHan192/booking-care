package com.company.myweb.service;

import com.company.myweb.dto.AnswersDTO;
import com.company.myweb.dto.QuestionDTO;
import com.company.myweb.entity.Answers;
import com.company.myweb.entity.Question;
import com.company.myweb.entity.SubmitAnswer;
import com.company.myweb.entity.Submition;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.payload.request.AddQuestionRequest;
import com.company.myweb.payload.request.SubmitTestRequest;
import com.company.myweb.repository.QuestionRepository;
import com.company.myweb.repository.SubmitRepository;
import com.company.myweb.service.interfaces.IQuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    private final SubmitRepository submitRepository;

    public QuestionService(QuestionRepository questionRepository, ObjectMapper objectMapper, SubmitRepository submitRepository) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
        this.submitRepository = submitRepository;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questionList = questionRepository.findAllByUsing(true);
        if (!questionList.isEmpty()) {
            return questionList.stream().map(
                    question -> {
                        QuestionDTO questionDTO = new QuestionDTO();
                        questionDTO.setId(question.getId());
                        questionDTO.setQuestionTitle(question.getQuestionTitle());
                        List<AnswersDTO> answersDTOList = question.getAnswers().stream().map(
                                answers -> new AnswersDTO(answers.getId(), answers.getAnswers(), answers.getPoint())).toList();
                        questionDTO.setAnswers(answersDTOList);
                        return questionDTO;
                    }).toList();
        } else {
            return List.of();
        }
    }

    @Override
    public Boolean importTest(MultipartFile testFile) {
        if (testFile.isEmpty()) {
            return false;
        }
        try {
            List<Question> currentQuestions = questionRepository.findAllByUsing(true);
            for (Question question : currentQuestions) {
                question.setUsing(false);
            }

            ArrayList<Question> questionList = new ArrayList<>();
            TypeReference<List<AddQuestionRequest>> typeRef = new TypeReference<List<AddQuestionRequest>>() {
            };

            List<AddQuestionRequest> addQuestionRequestList = objectMapper.readValue(testFile.getInputStream(), typeRef);
            for (AddQuestionRequest addQuestionRequest : addQuestionRequestList) {
                Question question = new Question();
                question.setQuestionTitle(addQuestionRequest.getQuestion());

                List<Answers> answersList = new ArrayList<>();
                List<String> options = addQuestionRequest.getOptions();
                for (int index = 0; index < options.size(); index++) {
                    Answers answer = Answers.builder()
                            .answers(options.get(index))
                            .point(addQuestionRequest.getAnswers().get(index))
                            .question(question)
                            .build();
                    answersList.add(answer);
                }
                question.setAnswers(answersList);
                questionList.add(question);
            }
            questionRepository.saveAll(questionList);
            questionRepository.saveAll(currentQuestions);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;  // Return false if an error occurs
        }
    }

    @Override
    public Boolean submitTest(SubmitTestRequest submitTestRequest) {
        try {
            List<SubmitTestRequest.SubmitQuestion> content = submitTestRequest.getContent();
            boolean isContentEmpty = content.isEmpty();
            if (isContentEmpty) {
                throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không thể để trống nội dung kiểm tra");
            }

            // Storing to Repository
            List<Long> questionIds = content
                    .stream()
                    .map(SubmitTestRequest.SubmitQuestion::getQuestionId)
                    .toList();

            Map<Long, Question> questionMap = questionRepository
                    .findAllById(questionIds)
                    .stream()
                    .collect(Collectors.toMap(Question::getId, Function.identity()));
            if (questionMap.keySet().size() != questionIds.size()) {
                throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Câu hỏi không hợp lệ với hệ thống");
            }

            Submition submition = new Submition();
            submition.setFullName(submitTestRequest.getFullName());
            submition.setEmail(submitTestRequest.getEmail());

            for (SubmitTestRequest.SubmitQuestion submitQuestion : content) {
                Question question = questionMap.get(submitQuestion.getQuestionId());
                Optional<Answers> answersOptional = question.getAnswers()
                        .stream()
                        .filter(answers -> Objects.equals(answers.getId(), submitQuestion.getAnswerId()))
                        .findFirst();
                if (answersOptional.isEmpty()) {
                    throw ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Câu trả lời không tồn tại");
                }
                submition.getSubmitAnswers().add(new SubmitAnswer(answersOptional.get(), submition));
            }

            submitRepository.save(submition);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
