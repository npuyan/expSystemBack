package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScoreKey {
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
     * Column:    lab_id
     * Nullable:  false
     */
    private Integer labId;
}