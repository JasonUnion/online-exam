package com.tangyi.mapper;

import com.tangyi.domain.MapperUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户操作
 * Created by tangyi on 2017/2/7.
 */
@Mapper
public interface UserMapper {

  /*  @Select("SELECT * FROM USER WHERE USERNAME=#{username}")
    @Results({
            @Result(property = "authorities", column = "id", many = @Many(
                    select = "com.tangyi.mapper.UserAuthorityMapper.findByUsername"
            ))
    })*/
    MapperUser findByUsername(String username);

    @Select("SELECT * FROM USER WHERE USERNAME=#{username}")
    MapperUser findByUsernameExcludeAuthority(String username);

    //模糊查询
    @Select("SELECT * FROM USER WHERE USERNAME LIKE CONCAT(#{username}, '%')")
    List<MapperUser> fuzzyFindByUsername(String username);

    @Select("SELECT ID,USERNAME, EMAIL, GENDER, ENABLED, ADDRESS, MOBILE_PHONE, IDENTITY_CARD, INTRODUCTION, BIRTHDAY FROM USER")
    List<MapperUser> findAll();
    @Insert("INSERT INTO USER(ID,USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, GENDER, CLASS_NAME, EXAM_NUMBER, ENABLED, LAST_PASSWORD_RESET_DATE, AVATAR, ADDRESS, MOBILE_PHONE, IDENTITY_CARD, INTRODUCTION, BIRTHDAY) " +
            "VALUES(#{id},#{username},#{password},#{firstName},#{lastName},#{email},#{gender},#{className},#{examNumber},#{gender},#{lastPasswordResetDate},#{avatar},#{address},#{mobilePhone},#{identityCard},#{introduction},#{birthday})")
    int insert(MapperUser user);

    @Update("UPDATE USER SET USERNAME=#{username}, PASSWORD=#{password}, FIRSTNAME=#{firstName},LASTNAME=#{lastName},EMAIL=#{email},GENDER=#{gender},CLASS_NAME=#{className},EXAM_NUMBER=#{examNumber},AVATAR=#{avatar}, ADDRESS=#{address}, MOBILE_PHONE=#{mobilePhone}, IDENTITY_CARD=#{identityCard}, INTRODUCTION=#{introduction}, BIRTHDAY=#{birthday} WHERE ID=#{id}")
    void update(MapperUser user);

    @Update("UPDATE USER SET AVATAR=#{avatar} WHERE ID=#{id}")
    void updateAvatar(MapperUser user);

    @Select("SELECT * FROM USER WHERE ID=#{id}")
    MapperUser findById(@Param("id") String id);

    @Delete("DELETE FROM USER WHERE ID=#{id}")
    void delete(@Param("id") String id);

}
