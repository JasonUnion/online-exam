package com.tangyi.mapper;

import com.tangyi.domain.ClassName;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by tangyi on 2017/3/25.
 */
@Mapper
public interface ClassNameMapper {

    @Select("SELECT * FROM CLASSNAME")
    List<ClassName> findAll();

    @Insert("INSERT INTO CLASSNAME(ID, CLASS_NAME) VALUES(#{id},#{className})")
    int insert(ClassName className);

    @Select("SELECT * FROM CLASSNAME WHERE CLASS_NAME=#{name}")
    ClassName findByClassName(String name);
}
