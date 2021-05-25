package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.UserCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCourse record);

    int insertSelective(UserCourse record);

    UserCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCourse record);

    int updateByPrimaryKey(UserCourse record);

    List<UserCourse> selectByUserId(int userId);

    List<UserCourse> selectByCourseaId(int courseId);

    List<UserCourse> selectAll();

    UserCourse selectByUserIdAndCourseId(int userId, int courseId);

    int selectLastInsertId();
}