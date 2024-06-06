package Controllers.Login;

import Controllers.Index.indexPageController;
import Controllers.Main.StudentMainController;
import Models.Entities.student;
import Models.Utils.All.Util_ForStage;
import Models.Utils.StudentLoginAndExit.Util_StudentLoginAndExit;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.ArrayList;

public class StudentLoginController extends Application {
    public static student student;

    //这个是构建学生登录页面的方法:
    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        //这是学号ID的Label栏:
        Label labelSchoolID = new Label("账户ID:");
        //这是账户密码的Label栏:
        Label labelPassword = new Label("账户密码:");
        //这是提示信息的Label栏:
        Label errorInfo = new Label();
        //这是输入学号的文本框:
        TextField textFieldSchoolID = new TextField("请输入11位学号!");
        //这是输入密码的密码框:
        PasswordField passwordField = new PasswordField();
        //登录按钮:用于进入学生的主界面;
        Button buttonLogin = new Button("登录");
        //返回按钮:用于关闭当前界面;
        Button buttonReturn = new Button("返回");
        Show( primaryStage, root, labelSchoolID, labelPassword, errorInfo, textFieldSchoolID, passwordField,buttonLogin,buttonReturn);
    }
    public static void Show(Stage primaryStage,AnchorPane root,Label labelSchoolID,Label labelPassword,Label errorInfo,TextField textFieldSchoolID,PasswordField passwordField,Button buttonLogin,Button buttonReturn){
        primaryStage.setTitle("学生登录页面");
        primaryStage.getIcons().add(new Image("image/图标_羽毛(1).png"));
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);

        root.setPrefSize(400, 400);
        labelSchoolID.setFont(new Font("Calibri", 22));
        AnchorPane.setTopAnchor(labelSchoolID, 94.0);//设置上边距;
        AnchorPane.setLeftAnchor(labelSchoolID, 51.0);//设置左边距;
        labelPassword.setFont(new Font("Calibri", 22));
        AnchorPane.setTopAnchor(labelPassword, 178.0);//设置上边距;
        AnchorPane.setLeftAnchor(labelPassword, 51.0);//设置左边距;
        errorInfo.setAlignment(Pos.CENTER);
        errorInfo.setLayoutX(100.0);
        errorInfo.setLayoutY(239.0);
        errorInfo.setPrefHeight(30.0);
        errorInfo.setPrefWidth(277.0);
        errorInfo.setTextFill(Color.web("#f43d3d"));
        errorInfo.setFont(new Font("Calibri", 18.0));
        errorInfo.setWrapText(true);//设置自动换行;
        errorInfo.setAlignment(Pos.CENTER);//设置居中;
        errorInfo.setVisible(false);//设置平时不可见;
        textFieldSchoolID.setPrefSize(160.0, 40.0);//
        AnchorPane.setTopAnchor(textFieldSchoolID, 94.0);
        AnchorPane.setLeftAnchor(textFieldSchoolID, 158.0);

        // 添加事件监听器，当获得焦点时清除默认文本
        textFieldSchoolID.setOnMouseClicked(e1-> {
            if (textFieldSchoolID.getText().equals("请输入11位学号!")) {
                textFieldSchoolID.setText("");
            }
        });
        // 添加事件监听器，当失去焦点时恢复默认文本
        textFieldSchoolID.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue && textFieldSchoolID.getText().isEmpty()) {
                textFieldSchoolID.setText("请输入11位学号!");
            }
        });

        passwordField.setPrefSize(160.0, 40.0);
        AnchorPane.setTopAnchor(passwordField, 175.0);//设置上边距;
        AnchorPane.setLeftAnchor(passwordField, 155.0);//设置左边距;
        buttonLogin.setFont(new Font("Calibri", 24));
        buttonLogin.setPrefSize(100.0, 50.0);
        AnchorPane.setTopAnchor(buttonLogin, 283.0);
        AnchorPane.setLeftAnchor(buttonLogin, 70.0);

        buttonReturn.setFont(new Font("Calibri", 24));
        buttonReturn.setPrefSize(100.0, 50.0);
        AnchorPane.setTopAnchor(buttonReturn, 283.0);
        AnchorPane.setLeftAnchor(buttonReturn, 239.0);


        //将所有组件放到根目录下面;
        root.getChildren().addAll(labelSchoolID, labelPassword, textFieldSchoolID, passwordField, buttonLogin, buttonReturn, errorInfo);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        //事件绑定:
        buttonLogin.setOnAction(event -> ActionLoginButton(textFieldSchoolID, passwordField, errorInfo, primaryStage));
        buttonReturn.setOnAction(event -> ActionReturnButton(primaryStage));
    }
    //实现点击登录按钮的响应方法:
    public static void ActionLoginButton(TextField textFieldSchoolID, PasswordField passwordField, Label errorInfo, Stage stage) {
        //首先判断数据形式是否正确:
        String schoolId = textFieldSchoolID.getText();
        String passwordNumber = passwordField.getText();
        if (!Util_StudentLoginAndExit.CheckText(schoolId, passwordNumber)) {
            errorInfo.setText("数据格式错误，请重新输入！");
            errorInfo.setStyle("-fx-text-fill: #f43d3d;-fx-font: 18.0 'Calibri';");
            Util_ForStage.setTime(errorInfo, 2);
        } else {
            //开始进行数据校验:
            ArrayList<student> students = Util_StudentLoginAndExit.LoginDaoCheck(schoolId, passwordNumber);

            //判断是否为null:
            if (students.isEmpty()) {
                errorInfo.setText("账户信息错误或未注册!");
                errorInfo.setStyle("-fx-text-fill: #f43d3d;-fx-font: 18.0 'Calibri';");
                Util_ForStage.setTime(errorInfo, 2);
            } else {
                //在这里就说明:学生的信息是没有问题的,现在检查学生登录状态;
                if (students.get(0).getLoginCode() != 1) {
                    //提示登录成功,并展示用户信息;
                    String name = students.get(0).getName();
                    BigInteger schoolId1 = students.get(0).getSchoolId();
                    errorInfo.setText("用户: " + name + schoolId1 + "欢迎登录!");
                    errorInfo.setStyle("-fx-text-fill: #f43d3d;-fx-font: 18.0 'Calibri';");
                    Util_ForStage.setTime(errorInfo, 4);
                    //更改登录状态:为1
                    final student student1 = Util_StudentLoginAndExit.LoginCodeChange(students.get(0));
                    //关闭当前旧的界面;
                    stage.close();


                    //展示新的页面:这里就到了学生的主页面:
                    StudentMainController studentMainController = new StudentMainController();
                    studentMainController.student = student1;//将学生信息传递给下一个页面;
                    studentMainController.rootFirst = new BorderPane();
                    studentMainController.rootSecond = new BorderPane();
                    studentMainController.start(new Stage());


                } else {
                    //弹出一个弹窗:账户登录异常!请先退出上次登录!
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("警告");
                    alert.setHeaderText(null);
                    alert.setContentText("账户登录异常!该账户已经登录!\n请先退出上次登录!");
                    ButtonType confirmButton = new ButtonType("确认");
                    alert.getButtonTypes().setAll(confirmButton);
                    alert.showAndWait();
                }

            }
        }
    }
    //实现返回按钮的任务绑定:
    public static void ActionReturnButton(Stage primaryStage) {
        //主要作用就是关闭当前界面:
        primaryStage.close();
        //然后重新开启程序:
        indexPageController indexPageController = new indexPageController();
        indexPageController.start(new Stage());
    }

}
