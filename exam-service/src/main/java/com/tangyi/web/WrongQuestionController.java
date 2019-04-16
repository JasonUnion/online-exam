package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.domain.WrongQuestion;
import com.tangyi.service.WrongQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tangyi on 2017-04-08.
 */
@RestController
@RequestMapping("/v1/wrong-questions")
public class WrongQuestionController {

    @Autowired
    WrongQuestionService wrongQuestionService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageInfo<WrongQuestion> getWrongQuestionList(@RequestParam(required = false) Integer pageIndex,
                                                        @RequestParam(required = false) Integer pageSize,
                                                        @RequestParam(required = false) Integer limit,
                                                        @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<WrongQuestion> wrongQuestions = wrongQuestionService.getWrongQuestionList();
        PageInfo pageInfo = new PageInfo(wrongQuestions);
        return pageInfo;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public PageInfo<WrongQuestion> getWrongQuestionListByUsername(@PathVariable String username,
                                                                  @RequestParam(required = false) Integer pageIndex,
                                                                  @RequestParam(required = false) Integer pageSize,
                                                                  @RequestParam(required = false) Integer limit,
                                                                  @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<WrongQuestion> wrongQuestions = wrongQuestionService.getWrongQuestionListByUsername(username);
        PageInfo pageInfo = new PageInfo(wrongQuestions);
        return pageInfo;
    }
}
