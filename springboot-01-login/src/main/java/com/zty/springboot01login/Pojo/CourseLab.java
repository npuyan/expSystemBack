package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Table: tb_course_lab
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLab {
    /**
     * 课程id，主键
     *
     * Table:     tb_course_lab
     * Column:    lab_id
     * Nullable:  false
     */
    private Integer labId;

    /**
     * 关联课程的id
     *
     * Table:     tb_course_lab
     * Column:    course_id
     * Nullable:  false
     */
    private Integer courseId;

    /**
     * 关联课程的名称
     *
     * Table:     tb_course_lab
     * Column:    course_name
     * Nullable:  false
     */
    private String courseName;

    /**
     * 实验名称
     *
     * Table:     tb_course_lab
     * Column:    lab_name
     * Nullable:  false
     */
    private String labName;

    /**
     * 关联环境id
     *
     * Table:     tb_course_lab
     * Column:    env_id
     * Nullable:  false
     */
    private Integer envId;

    /**
     * 实验文档路径
     *
     * Table:     tb_course_lab
     * Column:    doc_path
     * Nullable:  true
     */
    private String docPath;

    /**
     * 实验描述
     *
     * Table:     tb_course_lab
     * Column:    remarks
     * Nullable:  true
     */
    private String remarks;

    /**
     * 第几次实验
     *
     * Table:     tb_course_lab
     * Column:    section_id
     * Nullable:  false
     */
    private Integer sectionId;
}