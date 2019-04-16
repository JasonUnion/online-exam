package com.tangyi.mapper;

import com.tangyi.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by tangyi on 2017/3/20.
 */
@Mapper
public interface UserMapper {

    //@Select("SELECT * FROM USER u LEFT JOIN AUTHORITY a ON u.id= a.u_id WHERE USERNAME=#{username}")

    User findByUsername(String username);
/*
    List<User> findAll();

    User save(User user);

    User findById(Long id);

    void delete(Long id);*/

}
