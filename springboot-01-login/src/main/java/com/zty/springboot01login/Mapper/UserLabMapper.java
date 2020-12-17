package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.UserLab;
import com.zty.springboot01login.Pojo.UserLabExample;
import com.zty.springboot01login.Pojo.UserLabKey;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLabMapper {
    int countByExample(UserLabExample example);

    int deleteByExample(UserLabExample example);

    int deleteByPrimaryKey(UserLabKey key);

    int insert(UserLab record);

    int insertSelective(UserLab record);

    List<UserLab> selectByExampleWithRowbounds(UserLabExample example, RowBounds rowBounds);

    List<UserLab> selectByExample(UserLabExample example);

    UserLab selectByPrimaryKey(UserLabKey key);

    int updateByExampleSelective(@Param("record") UserLab record, @Param("example") UserLabExample example);

    int updateByExample(@Param("record") UserLab record, @Param("example") UserLabExample example);

    int updateByPrimaryKeySelective(UserLab record);

    int updateByPrimaryKey(UserLab record);
}