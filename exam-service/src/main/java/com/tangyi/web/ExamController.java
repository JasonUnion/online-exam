package com.tangyi.web;

import com.tangyi.constant.Role;
import com.tangyi.domain.*;
import com.tangyi.dto.DtoAnswerPaper;
import com.tangyi.dto.DtoResult;
import com.tangyi.dto.DtoRightAndWrong;
import com.tangyi.service.*;
import com.tangyi.utils.IdGen;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 考试控制层，负责试卷提交等
 * Created by tangyi on 2017/3/29.
 */
@RestController
@RequestMapping("/v1/exam")
public class ExamController {

    @Autowired
    ExamService examService;

    @Autowired
    AnswerPaperService answerPaperService;

    @Autowired
    AnswerQuestionService answerQuestionService;

    @Autowired
    AnswerPaperQuestionService answerPaperQuestionService;

    @Autowired
    QuestionService questionService;

    @Autowired
    PaperService paperService;

    @Autowired
    WrongQuestionService wrongQuestionService;

    @Autowired
    PaperAnswerPaperService paperAnswerPaperService;

    @ApiOperation(value = "根据试卷id和题目编号获取题目信息", notes = "根据题目id获取题目详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "试卷ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "number", value = "题目编号", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/questions/{number}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public Question getQuestionByPaperIdAndQuestionId(@RequestParam String paperId,
                                                      @RequestParam String username,
                                                      @RequestParam(required = false) String answerPaperId,
                                                      @PathVariable Integer number) {
        Question question = null;
        AnswerQuestion answerQuestion = null;
        if(answerPaperId == null) {
            Paper paper = paperService.getPaperById(paperId);
            if(paper != null) {
                AnswerPaper answerPaper = answerPaperService.findByAnswerUserAndPaperName(username, paper.getName());
                if(answerPaper != null) {
                    answerQuestion = answerQuestionService.getAnswerQuestionByPaperIdAndQuestionNumber(answerPaper.getId(), number);
                }
            }
        }else {
            answerQuestion = answerQuestionService.getAnswerQuestionByPaperIdAndQuestionNumber(answerPaperId, number);
        }

        if(answerQuestion == null) {
            question = questionService.getQuestionByPaperIdAndQuestionNumber(paperId, number);
            if(question != null) {
                //答案不返回
                question.setAnswer("");
            }
        } else {
            question = new Question();
            question.setId(answerQuestion.getId());
            question.setNumber(answerQuestion.getNumber());
            question.setTitle(answerQuestion.getTitle());
            question.setScore(answerQuestion.getScore());
            question.setType(answerQuestion.getType());
            question.setOptionA(answerQuestion.getOptionA());
            question.setOptionB(answerQuestion.getOptionB());
            question.setOptionC(answerQuestion.getOptionC());
            question.setOptionD(answerQuestion.getOptionD());
            question.setAnswer(answerQuestion.getAnswer());
        }
        return question;
    }

    @RequestMapping(value = "/submit/{type}/{username}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?> submit(@RequestBody Paper paper, @PathVariable String type,
                                    @PathVariable String username,
                                    @RequestParam(required = false) String answerPaperId) {
        /**
         * 更改试卷状态，finished:true
         */
        if(type.equals("official")) {
            /**
             * 正式考试
             */
            AnswerPaper answerPaper = new AnswerPaper();
            if(answerPaperId != null) {
                answerPaper.setId(answerPaperId);
            }else {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            answerPaper.setAnswerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            answerPaper.setPaperName(paper.getName());
            answerPaper.setAnswerUser(username);
            answerPaper.setChecked("false");
            answerPaper.setFinished("true");
            answerPaper.setType("official");
            examService.updateAnswerPaper(answerPaper);
        } else if(type.equals("simulate")) {
            /**
             * 模拟考试
             */
            AnswerPaper answerPaper = new AnswerPaper();
            if(answerPaperId != null) {
                answerPaper.setId(answerPaperId);
            }else {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            answerPaper.setAnswerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            answerPaper.setPaperName(paper.getName());
            answerPaper.setAnswerUser(username);
            answerPaper.setChecked("false");
            answerPaper.setFinished("true");
            answerPaper.setType("simulate");
            examService.updateAnswerPaper(answerPaper);
        }else if(type.equals("practice")) {
            /**
             * 1.接收提交的试卷
             * 2.计算成绩
             * 3.记录考试记录
             * 4.返回计算结果
             */
            int score = 0;

            //正确题目数
            double right = 0.0;

            //错误题目数
            double wrong = 0.0;

            double correctRate = 0.0;

            List<Question> questions = questionService.getQuestionByPaperId(paper.getId());

            AnswerPaper answerPaper = answerPaperService.findByAnswerUserAndPaperName(username, paper.getName());

            List<AnswerQuestion> answerQuestions = answerQuestionService.findByAnswerPaperId(answerPaper.getId());

            /*保存题目信息，返回给前端*/
            List<DtoRightAndWrong> results = new ArrayList<DtoRightAndWrong>();

            DtoRightAndWrong dtoRightAndWrong = null;

            //遍历提交的试卷的题目
            for(AnswerQuestion answerQuestion : answerQuestions) {

                //遍历包含正确答案的题目
                for(Question question : questions) {
                    /**
                     * 1.题目序号相同
                     * 2.结果与答案相同
                     */
                    if(answerQuestion.getNumber().equals(question.getNumber())) {
                        if(answerQuestion.getAnswer().equals(question.getAnswer())) {
                            /*累计得分*/
                            score += Integer.parseInt(question.getScore());
                            right ++;
                        }else {
                            wrong ++;
                            //记录错题
                            dtoRightAndWrong = new DtoRightAndWrong();
                            dtoRightAndWrong.setQuestion(question);
                            dtoRightAndWrong.setAnswerQuestion(answerQuestion);
                            results.add(dtoRightAndWrong);

                            //保存错题
                            WrongQuestion wrongQuestion = new WrongQuestion();
                            try{
                                BeanUtils.copyProperties(wrongQuestion, answerQuestion);
                                wrongQuestion.setUsername(username);
                                wrongQuestion.setRightAnswer(question.getAnswer());
                                wrongQuestion.setAnalysis(question.getAnalysis());
                                if(wrongQuestionService.getWrongQuestion(wrongQuestion.getId()) == null) {
                                    wrongQuestionService.saveQuestion(wrongQuestion);
                                }
                            }catch (Exception e) {
                                System.out.println(wrongQuestion.toString());
                            }

                        }
                    }
                }
            }
            //计算正确率
            correctRate = (right/(right + wrong)) * 100;

            DtoResult result = new DtoResult();
            result.setScore(score);
            result.setRight(right);
            result.setWrong(wrong);
            result.setCorrectRate(correctRate);
            result.setResults(results);

            Paper paper1 = paperService.getPaperById(paper.getId());
            //更新参与人数
            paper1.setPeoples(String.valueOf(Integer.parseInt(paper1.getPeoples()) + 1));
            paperService.updatePaper(paper1);

            return new ResponseEntity<Object>(result, HttpStatus.OK);
        }
        Paper paper1 = paperService.getPaperById(paper.getId());
        //更新参与人数
        paper1.setPeoples(String.valueOf(Integer.parseInt(paper1.getPeoples() + 1)));
        paperService.updatePaper(paper1);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    /**
     * 提交题目
     * @param username
     * @param dtoAnswerPaper
     * @return
     */
    @RequestMapping(value = "/submit/one/{username}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?> submitOne(@PathVariable String username, @RequestBody DtoAnswerPaper dtoAnswerPaper) {
        Paper paper = dtoAnswerPaper.getPaper();
        Question question = dtoAnswerPaper.getQuestion();
        //判断数据库是否保存了这次答卷
        AnswerPaper answerPaper = answerPaperService.getAnswerPaperByNameAndUser(paper.getName(), username);
        AnswerQuestion answerQuestion = null;
        AnswerPaperQuestion answerPaperQuestion = null;
        List<AnswerQuestion> answerQuestions = null;
        //重新生成id
        String answerPaperId = IdGen.uuid();
        String answerQuestionId = IdGen.uuid();
        //答卷为空，则执行保存
        if(answerPaper == null) {
            answerPaper = new AnswerPaper();
            answerPaper.setId(answerPaperId);
            answerPaper.setAnswerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            answerPaper.setPaperName(paper.getName());
            answerPaper.setType(paper.getType());
            answerPaper.setAnswerUser(username);
            answerPaper.setChecked("false");
            answerPaper.setFinished("false");

            //保存答卷
            answerPaperService.saveAnswerPaper(answerPaper);

            // TODO: 2017-04-17 保存试卷答卷
            PaperAnswerPaper paperAnswerPaper = new PaperAnswerPaper();
            paperAnswerPaper.setPaperId(paper.getId());
            paperAnswerPaper.setAnswerPaperId(answerPaperId);
            paperAnswerPaperService.save(paperAnswerPaper);

            //新记录
            answerQuestion = new AnswerQuestion();
            //初始化信息
            answerQuestion.setId(answerQuestionId);
            answerQuestion.setTitle(question.getTitle());
            answerQuestion.setType(question.getType());
            answerQuestion.setNumber(question.getNumber());
            answerQuestion.setOptionA(question.getOptionA());
            answerQuestion.setOptionB(question.getOptionB());
            answerQuestion.setOptionC(question.getOptionC());
            answerQuestion.setOptionD(question.getOptionD());
            answerQuestion.setContent(question.getContent());
            answerQuestion.setScore(question.getScore());
            answerQuestion.setAnalysis(question.getAnalysis());
            answerQuestion.setAnswer(question.getAnswer());


            answerPaperQuestion = new AnswerPaperQuestion();
            answerPaperQuestion.setAnswerPaperId(answerPaper.getId());
            answerPaperQuestion.setAnswerQuestionId(answerQuestionId);

            //保存
            answerQuestionService.saveAnswerQuestion(answerQuestion);
            answerPaperQuestionService.saveAnswerPaperQuestion(answerPaperQuestion);

            return new ResponseEntity<Object>(answerPaper, HttpStatus.OK);
        } else {
            answerQuestions = answerQuestionService.findByAnswerPaperId(answerPaper.getId());
            if(answerQuestions != null && answerQuestions.size() > 0) {
                int count = 0;
                AnswerQuestion existAnswerQuestion = null;
                for(AnswerQuestion question1 : answerQuestions) {
                    if (question1.getNumber().equals(question.getNumber())) {
                        count++;
                        existAnswerQuestion = question1;//保存当前存在的记录
                    }
                }
                //记录不存在
                if(count == 0) {
                    //新记录
                    answerQuestion = new AnswerQuestion();
                    answerPaperQuestion = new AnswerPaperQuestion();


                    answerQuestion = new AnswerQuestion();
                    //初始化信息
                    answerQuestion.setId(answerQuestionId);
                    answerQuestion.setTitle(question.getTitle());
                    answerQuestion.setType(question.getType());
                    answerQuestion.setNumber(question.getNumber());
                    answerQuestion.setOptionA(question.getOptionA());
                    answerQuestion.setOptionB(question.getOptionB());
                    answerQuestion.setOptionC(question.getOptionC());
                    answerQuestion.setOptionD(question.getOptionD());
                    answerQuestion.setContent(question.getContent());
                    answerQuestion.setScore(question.getScore());
                    answerQuestion.setAnalysis(question.getAnalysis());
                    answerQuestion.setAnswer(question.getAnswer());


                    answerPaperQuestion = new AnswerPaperQuestion();
                    answerPaperQuestion.setAnswerPaperId(answerPaper.getId());
                    answerPaperQuestion.setAnswerQuestionId(answerQuestionId);

                    //保存
                    answerQuestionService.saveAnswerQuestion(answerQuestion);
                    answerPaperQuestionService.saveAnswerPaperQuestion(answerPaperQuestion);
                } else {
                    //记录存在，则执行更新
                    // TODO: 2017/3/30
                    //更新当前存在的记录
                    existAnswerQuestion.setAnswer(question.getAnswer());

                    answerQuestionService.updateAnswerQuestion(existAnswerQuestion);
                }
            }
        }
        return new ResponseEntity<Object>(answerPaper, HttpStatus.OK);
    }
}
