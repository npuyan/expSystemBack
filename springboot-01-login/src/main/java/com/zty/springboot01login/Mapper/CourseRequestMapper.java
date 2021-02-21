package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseRequestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseRequest record);

    int insertSelective(CourseRequest record);

    CourseRequest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseRequest record);

    int updateByPrimaryKey(CourseRequest record);

    List<CourseRequest> selectByCheckUserId(int userId);
}