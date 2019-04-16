package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.ClassName;
import com.tangyi.mapper.ClassNameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 班级名称服务层
 * Created by tangyi on 2017/3/26.
 */
@Service
public class ClassNameService {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    ClassNameMapper classNameMapper;

    @Autowired
    private CacheService cacheService;

    /**
     * 根据名称获取数据
     * @param name
     * @return
     */
    public ClassName getByName(String name){
        //获取缓存数据
        ClassName className = (ClassName) cacheService.get(RedisKey.CLASSNAME_NAME + name);
        if(className == null) {
            className = classNameMapper.findByClassName(name);
            cacheService.set(RedisKey.CLASSNAME_NAME + name, className);
        }
        return className;
    }
}
