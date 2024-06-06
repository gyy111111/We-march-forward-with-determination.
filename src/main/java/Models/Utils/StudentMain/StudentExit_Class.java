package Models.Utils.StudentMain;

import Models.Entities.student;
import Models.Utils.StudentLoginAndExit.Util_StudentLoginAndExit;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class StudentExit_Class {
    //这里实现点击[退出]要实现的事件:
    public static void ActionOnExitMyself(MenuItem exitMyself, student student) {
        exitMyself.setOnAction(event -> {
            // 在这里编写退出相关的逻辑
            Util_StudentLoginAndExit.ExitCodeChange(student);
            Platform.exit(); // 关闭JavaFX应用程序
        });
    }
    //这里实现点击[切换账号]要实现的事件:
    public static void ActionOnExitAndLoginElse(MenuItem exitAndLoginElse, student student, Stage primaryStage) {
        exitAndLoginElse.setOnAction(event -> {
            //给出一个提示弹窗:"切换账号须先退出当前账号,是否继续?"
            //弹出一个弹窗:账户登录异常!请先退出上次登录!
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("登录警告!");
            alert.setHeaderText(null);
            alert.setContentText("切换账号须先退出当前账号,是否继续?");
            //设置确认按钮:
            ButtonType confirmButton = new ButtonType("确认");
            //设置取消按钮:
            ButtonType cancelButton = new ButtonType("取消");
            alert.getButtonTypes().setAll(confirmButton, cancelButton);
            alert.showAndWait();//显示弹窗
            //点击确认后,执行退出当前账号的操作:
            if (alert.getResult() == confirmButton) {
                // 在这里编写切换账号相关的逻辑
                Util_StudentLoginAndExit.ExitCodeChange(student);//退出原有的账号登录,更改登录状态
                //关闭当前的窗口,不是真正的退出程序,而是跳转到登录界面
                primaryStage.close();
                //跳转到登录界面:
                Util_StudentLoginAndExit.JumpToLogin();//跳转到登录界面
            } else if (alert.getResult() == cancelButton) {
                //点击取消后,仅仅关闭弹窗,不做任何操作
                alert.close();
            }
        });
    }

}
