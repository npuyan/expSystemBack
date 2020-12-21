package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.UserLab;
import com.zty.springboot01login.Pojo.UserLabKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLabMapper {
    int deleteByPrimaryKey(UserLabKey key);

    int insert(UserLab record);

    int insertSelective(UserLab record);

    UserLab selectByPrimaryKey(UserLabKey key);

    int updateByPrimaryKeySelective(UserLab record);

    int updateByPrimaryKey(UserLab record);
}