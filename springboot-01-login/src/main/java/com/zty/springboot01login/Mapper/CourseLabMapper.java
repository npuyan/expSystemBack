package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseLab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseLabMapper {
    int deleteByPrimaryKey(String labId);

    int insert(CourseLab record);

    int insertSelective(CourseLab record);

    CourseLab selectByPrimaryKey(String labId);

    int updateByPrimaryKeySelective(CourseLab record);

    int updateByPrimaryKey(CourseLab record);
}