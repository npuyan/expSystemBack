package com.zty.springboot01login.Pojo;

import lombok.*;

/**
 * Table: tb_course_image
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseImage {
    /**
     * 镜像id，主键
     *
     * Table:     tb_course_image
     * Column:    image_id
     * Nullable:  false
     */
    private String imageId;

    /**
     * 镜像名称
     *
     * Table:     tb_course_image
     * Column:    image_name
     * Nullable:  false
     */
    private String imageName;

    /**
     * 镜像版本号
     *
     * Table:     tb_course_image
     * Column:    version
     * Nullable:  false
     */
    private String version;

    /**
     * 镜像上传路径
     *
     * Table:     tb_course_image
     * Column:    path
     * Nullable:  false
     */
    private String path;

    /**
     * 创建时间
     *
     * Table:     tb_course_image
     * Column:    create_time
     * Nullable:  false
     */
    private String createTime;

    /**
     * 创建人
     *
     * Table:     tb_course_image
     * Column:    creato_id
     * Nullable:  false
     */
    private String creatoId;
}