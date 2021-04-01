package com.zty.springboot01login.Mapper;

import com.zty.springboot01login.Pojo.UserScore;
import com.zty.springboot01login.Pojo.UserScoreKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserScoreMapper {
    int deleteByPrimaryKey(UserScoreKey key);

    int insert(UserScore record);

    int insertSelective(UserScore record);

    /*这个不带blob*/
    UserScore selectByPrimaryKey(UserScoreKey key);

    int updateByPrimaryKeySelective(UserScore record);

    /*更新blob必须用这个*/
    int updateByPrimaryKeyWithBLOBs(UserScore record);

    int updateByPrimaryKey(UserScore record);

    UserScore selectByPrimaryKeyWithBLOBs(UserScoreKey key);

    List<UserScore> selectByLabId(Integer labId);

    List<UserScore> selectByLabIdWithBLOBs(Integer labid);

    List<UserScore> selectByUserId(Integer userId);

    List<UserScore> selectByUserIdWithBLOBs(Integer userId);
}