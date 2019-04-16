/*
package com.tangyi.web;

import com.tangyi.domain.Cache;
import com.tangyi.dto.CacheDto;
import com.tangyi.service.CacheService;
import com.tangyi.utils.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * 测试缓存
 * Created by tangyi on 2017/2/4.
 *//*

@RestController
public class TestController {

    @Autowired
    CacheService cacheService;

    @RequestMapping("/feign")
    @ResponseBody
    public String feign() {

        Cache cache = new Cache();
        cache.setId("test");
        cache.setName("test");
        String str = JsonMapper.getInstance().toJson(cache);
        String s = cacheService.set("test", str);
        String object = cacheService.get("test");
        //CacheDto cache1 = (CacheDto)JsonMapper.fromJsonString(object, CacheDto.class);
        Cache cache2 = (Cache) JsonMapper.fromJsonString(object, Cache.class);
        return object;
    }
}
*/
