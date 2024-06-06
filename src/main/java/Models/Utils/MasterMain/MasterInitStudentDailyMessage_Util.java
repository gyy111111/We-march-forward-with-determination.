package Models.Utils.MasterMain;

import Models.Entities.attendanceMessage;
import Models.Entities.course;
import Models.Entities.student;
import Models.Mappers.Main.MasterInitStudentDailyMessage_SQL;
import Models.Mappers.Main.MasterQueryCourse;
import Models.Mappers.Main.MasterStudentQuery;
import Models.Utils.All.Util_sqlSession;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class MasterInitStudentDailyMessage_Util {
    //这里是管理员初始化学生的每日信息的方法:
    public static void initStudentDailyMessage() {
        //1.查询所有的学生信息
        //2.获取当前时间日期信息
        //3.获取两个班级的当日课程信息
        //4.获取当前在一周中的时间;
        //5.最后向数据库中注入信息;
        final SqlSession sqlSession = Util_sqlSession.getSQLSession();
        final MasterStudentQuery mapper = sqlSession.getMapper(MasterStudentQuery.class);
        final ArrayList<student> class1Student = mapper.selectClass1Student();
        final ArrayList<student> class2Student = mapper.selectClass2Student();

        //获取当前时间日期信息
        //获取当前的时间,格式为:2021-07-01
        LocalDate currentDate = LocalDate.now();
        Date date = Date.valueOf(currentDate);

        //获取当前的星期数:
        int week = currentDate.getDayOfWeek().getValue();

        //根据星期数获取当日的课程信息:
        final ArrayList<course> class1Courses = sqlSession.getMapper(MasterQueryCourse.class).selectAllCourse_class1(week);
        final ArrayList<course> class2Courses = sqlSession.getMapper(MasterQueryCourse.class).selectAllCourse_class2(week);

        //最后向数据库中注入信息:
        ArrayList<attendanceMessage> attendanceMessages = new ArrayList<>();

        for (course course : class1Courses) {//遍历一班的课程信息
            for (student student : class1Student) {//
                attendanceMessages.add(new attendanceMessage(student.getSchoolId(), student.getName(), student.getClassMessage(), course.getClassName(), course.getClassTime().toString(), course.getClassId(), 1, course.getClassWeek(), date));
                System.out.println(student.getName() + "正在初始化一班的学生信息");
            }

        }

        for (course course : class2Courses) {//遍历二班的课程信息
            for (student student : class2Student) {//
                attendanceMessages.add(new attendanceMessage(student.getSchoolId(), student.getName(), student.getClassMessage(), course.getClassName(), course.getClassTime().toString(), course.getClassId(), 1, course.getClassWeek(), date));
                System.out.println(student.getName() + "正在初始化二班的学生信息");
            }

        }

        //向数据库中注入信息:
        MasterInitStudentDailyMessage_SQL mapper1 = sqlSession.getMapper(MasterInitStudentDailyMessage_SQL.class);
        for (attendanceMessage attendanceMessage : attendanceMessages) {
            int i = mapper1.insertStudentDailyMessage(attendanceMessage.getSchoolId(), attendanceMessage.getStudentName(), attendanceMessage.getClassMessage(), attendanceMessage.getClassName(), Integer.valueOf(attendanceMessage.getClassTime()), attendanceMessage.getClassId(), attendanceMessage.getCodeType(), attendanceMessage.getClassWeek(), attendanceMessage.getDate());
            System.out.println("注入状态:" + i);
        }

        sqlSession.commit();//提交事务
        sqlSession.close();


    }
    //这里是管理员进行学生考勤信息的时候进行的例行删除:


}
