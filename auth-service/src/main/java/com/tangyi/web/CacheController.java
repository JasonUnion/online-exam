package com.tangyi.web;

import com.tangyi.constant.Role;
import com.tangyi.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存Controller层
 * Created by tangyi on 2017-04-19.
 */
@RestController
@RequestMapping("/v1/caches")
public class CacheController {

    @Autowired
    CacheService cacheService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> flushAll() {
        try{
            cacheService.flushAll();
        }catch (Exception e) {

        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
