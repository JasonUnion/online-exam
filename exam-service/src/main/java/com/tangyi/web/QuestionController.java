package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.PaperAnswerPaper;
import com.tangyi.domain.Question;
import com.tangyi.service.PaperAnswerPaperService;
import com.tangyi.service.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by tangyi on 2017/3/20.
 */

@RestController
@RequestMapping(value = "/v1/questions")
public class QuestionController {

    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    PaperAnswerPaperService paperAnswerPaperService;

    @ApiOperation(value = "获取题目分页列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Question> getQuestionListByPage(@RequestParam(required = false) Integer pageIndex,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) Integer limit,
                                                    @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Question> questions = questionService.getQuestionList();
        PageInfo pageInfo = new PageInfo(questions);
        return pageInfo;
    }

    @ApiOperation(value = "获取试卷题目分页列表", notes = "")
    @RequestMapping(value = "/papers/{paperId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Question> getQuestionListByPaper(@PathVariable String paperId,
                                                     @RequestParam(required = false) Integer pageIndex,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) Integer limit,
                                                     @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Question> questions = questionService.getQuestionListByPaper(paperId);
        PageInfo pageInfo = new PageInfo(questions);
        return pageInfo;
    }

    @ApiOperation(value = "获取试卷题目数量", notes = "")
    @RequestMapping(value = "/papers/{paperId}/count", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?> getQuestionCountByPaper(@PathVariable String paperId) {
        Integer count = questionService.countByPaperId(paperId);
        return new ResponseEntity<Object>(count, HttpStatus.OK);
    }

    @ApiOperation(value = "创建题目", notes = "创建题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "question", value = "题目实体Question", required = true, dataType = "Question"),
            @ApiImplicitParam(name = "id", value = "试卷id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postQuestion(@PathVariable("id") String id, @RequestBody Question question) {
        questionService.saveQuestion(id, question);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取题目信息", notes = "根据题目id获取题目详细信息")
    @ApiImplicitParam(name = "id", value = "题目ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public Question getQuestion(@PathVariable String id) {
        return questionService.getQuestion(id);
    }

    @ApiOperation(value = "根据试卷id和题目编号获取题目信息", notes = "根据题目id获取题目详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "试卷ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "number", value = "题目编号", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/papers/{paperId}/questions/{number}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public Question getQuestionByPaperIdAndQuestionId(@PathVariable String paperId,
                                                      @PathVariable Integer number,
                                                      @RequestParam(required = false) String answerPaperId) {
        PaperAnswerPaper paperAnswerPaper = null;
        //传入的是答卷Id
        if(answerPaperId != null) {
            // TODO: 2017-04-17
            paperAnswerPaper = paperAnswerPaperService.getByAnswerPaperId(answerPaperId);
            if(paperAnswerPaper != null) {
                return questionService.getQuestionByPaperIdAndQuestionNumber(paperAnswerPaper.getPaperId(), number);
            }else {
                logger.error("根据答卷id获取答卷失败");
            }
        }
        return questionService.getQuestionByPaperIdAndQuestionNumber(paperId, number);
    }

    @ApiOperation(value = "获取题目信息", notes = "根据题目name获取题目详细信息")
    @ApiImplicitParam(name = "name", value = "试卷name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public List<Question> getQuestionByName(@PathVariable String name) {
        //模糊查询
        return questionService.getQuestionFuzzy(name);
    }

    @ApiOperation(value = "获取题目信息", notes = "根据试卷id获取所有题目")
    @ApiImplicitParam(name = "paperId", value = "试卷ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/papers/{paperId}/questions", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public List<Question> getQuestionByPaperId(@PathVariable String paperId) {
        return questionService.getQuestionByPaperId(paperId);
    }

    @ApiOperation(value = "获取题目信息", notes = "根据试卷id获取所有题目,但不返回答案")
    @ApiImplicitParam(name = "paperId", value = "试卷ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/papers/{paperId}/ignore", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public List<Question> getQuestionByPaperIdIgnoreAnswer(@PathVariable String paperId) {
        return questionService.getQuestionByPaperIdIgnoreAnswer(paperId);
    }

    @ApiOperation(value = "更新题目信息", notes = "根据题目id更新题目信息")
    @ApiImplicitParam(name = "question", value = "题目实体", required = true, dataType = "Question")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putQuestion(@RequestBody Question question) {
        questionService.updateQuestion(question);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除题目", notes = "根据题目id删除试卷")
    @ApiImplicitParam(name = "id", value = "题目ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
