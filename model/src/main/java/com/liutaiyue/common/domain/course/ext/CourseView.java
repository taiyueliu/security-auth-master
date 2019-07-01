package com.liutaiyue.common.domain.course.ext;

import com.liutaiyue.common.domain.course.CourseBase;
import com.liutaiyue.common.domain.course.CourseMarket;
import com.liutaiyue.common.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
@NoArgsConstructor
@ToString
public class CourseView implements java.io.Serializable {
    private CourseBase courseBase;
    private CoursePic coursePic;
    private CourseMarket courseMarket;
    private TeachplanNode teachplanNode;
}
