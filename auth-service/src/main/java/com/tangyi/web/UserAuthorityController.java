package com.tangyi.web;

import com.tangyi.constant.Role;
import com.tangyi.domain.UserAuthority;
import com.tangyi.service.UserAuthorityService;
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
 * Created by tangyi on 2017/2/15.
 */

@RestController
@RequestMapping(value = "/v1/user-authorities")
public class UserAuthorityController {

    private static Logger logger = LoggerFactory.getLogger(UserAuthorityController.class);

    @Autowired
    UserAuthorityService userAuthorityService;

    @ApiOperation(value = "获取用户权限列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<UserAuthority> getUserAuthorityList() {
        List<UserAuthority> list = userAuthorityService.getUserAuthorityList();
        return list;
    }

 /*   @ApiOperation(value = "新增用户权限", notes = "")
    @ApiImplicitParam(name = "userAuthority", value = "用户权限实体UserAuthority", required = true, dataType = "UserAuthority")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> postUserAuthority(@RequestBody UserAuthority userAuthority) {
        userAuthorityService.saveUserAuthority(userAuthority);
        return new ResponseEntity(HttpStatus.CREATED);
    }*/

    @ApiOperation(value = "新增用户权限", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAuthority", value = "用户权限实体UserAuthority", required = true, dataType = "UserAuthority"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postUserAuthority(@PathVariable String userId, @RequestBody List<UserAuthority> userAuthority) {
        userAuthorityService.saveUserAuthority(userId, userAuthority);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取用户权限", notes = "根据用户id获取用户权限详细信息")
    @ApiImplicitParam(name = "id", value = "用户权限ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public UserAuthority getUserAuthority(@PathVariable String id) {
        return userAuthorityService.getUserAuthority(id);
    }

/* @ApiOperation(value = "更新用户权限信息")
    public String putUserAuthority(@RequestBody UserAuthority userAuthority) {

    }*/

}

