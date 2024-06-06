package Models.Utils.StudentLoginAndExit;
import Controllers.Index.indexPageController;
import Controllers.Login.StudentLoginController;
import Models.Entities.student;
import Models.Mappers.Login.studentLogin;
import Models.Mappers.Main.StudentExit;
import Models.Utils.All.Util_sqlSession;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import java.util.ArrayList;
public class Util_StudentLoginAndExit {
    //用于进行数据对象化,以及和数据库之间的数据通信:(在前方的数据进行判断成功的情况下;)
    public static ArrayList<student> LoginDaoCheck(String account, String password) {
        //在这里调用数据库Mapper层:
         SqlSession sqlSession = Util_sqlSession.getSQLSession();
         studentLogin mapper = sqlSession.getMapper(studentLogin.class);
         ArrayList<student> students = mapper.LoginSelect(account, password);
        if (!(students==null)) {
            //说明信息正确:
            return students;
        }
        return new ArrayList<student>();
    }
    //用于文本域中的数据的校验:
    public static boolean CheckText(String account, String password) {
        return (account.length() == 11) && password.length() == 8;
    }
    //实现一个方法,当用户登录之后,会将用户在数据库中的登录信息更改为1;防止多次登录:
    public static student LoginCodeChange(student student){
        //在这里调用数据库Mapper层:
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        studentLogin mapper = sqlSession.getMapper(studentLogin.class);
        //对数据进行更新:
        mapper.updateStudent(student.getSchoolId().toString());
        sqlSession.commit(); // 提交事务
        sqlSession.close(); // 关闭会话
        student.setLoginCode(1);
        return student;
    }
    public static void ExitCodeChange(student student){
        //在这里调用数据库Mapper层:
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        StudentExit mapper = sqlSession.getMapper(StudentExit.class);
        //对数据进行更新:
       mapper.updateStudent(student.getSchoolId().toString());
        sqlSession.commit(); // 提交事务
        sqlSession.close(); // 关闭会话
    }
    public static void JumpToLogin() {
        //在这里实现跳转到登录界面的逻辑:
        //实际上就是重新启动一个新的JavaFX应用程序:
        //这里使用了递归调用:
        //然后重新开启程序:
        indexPageController indexPageController = new indexPageController();
        indexPageController.start(new Stage());

    }
}
