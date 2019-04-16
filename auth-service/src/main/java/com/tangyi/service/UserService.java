package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.MapperUser;
import com.tangyi.domain.UserAuthority;
import com.tangyi.dto.DtoUser;
import com.tangyi.mapper.TeacherMapper;
import com.tangyi.mapper.UserAuthorityMapper;
import com.tangyi.mapper.UserMapper;
import com.tangyi.utils.IdGen;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangyi on 2017/2/7.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserAuthorityMapper authorityMapper;

    @Autowired
    TeacherMapper teacherMapper;

    public List<MapperUser> getUserList() {
        return userMapper.findAll();
    }

    @Transactional(readOnly = false)
    public Integer saveUser(MapperUser user) {
        return userMapper.insert(user);
    }

    public MapperUser getUserById(String id) {
        //获取缓存数据
        MapperUser mapperUser = (MapperUser) cacheService.get(RedisKey.USER_ID + id);
        if(mapperUser == null) {
            mapperUser = userMapper.findById(id);
            cacheService.set(RedisKey.USER_ID + id, mapperUser);
        }
        return mapperUser;
    }

    /**
     * 根据用户名查询信息，返回结果包括权限信息
     * @param username
     * @return
     */
    public MapperUser getUserByName(String username) {
        //获取缓存数据
        MapperUser mapperUser = (MapperUser) cacheService.get(RedisKey.USER_NAME + username);
        if(mapperUser == null) {
            mapperUser = userMapper.findByUsername(username);
            if(mapperUser != null) {
                //存入缓存
                cacheService.set(RedisKey.USER_NAME + username, mapperUser);
            }else {
                logger.warn("用户名不存在：" + username);
                return null;
            }

        }
        return mapperUser;
    }

    /**
     * 根据用户名查询信息，返回结果不包括权限信息
     * @param username
     * @return
     */
    public MapperUser getUserExcludeAuthority(String username) {
        return userMapper.findByUsernameExcludeAuthority(username);
    }

    public List<MapperUser> getUserFuzzy(String username) {
        return userMapper.fuzzyFindByUsername(username);
    }

    @Transactional(readOnly = false)
    public  void updateUser(MapperUser user) {

        MapperUser mapperUser = userMapper.findById(user.getId());
        if(user.getUsername() != null) {
            mapperUser.setUsername(user.getUsername());
        }
        if(user.getEmail() != null) {
            mapperUser.setEmail(user.getEmail());
        }
        if(user.getGender() != null) {
            mapperUser.setGender(user.getGender());
        }
        if(user.getAvatar() != null) {
            mapperUser.setAvatar(user.getAvatar());
        }

        if(user.getAddress() != null) {
            mapperUser.setAddress(user.getAddress());
        }

        if(user.getIdentityCard() != null) {
            mapperUser.setIdentityCard(user.getIdentityCard());
        }

        if(user.getIntroduction() != null) {
            mapperUser.setIntroduction(user.getIntroduction());
        }

        if(user.getMobilePhone() != null) {
            mapperUser.setMobilePhone(user.getMobilePhone());
        }

        if(user.getAvatar() != null) {
            mapperUser.setAvatar(user.getAvatar());
        }

        if(user.getBirthday() != null) {
            mapperUser.setBirthday(user.getBirthday());
        }

        if(user.getPassword() != null) {
            mapperUser.setPassword(user.getPassword());
        }
        userMapper.update(mapperUser);

        //删除缓存数据
        cacheService.del(RedisKey.USER_NAME + user.getUsername());
        cacheService.del(RedisKey.USER_ID + user.getId());
    }

    @Transactional(readOnly = false)
    public void deleteUser(String id) {
        userMapper.delete(id);
        //清理缓存
        cacheService.del(RedisKey.USER_ID + id);
    }

    @Transactional(readOnly = false)
    public void registry(DtoUser dtoUser) {
        MapperUser user = new MapperUser();
        try {
            BeanUtils.copyProperties(user, dtoUser);
        }catch (Exception e) {
            logger.error("注册失败！");
        }
        String userId = IdGen.uuid();
        user.setId(userId);
        userMapper.insert(user);
        //注册,分配默认权限：学生
        UserAuthority authority = new UserAuthority();
        authority.setUserId(userId);
        authority.setAuthorityId("3");
        authorityMapper.insert(authority);

    }
}
