package generate;

import generate.TbUser;

public interface TbUserDao {
    int deleteByPrimaryKey(String userId);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}