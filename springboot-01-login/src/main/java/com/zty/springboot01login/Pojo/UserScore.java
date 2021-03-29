package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Table: tb_user_score
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScore {
    /**
     * Table:     tb_user_score
     * Column:    lab_id
     * Nullable:  false
     */
    private Integer labId;

    /**
     * 用户id
     *
     * Table:     tb_user_score
     * Column:    user_id
     * Nullable:  false
     */
    private Integer userId;

    /**
     * Table:     tb_user_score
     * Column:    score
     * Nullable:  false
     */
    private Integer score;

    /**
     * Table:     tb_user_score
     * Column:    homework
     * Nullable:  true
     */
    private byte[] homework;
}