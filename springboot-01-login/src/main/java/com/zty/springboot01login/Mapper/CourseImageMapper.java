package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseImage;
import com.zty.springboot01login.Pojo.CourseImageExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseImageMapper {
    int countByExample(CourseImageExample example);

    int deleteByExample(CourseImageExample example);

    int deleteByPrimaryKey(String imageId);

    int insert(CourseImage record);

    int insertSelective(CourseImage record);

    List<CourseImage> selectByExampleWithRowbounds(CourseImageExample example, RowBounds rowBounds);

    List<CourseImage> selectByExample(CourseImageExample example);

    CourseImage selectByPrimaryKey(String imageId);

    int updateByExampleSelective(@Param("record") CourseImage record, @Param("example") CourseImageExample example);

    int updateByExample(@Param("record") CourseImage record, @Param("example") CourseImageExample example);

    int updateByPrimaryKeySelective(CourseImage record);

    int updateByPrimaryKey(CourseImage record);
}