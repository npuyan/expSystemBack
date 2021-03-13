package com.zty.springboot01login.Service;

import com.zty.springboot01login.Mapper.CourseImageMapper;
import com.zty.springboot01login.Pojo.Course;
import com.zty.springboot01login.Pojo.CourseImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CourseImageService {
    @Autowired
    CourseImageMapper courseImageMapper;

    /*得到所有镜像*/
    public List<CourseImage> getAllcourseImage() {
        List<CourseImage> allcourseimage = courseImageMapper.selectAll();
        return allcourseimage;
    }

    /*通过镜像id删除课程*/
    public boolean delCourseImageById(@RequestParam() Integer id) throws Exception {
        courseImageMapper.deleteByPrimaryKey(id);
        return true;
    }

    /*根据记录中的id更新一条数据*/
    public boolean updateCourseImage(CourseImage couseimage) throws Exception {
        courseImageMapper.updateByPrimaryKey(couseimage);
        return true;
    }

    /*通过镜像名得到镜像*/
    public CourseImage getCourseImageByName(String imageName) {

        return courseImageMapper.selectByImageName(imageName);
    }

    /*通过镜像id得到镜像*/
    public CourseImage getCourseImageById(int id) {
        return courseImageMapper.selectByPrimaryKey(id);
    }

    /*根据用户id得到本用户创建的所有镜像*/
    public List<CourseImage> getCourseImageByCreatorId(int creatorId) {
        /*得到novn镜像*/
        CourseImage novnc = courseImageMapper.selectByPrimaryKey(1);
        List<CourseImage> courseImages = courseImageMapper.selectByCreatorId(creatorId);
        Collections.sort(courseImages, new Comparator<CourseImage>() {
            @Override
            public int compare(CourseImage o1, CourseImage o2) {
                return o1.getId() - o2.getId();
            }
        });
        /*如果没有基础novnc镜像，那么假如基础novn镜像*/
        if ((courseImages.isEmpty()) ||
                (!courseImages.isEmpty() && courseImages.get(0).getId() != novnc.getId())) {
            courseImages.add(novnc);
        }
        return courseImages;
    }
}
