package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.Authority;
import com.tangyi.domain.UserAuthority;
import com.tangyi.dto.DtoAuthority;
import com.tangyi.mapper.AuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限增删改查
 * Created by tangyi on 2017/2/5.
 */

@Service
@Transactional(readOnly = true)
public class AuthorityService {

    private static Logger logger = LoggerFactory.getLogger(AuthorityService.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    AuthorityMapper authorityMapper;

    @Autowired
    UserAuthorityService userAuthorityService;

    public List<Authority> getAuthorityList() {
        List<Authority> authorities = (List<Authority>) cacheService.get(RedisKey.AUTHORITY_LIST);
        if(authorities == null) {
            authorities = authorityMapper.findAll();
            cacheService.set(RedisKey.AUTHORITY_LIST, authorities);
        }
        return authorities;
    }

    //根据用户id查询权限树
    public List<DtoAuthority> getAuthorityTreeList(String id) {
        //获取所有权限
        List<Authority> authorities = this.getAuthorityList();

        //用户权限列表
        List<UserAuthority> userAuthorities = userAuthorityService.findByUserId(id);

        //处理逻辑
        List<DtoAuthority> dtoAuthorities = new ArrayList<DtoAuthority>();

        DtoAuthority dtoAuthority = null;
        for(Authority authority : authorities) {
            dtoAuthority = new DtoAuthority();
            dtoAuthority.setAuthorityId(authority.getAuthorityId());
            dtoAuthority.setLabel(authority.getName());

            for(UserAuthority userAuthority : userAuthorities) {
                if(userAuthority.getAuthorityId().equals(dtoAuthority.getAuthorityId())) {
                    dtoAuthority.setSelected(true);
                }
            }
            //TODO 暂不支持子级权限配置
            dtoAuthorities.add(dtoAuthority);
        }
        return dtoAuthorities;
    }

    public Authority getAuthority(String id) {
        //获取缓存数据
        Authority authority = (Authority) cacheService.get(RedisKey.AUTHORITY_ID + id);
        if(authority == null) {
            authority = authorityMapper.findById(id);
            cacheService.set(RedisKey.AUTHORITY_ID + id, authority);
        }
        return authority;
    }

    @Transactional(readOnly = false)
    public Integer saveAuthority(Authority authority) {
        return authorityMapper.insert(authority.getAuthority());
    }

    @Transactional(readOnly = false)
    public void updateAuthority(Authority authority) {

        Authority updateAuthority = authorityMapper.findById(authority.getAuthorityId());

        if(authority.getAuthority() != null) {
            updateAuthority.setAuthority(authority.getAuthority());
        }
        if(authority.getName() != null) {
            updateAuthority.setName(authority.getName());
        }
        authorityMapper.update(updateAuthority);
        //清理缓存
        cacheService.del(RedisKey.AUTHORITY_ID + authority.getAuthorityId());
       }

    @Transactional(readOnly = false)
    public void deleteAuthority(String id) {
        authorityMapper.delete(id);
        //清理缓存
        cacheService.del(RedisKey.AUTHORITY_ID + id);
    }
}
