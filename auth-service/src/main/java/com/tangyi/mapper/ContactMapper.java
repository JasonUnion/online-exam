package com.tangyi.mapper;

import com.tangyi.domain.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 消息mapper接口
 * Created by tangyi on 2017-04-16.
 */
@Mapper
public interface ContactMapper {

    @Select("SELECT * FROM CONTACT")
    List<Contact> getContactList();

    @Select("SELECT * FROM CONTACT WHERE ID=#{id}")
    Contact getContactById(Long id);

    @Select("SELECT * FROM CONTACT WHERE USERNAME=#{username}")
    Contact getContactByUsername(String username);

    @Select("SELECT * FROM CONTACT WHERE USERNAME LIKE CONCAT('%', #{username}, '%')")
    List<Contact> getContactListByUsername(String username);

    @Select("SELECT COUNT(1) FROM CONTACT WHERE STATUS=#{status}")
    int getContactCountByStatus(String status);

    @Insert("INSERT INTO CONTACT(USERNAME, EMAIL, REMARK, CREATED, STATUS) VALUES (#{username},#{email},#{remark},#{created}, #{status})")
    int insert(Contact contact);

    @Update("UPDATE CONTACT SET USERNAME=#{username}, EMAIL=#{email}, REMARK=#{remark}, CREATED=#{created}, STATUS=#{status} WHERE ID=#{id}")
    void update(Contact contact);

    @Delete("DELETE FROM CONTACT WHERE ID=#{id}")
    void delete(Contact contact);
}
