package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLabKey {
    /**
     * 用户id，主键
     *
     * Table:     tb_user_lab
     * Column:    user_id
     * Nullable:  false
     */
    private String userId;

    /**
     * 实验id，主键
     *
     * Table:     tb_user_lab
     * Column:    lab_id
     * Nullable:  false
     */
    private String labId;
}