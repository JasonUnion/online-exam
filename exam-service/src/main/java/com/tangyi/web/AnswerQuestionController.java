package com.tangyi.web;

import com.tangyi.constant.Role;
import com.tangyi.domain.AnswerQuestion;
import com.tangyi.service.AnswerQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tangyi on 2017/3/31.
 */
@RestController
@RequestMapping("/v1/answer-questions")
public class AnswerQuestionController {

    @Autowired
    AnswerQuestionService answerQuestionService;

    /**
     * 获取试卷题目分页列表
     * @param paperId
     * @return
     */
    @RequestMapping(value = "/{paperId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public List<AnswerQuestion> getAnswerQuestionListByPaper(@PathVariable String paperId) {
        return answerQuestionService.findByAnswerPaperId(paperId);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?> putPaper(@RequestBody AnswerQuestion answerQuestion) {
        answerQuestionService.updateAnswerQuestion(answerQuestion);
        return new ResponseEntity(HttpStatus.OK);
    }
}
