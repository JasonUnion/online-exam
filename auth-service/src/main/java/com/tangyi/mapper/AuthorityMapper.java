package com.tangyi.mapper;

import com.tangyi.domain.Authority;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 权限增删改查
 * Created by tangyi on 2017/2/15.
 */
@Mapper
public interface AuthorityMapper {

    @Select("SELECT * FROM AUTHORITY")
    List<Authority> findAll();

    @Select("SELECT * FROM AUTHORITY WHERE AUTHORITY_ID=#{id}")
    Authority findById(String id);

    @Select("SELECT * FROM AUTHORITY WHERE USER_ID=#{userId}")
    Authority findByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM AUTHORITY WHERE USER_NAME=#{authority}")
    Authority findByUsername(@Param("authority") String authority);

    @Insert("INSERT INTO AUTHORITY(AUTHORITY) VALUES (#{authority})")
    int insert(@Param("authority") String authority);

    @Update("UPDATE AUTHORITY SET AUTHORITY=#{authority} WHERE AUTHORITY_ID=#{authorityId}")
    void update(Authority authority);

    @Delete("DELETE FROM AUTHORITY WHERE AUTHORITY_ID=#{id}")
    void delete(String id);
}
