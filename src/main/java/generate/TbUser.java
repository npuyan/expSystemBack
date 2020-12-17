package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * tb_user
 * @author 
 */
@Data
public class TbUser implements Serializable {
    /**
     * 用户id，主键
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户状态：1 正常；2 禁止登陆
     */
    private Integer state;

    /**
     * 用户类型：0 管理员；1 老师；2 学生
     */
    private String userType;

    private static final long serialVersionUID = 1L;
}