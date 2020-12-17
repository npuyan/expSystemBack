package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseEnv;
import com.zty.springboot01login.Pojo.CourseEnvExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseEnvMapper {
    int countByExample(CourseEnvExample example);

    int deleteByExample(CourseEnvExample example);

    int deleteByPrimaryKey(String envId);

    int insert(CourseEnv record);

    int insertSelective(CourseEnv record);

    List<CourseEnv> selectByExampleWithRowbounds(CourseEnvExample example, RowBounds rowBounds);

    List<CourseEnv> selectByExample(CourseEnvExample example);

    CourseEnv selectByPrimaryKey(String envId);

    int updateByExampleSelective(@Param("record") CourseEnv record, @Param("example") CourseEnvExample example);

    int updateByExample(@Param("record") CourseEnv record, @Param("example") CourseEnvExample example);

    int updateByPrimaryKeySelective(CourseEnv record);

    int updateByPrimaryKey(CourseEnv record);
}