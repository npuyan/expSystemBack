package com.zty.springboot01login.Pojo;

import lombok.*;

/**
 * Table: tb_user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 用户id，主键
     *
     * Table:     tb_user
     * Column:    user_id
     * Nullable:  false
     */
    private String userId;

    /**
     * 用户名
     *
     * Table:     tb_user
     * Column:    user_name
     * Nullable:  false
     */
    private String userName;

    /**
     * 登录密码
     *
     * Table:     tb_user
     * Column:    password
     * Nullable:  false
     */
    private String password;

    /**
     * 用户电话
     *
     * Table:     tb_user
     * Column:    phone
     * Nullable:  true
     */
    private String phone;

    /**
     * 用户邮箱
     *
     * Table:     tb_user
     * Column:    email
     * Nullable:  true
     */
    private String email;

    /**
     * 用户状态：1 正常；2 禁止登陆
     *
     * Table:     tb_user
     * Column:    state
     * Nullable:  false
     */
    private Integer state;

    /**
     * 用户类型：0 管理员；1 老师；2 学生
     *
     * Table:     tb_user
     * Column:    user_type
     * Nullable:  true
     */
    private String userType;
}