package com.tangyi.web;

import com.tangyi.constant.Role;
import com.tangyi.domain.Authority;
import com.tangyi.dto.DtoAuthority;
import com.tangyi.service.AuthorityService;
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
 * Created by tangyi on 2017/2/10.
 */
@RestController
@RequestMapping("/v1/authorities")
public class AuthorityController {

    private static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    AuthorityService authorityService;

    @ApiOperation(value = "获取权限列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Authority> getAuthorityList() {
        return authorityService.getAuthorityList();
    }

    @ApiOperation(value = "获取权限树列表", notes = "")
    @RequestMapping(value = "/tree/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<DtoAuthority> getAuthorityTreeList(@PathVariable String id) {
        return authorityService.getAuthorityTreeList(id);
    }

    @ApiOperation(value = "新增权限", notes = "新增权限")
    @ApiImplicitParam(name = "authority", value = "权限实体authority", required = true, dataType = "Authority")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postAuthority(@RequestBody Authority authority) {
        authorityService.saveAuthority(authority);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取权限信息", notes = "根据权限id获取权限详细信息")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Authority getAuthority(@PathVariable String id) {
        return authorityService.getAuthority(id);
    }

    @ApiOperation(value = "更新权限信息", notes = "根据权限id更新权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "authority", value = "权限实体", required = true, dataType = "Authority")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putAuthority(@PathVariable String id, @RequestBody Authority authority) {
        authorityService.updateAuthority(authority);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除权限", notes = "根据权限id删除用户")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteAuthority(@PathVariable String id) {
        authorityService.deleteAuthority(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

