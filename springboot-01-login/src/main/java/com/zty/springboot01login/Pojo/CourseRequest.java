package com.zty.springboot01login.Pojo;

import com.zty.springboot01login.Utils.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Table: tb_course_request
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    /**
     * 主键id
     *
     * Table:     tb_course_request
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 请求类型，枚举
     *
     * Table:     tb_course_request
     * Column:    request_type
     * Nullable:  false
     */
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    /**
     * 课程id
     *
     * Table:     tb_course_request
     * Column:    course_id
     * Nullable:  false
     */
    private Integer courseId;

    /**
     * 申请人id
     *
     * Table:     tb_course_request
     * Column:    request_user_id
     * Nullable:  false
     */
    private Integer requestUserId;

    /**
     * 申请时间
     *
     * Table:     tb_course_request
     * Column:    request_time
     * Nullable:  false
     */
    private String requestTime;

    /**
     * 审核人id
     *
     * Table:     tb_course_request
     * Column:    check_user_id
     * Nullable:  false
     */
    private Integer checkUserId;

    /**
     * 审核时间
     *
     * Table:     tb_course_request
     * Column:    check_time
     * Nullable:  true
     */
    private String checkTime;

    /**
     * 申请状态：0申请，1同意，2拒绝
     *
     * Table:     tb_course_request
     * Column:    state
     * Nullable:  true
     */
    private Integer state;
}