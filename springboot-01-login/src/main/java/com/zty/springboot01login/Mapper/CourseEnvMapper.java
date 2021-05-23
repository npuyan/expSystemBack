package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.CourseEnv;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface CourseEnvMapper {
    int deleteByPrimaryKey(Integer envId);

    int insert(CourseEnv record);

    int insertSelective(CourseEnv record);

    CourseEnv selectByPrimaryKey(Integer envId);

    int updateByPrimaryKeySelective(CourseEnv record);

    int updateByPrimaryKey(CourseEnv record);

    List<CourseEnv> selectAll();

    CourseEnv selectByCourseEnvName(String name);

    CourseEnv selectByImageId(int imageid);

    int selectLastInsertId();

}