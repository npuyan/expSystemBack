package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Table: tb_course
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    /**
     * 课程id，主键
     *
     * Table:     tb_course
     * Column:    course_id
     * Nullable:  false
     */
    private String courseId;

    /**
     * 课程名称
     *
     * Table:     tb_course
     * Column:    course_name
     * Nullable:  false
     */
    private String courseName;

    /**
     * 课程作者（教师）
     *
     * Table:     tb_course
     * Column:    author
     * Nullable:  false
     */
    private String author;

    /**
     * 课程类别
     *
     * Table:     tb_course
     * Column:    type
     * Nullable:  false
     */
    private String type;

    /**
     * 课程标签
     *
     * Table:     tb_course
     * Column:    tag
     * Nullable:  true
     */
    private String tag;

    /**
     * 课程时间，以分钟计时
     *
     * Table:     tb_course
     * Column:    time
     * Nullable:  true
     */
    private Integer time;

    /**
     * 课程图片 路径
     *
     * Table:     tb_course
     * Column:    picture
     * Nullable:  true
     */
    private String picture;

    /**
     * 创建时间
     *
     * Table:     tb_course
     * Column:    create_time
     * Nullable:  false
     */
    private String createTime;

    /**
     * 课程描述
     *
     * Table:     tb_course
     * Column:    remark
     * Nullable:  true
     */
    private String remark;
}