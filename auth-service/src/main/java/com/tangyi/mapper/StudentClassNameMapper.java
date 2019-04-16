package com.tangyi.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by tangyi on 2017/3/26.
 */
@Mapper
public interface StudentClassNameMapper {

    @Insert("INSERT INTO STUDENT_CLASSNAME(STUDENT_ID,CLASSNAME_ID) VALUES(#{studentId},#{classnameId})")
    int insert(@Param("studentId") String studentId, @Param("classnameId") String classnameId);

    @Delete("DELETE FROM STUDENT_CLASSNAME WHERE STUDENT_ID=#{studentId}")
    int deleteByStudentId(@Param("studentId") String studentId);
}
