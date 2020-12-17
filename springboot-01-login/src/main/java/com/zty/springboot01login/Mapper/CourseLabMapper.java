package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseLab;
import com.zty.springboot01login.Pojo.CourseLabExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseLabMapper {
    int countByExample(CourseLabExample example);

    int deleteByExample(CourseLabExample example);

    int deleteByPrimaryKey(String labId);

    int insert(CourseLab record);

    int insertSelective(CourseLab record);

    List<CourseLab> selectByExampleWithRowbounds(CourseLabExample example, RowBounds rowBounds);

    List<CourseLab> selectByExample(CourseLabExample example);

    CourseLab selectByPrimaryKey(String labId);

    int updateByExampleSelective(@Param("record") CourseLab record, @Param("example") CourseLabExample example);

    int updateByExample(@Param("record") CourseLab record, @Param("example") CourseLabExample example);

    int updateByPrimaryKeySelective(CourseLab record);

    int updateByPrimaryKey(CourseLab record);
}