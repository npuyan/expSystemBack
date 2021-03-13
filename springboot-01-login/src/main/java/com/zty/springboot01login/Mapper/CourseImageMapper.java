package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseImage record);

    int insertSelective(CourseImage record);

    CourseImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseImage record);

    int updateByPrimaryKey(CourseImage record);

    List<CourseImage> selectAll();

    CourseImage selectByImageName(String imageName);

    List<CourseImage> selectByCreatorId(Integer creatorid);
}