package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.UserLab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLabMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLab record);

    int insertSelective(UserLab record);

    UserLab selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLab record);

    int updateByPrimaryKey(UserLab record);

    UserLab selectByUserIdAndLabId(Integer userid,Integer labid);

    int selectLastInsertId();
}