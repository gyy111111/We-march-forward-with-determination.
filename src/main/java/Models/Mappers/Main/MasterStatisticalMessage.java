package Models.Mappers.Main;

import Models.Entities.attendanceMessage;
import Models.Entities.student;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MasterStatisticalMessage {
    //范围统计:
    //1.时间选择一周
    public ArrayList<attendanceMessage> selectByChooseWeek(String class_Message, String course_Name);
    //2.时间选择一个月:
    public ArrayList<attendanceMessage> selectByChooseMonth(String class_Message, String course_Name);
    //3.选择全部:
    public ArrayList<attendanceMessage> selectByChooseAll( String class_Message,String course_Name);
    public ArrayList<attendanceMessage> selectForAllStudentWeek(String course_Name);
    public ArrayList<attendanceMessage> selectForAllStudentMonth(String course_Name);
    public ArrayList<attendanceMessage> selectForAllStudentAll(String course_Name);



    //---------------------------------------------
    //下面是Right_Second要使用的方法:
    //1.查询某个学生存在状态:
    public ArrayList<attendanceMessage> selectByStudentExist_Week(String student_Name, String course_Name);
    public ArrayList<attendanceMessage> selectByStudentExist_Month(String student_Name, String course_Name);
    public ArrayList<attendanceMessage> selectByStudentExist_All(String student_Name, String course_Name);
    //2.查询可能的学生:
    public ArrayList<student> selectByStudentPossible(String student_Name);
}
