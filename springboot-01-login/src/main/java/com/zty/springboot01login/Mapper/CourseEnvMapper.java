package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseEnv;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Mapper
@Repository
public interface CourseEnvMapper {
    int deleteByPrimaryKey(String envId);

    int insert(CourseEnv record);

    int insertSelective(CourseEnv record);

    CourseEnv selectByPrimaryKey(String envId);

    int updateByPrimaryKeySelective(CourseEnv record);

    int updateByPrimaryKey(CourseEnv record);
}