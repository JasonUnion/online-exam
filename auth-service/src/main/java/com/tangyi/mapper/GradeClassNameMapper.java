package com.tangyi.mapper;

import com.tangyi.domain.GradeClassName;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by tangyi on 2017/3/25.
 */
@Mapper
public interface GradeClassNameMapper {

    @Insert("INSERT INTO GRADE_CLASSNAME(GRADE_ID,CLASSNAME_ID) VALUES(#{gradeId},#{classnameId})")
    int insert(GradeClassName gradeClassName);

    @Delete("DELETE FROM GRADE_CLASSNAME WHERE GRADE_ID=#{id}")
    void delete(String id);
}
