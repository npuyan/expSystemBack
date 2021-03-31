package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseComment record);

    int insertSelective(CourseComment record);

    CourseComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseComment record);

    int updateByPrimaryKeyWithBLOBs(CourseComment record);

    int updateByPrimaryKey(CourseComment record);

    List<CourseComment> selectByCourseId(Integer courseId);
}