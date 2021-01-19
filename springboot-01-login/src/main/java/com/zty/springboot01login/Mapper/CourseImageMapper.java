package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseImage record);

    int insertSelective(CourseImage record);

    CourseImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseImage record);

    int updateByPrimaryKey(CourseImage record);
}