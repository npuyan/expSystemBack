package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Table: tb_user_lab
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLab {
    /**
     * 主键id
     *
     * Table:     tb_user_lab
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 用户id
     *
     * Table:     tb_user_lab
     * Column:    user_id
     * Nullable:  false
     */
    private Integer userId;

    /**
     * 实验环境id
     *
     * Table:     tb_user_lab
     * Column:    env_id
     * Nullable:  true
     */
    private Integer envId;

    /**
     * 实验id
     *
     * Table:     tb_user_lab
     * Column:    lab_id
     * Nullable:  false
     */
    private Integer labId;

    /**
     * 实验结束时间
     *
     * Table:     tb_user_lab
     * Column:    end_time
     * Nullable:  true
     */
    private String endTime;

    /**
     * 实验开始时间
     *
     * Table:     tb_user_lab
     * Column:    start_time
     * Nullable:  true
     */
    private String startTime;

    /**
     * 试验完成标志：0 未完成；1 已完成
     *
     * Table:     tb_user_lab
     * Column:    flag
     * Nullable:  false
     */
    private String flag;
}