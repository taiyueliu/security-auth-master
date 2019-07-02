package com.liutaiyue.common.domain.portalview;

import com.liutaiyue.common.domain.course.CourseBase;
import com.liutaiyue.common.domain.course.CourseMarket;
import com.liutaiyue.common.domain.course.CoursePic;
import com.liutaiyue.common.domain.course.ext.TeachplanNode;
import com.liutaiyue.common.domain.report.ReportCourse;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by admin on 2018/2/27.
 */
@Data
@ToString
public class ViewCourse implements Serializable{

    @Id
    private String id;
    private CourseBase courseBase;
    private CourseMarket courseMarket;
    private CoursePic coursePic;
    private TeachplanNode teachplan;
    //课程统计信息
    private ReportCourse reportCourse;

}
