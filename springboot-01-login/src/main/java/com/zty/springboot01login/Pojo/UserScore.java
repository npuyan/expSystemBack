package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

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
     * 分数最后一次上传的时间，如果没有时间就代表没有上传
     *
     * Table:     tb_user_score
     * Column:    score_time
     * Nullable:  true
     */
    private Date scoreTime;

    /**
     * 作业文件类型，因为可能出现不同的类型，但是保存blob时只保存二进制不保存名称，所以需要另外存储
     *
     * Table:     tb_user_score
     * Column:    homework_type
     * Nullable:  false
     */
    private String homeworkType;

    /**
     * 作业最后一次上传的时间，没有时间就代表没有上传
     *
     * Table:     tb_user_score
     * Column:    homework_time
     * Nullable:  true
     */
    private Date homeworkTime;

    /**
     * Table:     tb_user_score
     * Column:    homework
     * Nullable:  true
     */
    private byte[] homework;
}