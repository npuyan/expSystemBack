package com.zty.springboot01login.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/*课程评论实体*/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseComment {
    /**
     * 主键
     *
     * Table:     tb_course_comment
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 外键，关联课程id
     *
     * Table:     tb_course_comment
     * Column:    course_id
     * Nullable:  false
     */
    private Integer courseId;

    /**
     * 外键，关联用户id
     *
     * Table:     tb_course_comment
     * Column:    user_id
     * Nullable:  false
     */
    private Integer userId;

    /**
     * 评论发表时间
     *
     * Table:     tb_course_comment
     * Column:    time
     * Nullable:  true
     */
    private Date time;

    /**
     * 评论内容
     *
     * Table:     tb_course_comment
     * Column:    text
     * Nullable:  true
     */
    private String text;
}
