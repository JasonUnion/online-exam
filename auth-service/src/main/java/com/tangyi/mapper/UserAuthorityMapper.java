package com.tangyi.mapper;

import com.tangyi.domain.UserAuthority;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by tangyi on 2017/2/5.
 */
@Mapper
public interface UserAuthorityMapper {
    @Select("SELECT * FROM USER_AUTHORITY WHERE USER_ID=#{userId}")
    List<UserAuthority> findByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM USER_AUTHORITY WHERE AUTHORITY_ID=#{authorityId}")
    List<UserAuthority> findByAuthorityId(@Param("authorityId") String authorityId);

    @Select("SELECT * FROM USER_AUTHORITY WHERE USER_ID=#{userId} AND AUTHORITY_ID=#{authorityId}")
    UserAuthority findByUserIdAndAuthorityId(UserAuthority userAuthority);

    @Insert("INSERT INTO USER_AUTHORITY(USER_ID, AUTHORITY_ID) VALUES (#{userId}, #{authorityId})")
    int insert(UserAuthority userAuthority);


    void delete(Long id);

    @Delete("DELETE FROM USER_AUTHORITY WHERE USER_ID=#{userId}")
    void deleteByUserId(@Param("userId") String userId);

    List<UserAuthority> findAll();

    UserAuthority findById(String id);
}
