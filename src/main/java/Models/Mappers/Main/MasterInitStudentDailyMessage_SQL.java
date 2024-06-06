package Models.Mappers.Main;

import Models.Entities.attendanceMessage;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MasterInitStudentDailyMessage_SQL {
    //根据学号查询学生信息:
    ArrayList<attendanceMessage> selectStudentDailyMessageById(BigInteger student_id);
    //查询所有的考勤信息:
    ArrayList<attendanceMessage> selectAllAttendanceMessage();
    //进行学生信息初始化的更新写入数据库操作:
    int insertStudentDailyMessage(BigInteger student_id, String student_name, Integer class_message, String class_name, Integer class_time, Integer class_id, Integer code_type, Integer class_week, Date date);

    //读取数据库中的时间日期信息,看看是否要进行今天的学生信息初始化:
    ArrayList<attendanceMessage> CheckStudentDailyMessage(Date date);

    //进行初始化之后的信息查询:
    ArrayList<attendanceMessage> selectALLStudentDailyMessage(Date date);

    //进行学生考勤信息初始化的更新写入数据库操作:
    int updateStudentAttendanceMessage(Integer code_type, BigInteger student_id, Integer class_id, Date date);

    //根据班级信息查询学生信息:
    ArrayList<attendanceMessage> selectStudentDailyMessageByClass(Integer class_id, Date date);
    //根据学号删除学生信息:
    int deleteStudentDailyMessage(List<BigInteger> student_id);


}
