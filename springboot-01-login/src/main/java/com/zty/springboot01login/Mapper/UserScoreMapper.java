package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.UserScore;

public interface UserScoreMapper {
    int deleteByPrimaryKey(Integer labId);

    int insert(UserScore record);

    int insertSelective(UserScore record);

    UserScore selectByPrimaryKey(Integer labId);

    int updateByPrimaryKeySelective(UserScore record);

    /*更新blob必须用这个*/
    int updateByPrimaryKeyWithBLOBs(UserScore record);

    int updateByPrimaryKey(UserScore record);
}