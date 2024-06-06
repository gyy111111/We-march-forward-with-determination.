package Models.Utils.MasterLoginAndExit;

import Controllers.Index.indexPageController;
import Models.Entities.Master;
import Models.Entities.student;
import Models.Mappers.Login.masterLogin;
import Models.Mappers.Main.MasterExit;
import Models.Mappers.Main.StudentExit;
import Models.Utils.All.Util_sqlSession;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

//编写用来处理管理员用户登录:
public class Util_MasterLoginAndExit {
    //调用方法:用来查询数据库中的信息:
    public static ArrayList<Master> LoginDaoCheck(String account, String password) {
        //在这里调用数据库Mapper层:
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        masterLogin mapper = sqlSession.getMapper(masterLogin.class);
        ArrayList<Master> masters = mapper.LoginSelect(account, password);
        if (!(masters==null)) {
            //说明信息正确:
            return masters;
        }
        return new ArrayList<Master>();
    }

    //用于文本域中的数据的校验:
    public static boolean CheckText(String accountId, String password) {
        return (password.length() == 8&&accountId.length() == 1);
    }

    //实现一个方法,当用户登录之后,会将用户在数据库中的登录信息更改为1;防止多次登录:
    public static Master LoginCodeChange(Master master){
        //在这里调用数据库Mapper层:
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        masterLogin mapper = sqlSession.getMapper(masterLogin.class);
        //对数据进行更新:
        mapper.updateMaster(master.getWorkId());
        sqlSession.commit(); // 提交事务
        sqlSession.close(); // 关闭会话
        master.setLoginCode(1);
        return master;
    }

    //实现一个方法,当管理员用户退出之后,会将用户在数据库中的登录信息更改为0;防止多次登录:
    public static void ExitCodeChange(Master master){
        //在这里调用数据库Mapper层:
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        MasterExit mapper = sqlSession.getMapper(MasterExit.class);
        //对数据进行更新:
        mapper.updateMaster(master.getWorkId());
        sqlSession.commit(); // 提交事务
        sqlSession.close(); // 关闭会话
        master.setLoginCode(0);
    }
    //实现天转到起始界面的方法:
    public static void JumpToLogin() {
        //在这里实现跳转到登录界面的逻辑:
        //实际上就是重新启动一个新的JavaFX应用程序:
        //这里使用了递归调用:
        //然后重新开启程序:
        indexPageController indexPageController = new indexPageController();
        indexPageController.start(new Stage());

    }
}
