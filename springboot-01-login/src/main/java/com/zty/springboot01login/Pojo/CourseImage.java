package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Table: tb_course_image
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseImage {
    /**
     * 主键id，自增
     *
     * Table:     tb_course_image
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * image id 即实际的镜像id
     *
     * Table:     tb_course_image
     * Column:    img_id
     * Nullable:  false
     */
    private Integer imgId;

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