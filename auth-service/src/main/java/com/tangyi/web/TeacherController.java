package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.Grade;
import com.tangyi.domain.MapperUser;
import com.tangyi.domain.Student;
import com.tangyi.domain.Teacher;
import com.tangyi.service.TeacherService;
import com.tangyi.service.UserService;
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
@RequestMapping("/v1/teachers")
public class TeacherController {

    private static Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Teacher> getTeacherList(@RequestParam(required = false) Integer pageIndex,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Teacher> teachers = teacherService.getTeacherList();
        PageInfo pageInfo = new PageInfo(teachers);
        return pageInfo;
    }

    @ApiOperation(value = "新增教师", notes = "新增教师")
    @ApiImplicitParam(name = "teacher", value = "教师实体teacher", required = true, dataType = "Teacher")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postTeacher(@RequestBody Teacher teacher) {
        /**
         * 1.已经注册
         * 2.不能重复添加
         * 3.分配权限
         */
        MapperUser user = userService.getUserByName(teacher.getTeacherName());
        if(user != null && teacherService.getTeacherByName(teacher.getTeacherName()) == null) {
            //绑定id
            teacher.setId(user.getId());
            teacherService.saveTeacher(teacher);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "根据名字获取教师信息", notes = "根据教师名字获取教师详细信息")
    @ApiImplicitParam(name = "name", value = "教师名字", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{name}/name", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Teacher> getTeacherByName(@PathVariable String name) {
        return teacherService.getTeacherFuzzy(name);
    }

    @ApiOperation(value = "获取教师信息", notes = "根据教师id获取教师详细信息")
    @ApiImplicitParam(name = "id", value = "教师ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Teacher getTeacherById(@PathVariable String id) {
        return teacherService.getTeacherById(id);
    }

    @ApiOperation(value = "获取教师班级信息", notes = "根据教师name获取班级详细信息")
    @ApiImplicitParam(name = "name", value = "教师name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name/grade/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Grade> getGradeByTeacher(@PathVariable String name) {
        return teacherService.findGradeByName(name);
    }

    @ApiOperation(value = "获取教师学生信息", notes = "根据教师name获取学生详细信息")
    @ApiImplicitParam(name = "name", value = "教师name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name/student/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Student> getStudentByTeacher(@PathVariable String name) {
        return teacherService.findStudentByName(name);
    }

    @ApiOperation(value = "更新教师信息", notes = "根据教师id更新教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "teacher", value = "教师实体", required = true, dataType = "Teacher")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putTeacher(@PathVariable String id, @RequestBody Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除教师", notes = "根据教师id删除教师")
    @ApiImplicitParam(name = "id", value = "教师ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

