package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * tb_user_course
 * @author 
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserCourse {
    /**
     * id，主键
     *
     * Table:     tb_user_course
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 外键，关联用户id
     *
     * Table:     tb_user_course
     * Column:    user_id
     * Nullable:  false
     */
    private Integer userId;

    /**
     * 外键，关联课程id
     *
     * Table:     tb_user_course
     * Column:    course_id
     * Nullable:  false
     */
    private Integer courseId;
}