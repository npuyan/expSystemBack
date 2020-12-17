package com.zty.springboot01login.Pojo;

import lombok.*;

/**
 * Table: tb_user_lab
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLab extends UserLabKey {
    /**
     * 实验环境id
     *
     * Table:     tb_user_lab
     * Column:    env_id
     * Nullable:  true
     */
    private String envId;

    /**
     * 试验完成标志：0 未完成；1 已完成
     *
     * Table:     tb_user_lab
     * Column:    flag
     * Nullable:  false
     */
    private String flag;

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
}