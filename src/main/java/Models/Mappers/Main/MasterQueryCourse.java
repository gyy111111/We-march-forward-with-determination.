package Models.Mappers.Main;

import Models.Entities.course;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MasterQueryCourse {
    //1.一班课程查询:
    ArrayList<course> selectAllCourse_class1(int week);
    //2.二班课程查询:
    ArrayList<course> selectAllCourse_class2(int week);
    //3.一班课程全部查询:
    ArrayList<course> selectAllCourseClass1();

    ArrayList<course> selectAllCourseClass2();



}
