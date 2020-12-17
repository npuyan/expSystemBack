package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseMapper {
    int countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(String courseId);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExampleWithRowbounds(CourseExample example, RowBounds rowBounds);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(String courseId);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}