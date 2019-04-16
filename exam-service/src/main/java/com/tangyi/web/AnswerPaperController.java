package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.AnswerPaper;
import com.tangyi.domain.AnswerQuestion;
import com.tangyi.domain.PaperAnalysis;
import com.tangyi.dto.DtoTask;
import com.tangyi.service.AnswerPaperService;
import com.tangyi.service.AnswerQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 答卷控制层，用于获取已经提交的答卷
 * Created by tangyi on 2017/3/29.
 */
@RestController
@RequestMapping("/v1/answer-papers")
public class AnswerPaperController {

    @Autowired
    AnswerPaperService answerPaperService;

    @Autowired
    AnswerQuestionService answerQuestionService;

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public AnswerPaper getAnswerPaper(@PathVariable String id) {
        return answerPaperService.getAnswerPaperById(id);
    }

    /**
     * 根据name查找
     * @param name
     * @return
     */
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<AnswerPaper> getAnswerPaperByName(@PathVariable String name) {
        return answerPaperService.getAnswerPaperFuzzy(name);
    }

    /**
     * 根据答卷id和题目编号获取题目信息
     * @param paperId
     * @param number
     * @return
     */
    @RequestMapping(value = "/papers/{paperId}/questions/{number}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public AnswerQuestion getQuestionByPaperIdAndQuestionId(@PathVariable String paperId, @PathVariable Integer number) {
        AnswerQuestion answerQuestion = answerQuestionService.getAnswerQuestionByPaperIdAndQuestionNumber(paperId, number);
        return answerQuestion;
    }

    /**
     * 已分页方式获取数据
     * @param username
     * @param pageIndex
     * @param pageSize
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public PageInfo<AnswerPaper> getListByUser(@PathVariable("username") String username,
                                               @RequestParam(required = false) Integer pageIndex,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) Integer limit,
                                               @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<AnswerPaper> answerPapers = answerPaperService.getAnswerPaperListByAnswerUser(username);
        PageInfo pageInfo = new PageInfo(answerPapers);
        return pageInfo;
    }

    @RequestMapping(value = "/users/{username}/type/{type}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public PageInfo<AnswerPaper> getListByUserAndType(@PathVariable("username") String username,
                                                @PathVariable("type") String type,
                                                @RequestParam(required = false) Integer pageIndex,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) Integer limit,
                                                @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<AnswerPaper> answerPapers = answerPaperService.getAnswerPaperListByAnswerUserAndType(username, type);
        PageInfo pageInfo = new PageInfo(answerPapers);
        return pageInfo;
    }

    /**
     * 获取未批改或已批改的答卷数量，
     * @return
     */
    @RequestMapping("/check")
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public DtoTask countUnCheckAnswerPaper() {
        DtoTask dtoTask = new DtoTask();
        Integer checked = answerPaperService.countCheck("true");
        Integer unChecked = answerPaperService.countCheck("false");
        dtoTask.setChecked(checked);
        dtoTask.setUnChecked(unChecked);
        return dtoTask;
    }

    /**
     * 以分页方式获取数据
     * @param pageIndex
     * @param pageSize
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<AnswerPaper> getListByUser(@RequestParam(required = false) Integer pageIndex,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) Integer limit,
                                               @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<AnswerPaper> answerPapers = answerPaperService.getAnswerPaperList();
        PageInfo pageInfo = new PageInfo(answerPapers);
        return pageInfo;
    }

    /**
     * 更新
     * @param answerPaper
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putPaper(@RequestBody AnswerPaper answerPaper) {
        answerPaperService.updatePaper(answerPaper);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 计算考试成绩
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/calculate", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?> CalculationScore(@PathVariable String id) {
        /**
         * 计算成绩
         */
        List<AnswerQuestion> questions = answerQuestionService.findByAnswerPaperId(id);
        if(questions != null && questions.size() > 0) {

            int score = 0;
            try {
                for(AnswerQuestion question : questions) {
                    score += Integer.parseInt(question.getMarkScore());
                }
            } catch (Exception e) {
                // TODO: 2017/4/1
            }

            /**
             * 保存成绩
             */

            AnswerPaper answerPaper = new AnswerPaper();
            answerPaper.setId(id);
            answerPaper.setScore(Integer.toString(score));
            answerPaper.setChecked("true");
            answerPaperService.updatePaper(answerPaper);
        } else {
            // TODO: 2017/4/1
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = "/analysis/paper")
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<PaperAnalysis> analysisPaper() {
        return answerPaperService.analysisPaper();
    }
}
