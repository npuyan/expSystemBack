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
public class UserScore extends UserScoreKey {
    /**
     * Table:     tb_user_score
     * Column:    score
     * Nullable:  false
     */
    private Integer score;

    /**
     * 作业文件类型，因为可能出现不同的类型，但是保存blob时只保存二进制不保存名称，所以需要另外存储
     * <p>
     * Table:     tb_user_score
     * Column:    homeworktype
     * Nullable:  false
     */
    private String homeworktype;
    /**
     * Table:     tb_user_score
     * Column:    homework
     * Nullable:  true
     */
    private byte[] homework;
}