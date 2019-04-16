package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.UserAuthority;
import com.tangyi.mapper.UserAuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 用户权限服务层
 * Created by tangyi on 2017/3/29.
 */

@Service
@Transactional(readOnly = true)
public class UserAuthorityService {

    private static Logger logger = LoggerFactory.getLogger(UserAuthorityService.class);

    @Autowired
    UserAuthorityMapper userAuthorityMapper;

    @Autowired
    CacheService cacheService;

    public List<UserAuthority> getUserAuthorityList() {
        return userAuthorityMapper.findAll();
    }

    public UserAuthority getUserAuthority(String id) {
        return userAuthorityMapper.findById(id);
    }

    @Transactional(readOnly = false)
    public Integer saveUserAuthority(String userId, List<UserAuthority> userAuthority) {
        //先删除，再新增
        userAuthorityMapper.deleteByUserId(userId);
        int count = 0;
        for(UserAuthority authority : userAuthority) {
            UserAuthority result = userAuthorityMapper.findByUserIdAndAuthorityId(authority);
            if(result == null) {
                authority.setUserId(userId);
                count += userAuthorityMapper.insert(authority);
            }
        }
        cacheService.del(RedisKey.USER_AUTHORITY_ID + userId);
        return count;
    }

    @Transactional(readOnly = false)
    public void deleteUserAuthority(Long id) {
        userAuthorityMapper.delete(id);
        cacheService.del(RedisKey.USER_AUTHORITY_ID + id);
    }


    /**
     * 根据用户id获取权限
     * @param userId
     * @return
     */
    public List<UserAuthority> findByUserId(String userId) {
        List<UserAuthority> userAuthorities = (List<UserAuthority>) cacheService.get(RedisKey.USER_AUTHORITY_ID + userId);
        if(userAuthorities == null) {
            userAuthorities = userAuthorityMapper.findByUserId(userId);
            cacheService.set(RedisKey.USER_AUTHORITY_ID + userId, userAuthorities);
        }
        return userAuthorities;
    }
}

