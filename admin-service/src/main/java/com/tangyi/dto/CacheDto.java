package com.tangyi.dto;

/**
 * 缓存DTO层， 封装缓存内容
 * @author tangyi
 * @date 2017/3/16
 */
public class CacheDto {
    private String  status;

    private String content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
