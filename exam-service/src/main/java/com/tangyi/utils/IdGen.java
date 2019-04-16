package com.tangyi.utils;

import java.util.UUID;

/**
 * 生成唯一id工具类
 * Created by tangyi on 2017/3/20.
 */
public class IdGen {

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
