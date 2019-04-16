package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.MapperUser;
import com.tangyi.domain.Student;
import com.tangyi.dto.Dto;
import com.tangyi.dto.DtoPage;
import com.tangyi.dto.DtoStudent;
import com.tangyi.service.StudentService;
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
@RequestMapping("/v1/students")
public class StudentController {

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取学生列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Student> getStudentList(@RequestBody DtoPage page) {
        if(page.getPageIndex() != null && page.getPageSize() != null) {
            PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        }
        List<Student> students = studentService.getStudentList();
        PageInfo pageInfo = new PageInfo(students);
        return pageInfo;
    }

    @ApiOperation(value = "根据老师信息获取学生列表", notes = "根据老师信息获取学生列表")
    @ApiImplicitParam(name = "name", value = "老师名字", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/teacher/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Student> getStudentListByTeacher(@PathVariable String name,
                                                     @RequestParam(required = false) Integer pageIndex,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) Integer limit,
                                                     @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Student> students = studentService.findStudentListByTeacher(name);
        PageInfo pageInfo = new PageInfo(students);
        return pageInfo;
    }

    @ApiOperation(value = "新增学生", notes = "新增学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教师id", required = true, dataType = "path"),
            @ApiImplicitParam(name = "student", value = "学生实体student", required = true, dataType = "DtoStudent")
    })
    @RequestMapping(value = "/{id}/teacher", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postStudent(@PathVariable String id, @RequestBody DtoStudent dtoStudent) {
        MapperUser user = userService.getUserExcludeAuthority(dtoStudent.getStudentName());
        if(user == null) {
            return new ResponseEntity(new Dto("用户未注册！"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Student student = studentService.getStudent(user.getId());
        if(user != null && student == null) {
            try {
                studentService.saveStudent(dtoStudent, user, id);
            }catch (Exception e) {
                logger.error("保存学生信息失败！");
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/name/{name}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public MapperUser checkStudent(@PathVariable String name) {
        return userService.getUserExcludeAuthority(name);
    }

    @ApiOperation(value = "获取学生信息", notes = "根据学生id获取学生详细信息")
    @ApiImplicitParam(name = "id", value = "学生ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Student getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @ApiOperation(value = "获取学生信息", notes = "根据学生id获取学生详细信息")
    @ApiImplicitParam(name = "id", value = "学生ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}/grade", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Student getStudentAndGrade(@PathVariable String id) {
        return studentService.getStudentAndGrade(id);
    }

    @ApiOperation(value = "获取学生信息", notes = "根据学生name获取学生详细信息")
    @ApiImplicitParam(name = "name", value = "学生name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{studentName}/teacher/{teacherName}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE:TEACHER') or hasAuthority('ROLE:ADMIN')")
    public List<Student> getStudentByTeacher(@PathVariable String studentName, @PathVariable String teacherName) {

        //模糊查询
        return studentService.getStudentFuzzy(studentName, teacherName);
    }

    @ApiOperation(value = "更新学生信息", notes = "根据学生id更新学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "学生ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "student", value = "学生实体", required = true, dataType = "Student")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putStudent(@PathVariable String id, @RequestBody Student student) {
        studentService.updateStudent(student);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除学生", notes = "根据学生id删除学生")
    @ApiImplicitParam(name = "id", value = "学生ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

