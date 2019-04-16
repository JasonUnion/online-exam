package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.MapperUser;
import com.tangyi.domain.User;
import com.tangyi.mapper.UserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by tangyi on 2017/2/7.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    CacheService cacheService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取缓存数据
        MapperUser mapperUser = (MapperUser) cacheService.get(RedisKey.USER_NAME + username);
        if(mapperUser == null) {
            mapperUser  = userMapper.findByUsername(username);
            cacheService.set(RedisKey.USER_NAME + username, mapperUser);
        }

        if(mapperUser == null) {
            throw new UsernameNotFoundException("用户名"+ username + "不存在");
        }
        User user = new User();

        try {
            BeanUtils.copyProperties(user, mapperUser);
        }catch (IllegalAccessException e) {
            logger.warn("loadUserByUsername->BeanUtils.copyProperties", e);
        }catch (InvocationTargetException t) {
            logger.warn("loadUserByUsername->BeanUtils.copyProperties", t);
        }

       /* user.setId(mapperUser.getId());
        user.setUsername(mapperUser.getUsername());
        user.setPassword(mapperUser.getPassword());
        user.setFirstname(mapperUser.getFirstName());
        user.setLastname(mapperUser.getLastName());
        user.setEmail(mapperUser.getEmail());
        user.setGender(mapperUser.getGender());
        user.setClassName(mapperUser.getClassName());
        user.setExamNumber(mapperUser.getExamNumber());
        user.setLastPasswordResetDate(mapperUser.getLastPasswordResetDate());
        user.setAuthorities(mapperUser.getAuthorities());*/
        return user;
    }
}
