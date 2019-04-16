package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.Subject;
import com.tangyi.dto.Dto;
import com.tangyi.service.SubjectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangyi on 2017/3/20.
 */
@RestController
@RequestMapping(value = "/v1/subjects")
public class SubjectController {

    private static Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    SubjectService subjectService;

    @ApiOperation(value = "获取科目列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Subject> getSubjectList(@RequestParam(required = false) Integer pageIndex,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Subject> subjects = subjectService.getSubjectList();
        PageInfo pageInfo = new PageInfo(subjects);
        return pageInfo;
    }

    @ApiOperation(value = "根据名字获取科目信息", notes = "根据科目名称获取科目详细信息")
    @ApiImplicitParam(name = "name", value = "科目名称", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{name}/name", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Subject> getSubjectByName(@PathVariable String name) {
        return subjectService.getSubjectFuzzy(name);
    }


    @ApiOperation(value = "获取课程信息", notes = "根据课程id获取课程详细信息")
    @ApiImplicitParam(name = "idOrName", value = "课程ID或名称", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/search/{idOrName}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Subject> getSubjectForSearch(@PathVariable String idOrName) {
        List<Subject> subjects = new ArrayList<Subject>();
        Subject subject = subjectService.getSubjectByName(idOrName);
        if (subject == null) {
            try {
                subject = subjectService.getSubjectById(idOrName);
            } catch (Exception e) {

            }
        }
        if (subject != null) {
            subjects.add(subject);
        }
        return subjects;
    }

    @ApiOperation(value = "创建课程", notes = "创建课程")
    @ApiImplicitParam(name = "subject", value = "课程实体Subject", required = true, dataType = "Subject")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postSubject(@RequestBody Subject subject) {
        if(subjectService.getSubjectByName(subject.getName()) != null) {
            return new ResponseEntity<Object>(new Dto("课程已存在！"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        subjectService.saveSubject(subject);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取课程信息", notes = "根据课程id获取课程详细信息")
    @ApiImplicitParam(name = "id", value = "课程ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Subject getSubject(@PathVariable String id) {
        return subjectService.getSubjectById(id);
    }

    @ApiOperation(value = "更新课程信息", notes = "根据课程id更新用户信息")
    @ApiImplicitParam(name = "subject", value = "课程实体", required = true, dataType = "Subject")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putSubject(@RequestBody Subject subject) {
        subjectService.updateSubject(subject);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除课程", notes = "根据课程id删除课程")
    @ApiImplicitParam(name = "id", value = "课程ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteSubject(@PathVariable String id) {
        try {
            subjectService.deleteSubject(id);
        }catch (RuntimeException e) {
            return new ResponseEntity(new Dto("该课程包含有考试，不能删除"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}

