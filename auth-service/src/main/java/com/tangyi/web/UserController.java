
package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.MapperUser;
import com.tangyi.dto.DtoUser;
import com.tangyi.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;


/**
 * Created by tangyi on 2017/2/14.
 */
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${my.localFilepath}")
    private String localFilepath;

    @Value("${my.fileServer}")
    private String fileServer;

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取用户列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<MapperUser> getUserList(@RequestParam(required = false) Integer pageIndex,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<MapperUser> mapperUsers = userService.getUserList();
        PageInfo pageInfo = new PageInfo(mapperUsers);
        return pageInfo;
    }

    @ApiOperation(value = "创建用户", notes = "创建用户")
    @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "MapperUser")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postUser(@RequestBody MapperUser user) {
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取用户信息", notes = "根据用户id获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public MapperUser getUserById(@RequestParam String id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "获取用户信息", notes = "根据用户name获取用户详细信息")
    @ApiImplicitParam(name = "name", value = "用户name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<MapperUser> getUserFuzzyByName(@RequestParam String name) {
        //模糊查询
        return userService.getUserFuzzy(name);
    }

    @ApiOperation(value = "更新用户信息", notes = "根据用户id更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "MapperUser")
    })
    @RequestMapping(value = "/id", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putUser(@RequestBody MapperUser user) {
        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/id", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "获取用户信息", notes = "根据用户名获取用户详细信息")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public MapperUser getUser(Principal principal) {
        MapperUser user = null;
        if(principal != null) {
            user = userService.getUserByName(principal.getName());
        }
        return user;
    }

    @ApiOperation(value = "注册", notes = "用户注册")
    @ApiImplicitParam(name = "dtoUser", value = "用户实体", required = true, dataType = "DtoUser")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registry(@RequestBody DtoUser dtoUser) {
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);//将密码加密
        dtoUser.setPassword(bc.encode(dtoUser.getPassword()));
        userService.registry(dtoUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 注册时验证用户名是否存在
     * true:用户名已存在
     * false:用户名不存在，可以使用此用户名注册
     * @param username
     * @return
     */
    @ApiOperation(value = "注册时验证用户名是否存在", notes = "注册时验证用户名是否存在")
    @RequestMapping(value = "/register/name", method = RequestMethod.GET)
    public boolean getUserByName(@RequestParam String username) {
        if(userService.getUserByName(username) == null) {
            return true;
        }else {
            return false;
        }
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParam(name = "dtoUser", value = "用户", required = true, dataType = "DtoUser")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?> changePassword(@RequestBody DtoUser dtoUser, Principal principal) {
        String username = dtoUser.getUsername();
        if(username == null) {
            username = principal.getName();
        }
        MapperUser user = userService.getUserByName(username);
        if(user == null) {
            logger.error("修改密码->用户名不存在！");
            return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
        //判断旧密码是否匹配
        if(bc.matches(dtoUser.getOldPwd(),user.getPassword())) {
            //更新密码
            user.setPassword(bc.encode(dtoUser.getNewPwd()));
            userService.updateUser(user);
        }else {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public ResponseEntity<?>  uploadImg(HttpServletRequest request, Principal principal) {
        //获取当前用户信息
        MapperUser user = null;
        if(principal != null) {
            user = userService.getUserByName(principal.getName());
        }
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if(multipartResolver.isMultipart(request)){
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //这里的name为fileItem的alias属性值，相当于form表单中name
                String name=(String)iter.next();
                //根据name值拿取文件
                MultipartFile file = multiRequest.getFile(name);
                if(file != null){
                    String[] names = file.getOriginalFilename().split("\\.");
                    String fileName = user.getUsername() + "." + names[1];
                    File localFile = new File(localFilepath + fileName);
                    if(!localFile.getParentFile().exists()) {
                        //如果目标文件所在的目录不存在，则创建父目录
                        localFile.getParentFile().mkdirs();
                    }
                    //写文件到本地
                    try {
                        file.transferTo(localFile);
                        //更新用户信息
                        user.setAvatar(fileServer + fileName);
                        userService.updateUser(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
            }
        }else {
            return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}

