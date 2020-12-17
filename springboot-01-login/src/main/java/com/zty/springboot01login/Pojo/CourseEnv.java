package com.zty.springboot01login.Pojo;

import lombok.*;

/**
 * Table: tb_course_env
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnv {
    /**
     * 环境id，主键
     *
     * Table:     tb_course_env
     * Column:    env_id
     * Nullable:  false
     */
    private String envId;

    /**
     * 环境名称
     *
     * Table:     tb_course_env
     * Column:    env_name
     * Nullable:  false
     */
    private String envName;

    /**
     * 环境描述
     *
     * Table:     tb_course_env
     * Column:    remark
     * Nullable:  true
     */
    private String remark;

    /**
     * 节点名称，只能输入小写字母和数字
     *
     * Table:     tb_course_env
     * Column:    node_name
     * Nullable:  false
     */
    private String nodeName;

    /**
     * cpu大小
     *
     * Table:     tb_course_env
     * Column:    cpu
     * Nullable:  false
     */
    private Integer cpu;

    /**
     * 内存大小，单位为GB
     *
     * Table:     tb_course_env
     * Column:    memsize
     * Nullable:  false
     */
    private Integer memsize;

    /**
     * 创建时间
     *
     * Table:     tb_course_env
     * Column:    create_time
     * Nullable:  true
     */
    private String createTime;

    /**
     * 创建人
     *
     * Table:     tb_course_env
     * Column:    creator_id
     * Nullable:  true
     */
    private String creatorId;

    /**
     * 镜像id，外键
     *
     * Table:     tb_course_env
     * Column:    image_id
     * Nullable:  false
     */
    private String imageId;
}