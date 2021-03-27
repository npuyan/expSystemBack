package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseLab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseLabMapper {
    int deleteByPrimaryKey(Integer labId);

    int insert(CourseLab record);

    int insertSelective(CourseLab record);

    CourseLab selectByPrimaryKey(Integer labId);

    int updateByPrimaryKeySelective(CourseLab record);

    int updateByPrimaryKey(CourseLab record);

    List<CourseLab> selectByCourseId(Integer courseId);

    List<CourseLab> selectAll();

    int selectLastInsertId();
}