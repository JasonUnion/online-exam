package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.Grade;
import com.tangyi.domain.Student;
import com.tangyi.dto.Dto;
import com.tangyi.dto.DtoGrade;
import com.tangyi.service.GradeService;
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
 * Created by tangyi on 2017/2/26.
 */
@RestController
@RequestMapping("/v1/grades")
public class GradeController {

    private static Logger logger = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    GradeService gradeService;

    @ApiOperation(value = "获取班级树列表", notes = "")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<DtoGrade> getGradeTreeList() {
        return gradeService.getGradeTreeList();
    }

    @ApiOperation(value = "以分页方式获取班级列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Grade> getGradeList(@RequestParam(required = false) Integer pageIndex,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Grade> grades = gradeService.getGradeList();
        PageInfo pageInfo = new PageInfo(grades);
        return pageInfo;
    }

    @ApiOperation(value = "根据名字获取班级信息", notes = "根据班级名称获取班级详细信息")
    @ApiImplicitParam(name = "name", value = "班级名称", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{name}/name", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Grade> getGradeByName(@PathVariable String name) {
        return gradeService.getGradeFuzzy(name);
    }

    @ApiOperation(value = "新增班级", notes = "新增班级")
    @ApiImplicitParam(name = "grade", value = "班级实体grade", required = true, dataType = "Grade")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postGrade(@RequestBody Grade grade) {
        if(gradeService.getGradeByName(grade.getGradeName()) != null) {
            return new ResponseEntity<Object>(new Dto("班级已存在！"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        gradeService.saveGrade(grade);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取班级信息", notes = "根据班级id获取班级详细信息")
    @ApiImplicitParam(name = "id", value = "班级ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Grade getGrade(@PathVariable String id) {
        return gradeService.getGradeById(id);
    }

    @ApiOperation(value = "更新班级信息", notes = "根据班级id更新班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "班级ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "grade", value = "班级实体", required = true, dataType = "Grade")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putGrade(@RequestBody Grade grade) {
        gradeService.updateGrade(grade);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除班级", notes = "根据班级id删除班级")
    @ApiImplicitParam(name = "id", value = "班级ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteGrade(@PathVariable String id) {
        try {
            gradeService.deleteGrade(id);
        }catch (RuntimeException e) {
            return new ResponseEntity(new Dto("删除失败，该班级还有学生！"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "获取学生信息", notes = "根据班级name获取班级学生信息")
    @ApiImplicitParam(name = "name", value = "班级name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name/student/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Student getGradeStudents(@PathVariable String name) {
        return gradeService.findStudentByName(name);
    }

    @ApiOperation(value = "获取班级名称信息", notes = "根据班级名称获取班级名称信息")
    @ApiImplicitParam(name = "name", value = "班级name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name/class/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Grade getGradeClassNames(@PathVariable String name) {
        return gradeService.findClassNameByName(name);
    }

}
