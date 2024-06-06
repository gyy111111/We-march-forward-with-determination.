package Controllers.Login;

import Controllers.Index.indexPageController;
import Controllers.Main.MasterMainController;
import Models.Entities.Master;
import Models.Utils.All.Util_ForStage;
import Models.Utils.MasterLoginAndExit.Util_MasterLoginAndExit;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

//这里是管理员登录的控制类:
public class MasterLoginController extends Application {
    public static Master master;
    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        //这是ID的Label栏:
        Label labelSchoolID = new Label("工号ID:");
        //这是账户密码的Label栏:
        Label labelPassword = new Label("工号密码:");
        //这是提示信息的Label栏:
        Label errorInfo = new Label();
        //这是输入学号的文本框:
        TextField textFieldSchoolID = new TextField();
        //这是输入密码的密码框:
        PasswordField passwordField = new PasswordField();
        //登录按钮:用于进入学生的主界面;
        Button buttonLogin = new Button("登录");
        //返回按钮:用于关闭当前界面;
        Button buttonReturn = new Button("返回");
       Show(stage,root,labelSchoolID,labelPassword,errorInfo,textFieldSchoolID,passwordField,buttonLogin,buttonReturn);
    }
    //进行对显示界面的抽取:
    public static void Show(Stage stage,AnchorPane root,Label labelSchoolID, Label labelPassword,Label errorInfo,TextField textFieldSchoolID,PasswordField passwordField,Button buttonLogin,Button buttonReturn){
        root.setPrefSize(400, 400);
        labelSchoolID.setFont(new Font("Calibri", 22));
        AnchorPane.setTopAnchor(labelSchoolID, 94.0);
        AnchorPane.setLeftAnchor(labelSchoolID, 51.0);
        labelPassword.setFont(new Font("Calibri", 22));
        AnchorPane.setTopAnchor(labelPassword, 178.0);
        AnchorPane.setLeftAnchor(labelPassword, 51.0);
        errorInfo.setAlignment(Pos.CENTER);
        errorInfo.setLayoutX(100.0);
        errorInfo.setLayoutY(239.0);
        errorInfo.setPrefHeight(30.0);
        errorInfo.setPrefWidth(277.0);
        errorInfo.setFont(new Font("Calibri", 18.0));
        errorInfo.setStyle("-fx-text-fill: #f43d3d;-fx-font: 18.0 'Calibri';");
        errorInfo.setVisible(false);//设置平时不可见;
        textFieldSchoolID.setPrefSize(161.0, 38.0);//
        AnchorPane.setTopAnchor(textFieldSchoolID, 94.0);
        AnchorPane.setLeftAnchor(textFieldSchoolID, 158.0);

        // 添加事件监听器，当获得焦点时清除默认文本
        textFieldSchoolID.setOnMouseClicked(e1-> {
            if (textFieldSchoolID.getText().equals("请输入管理员账户!")) {
                textFieldSchoolID.setText("");
            }
        });
        // 添加事件监听器，当失去焦点时恢复默认文本
        textFieldSchoolID.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue && textFieldSchoolID.getText().isEmpty()) {
                textFieldSchoolID.setText("请输入管理员账户!");
            }
        });

        passwordField.setPrefSize(161.0, 38.0);
        AnchorPane.setTopAnchor(passwordField, 175.0);
        AnchorPane.setLeftAnchor(passwordField, 155.0);
        buttonLogin.setFont(new Font("Calibri", 24));
        buttonLogin.setPrefSize(101.0, 48.0);
        AnchorPane.setTopAnchor(buttonLogin, 283.0);
        AnchorPane.setLeftAnchor(buttonLogin, 70.0);
        buttonLogin.setOnAction(event -> ActionOnLoginMaster(textFieldSchoolID, passwordField, errorInfo, stage));
        buttonReturn.setFont(new Font("Calibri", 24));
        buttonReturn.setPrefSize(101.0, 48.0);
        buttonReturn.setOnAction(event -> ActionReturnButton(stage));
        AnchorPane.setTopAnchor(buttonReturn, 283.0);
        AnchorPane.setLeftAnchor(buttonReturn, 239.0);
        //将所有组件放到根目录下面;
        root.getChildren().addAll(labelSchoolID, labelPassword, textFieldSchoolID, passwordField, buttonLogin, buttonReturn, errorInfo);
        Scene scene = new Scene(root);
        //其他设置:
        stage.setScene(scene);
        stage.setTitle("管理员登录页面");
        stage.getIcons().add(new Image("image/工学院校徽(1).jpg"));
        stage.setResizable(false);
        stage.setMaximized(false);
        //显示页面:
        stage.show();

    }

    //在这里实现点击登录按钮时候的管理员的方法:
    public static void ActionOnLoginMaster(TextField fieldAccountID, PasswordField passwordField, Label errorInfo, Stage stage) {
        //首先判断数据形式是否正确:
        String accountId = fieldAccountID.getText();
        String password = passwordField.getText();

        if (!Util_MasterLoginAndExit.CheckText(accountId, password)) {
            errorInfo.setText("数据格式错误，请重新输入！");
            errorInfo.setStyle("-fx-text-fill: #f43d3d;-fx-font: 18.0 'Calibri';");
            Util_ForStage.setTime(errorInfo, 2);//动态显示;

        } else {
            //开始进行数据校验:
            ArrayList<Master> masters = Util_MasterLoginAndExit.LoginDaoCheck(accountId, password);

            //判断是否为null:
            if (masters.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("账户登录警告");
                alert.setHeaderText(null);
                alert.setContentText("未持有该管理员身份!");
                ButtonType confirmButton = new ButtonType("确认");
                alert.getButtonTypes().setAll(confirmButton);
                alert.showAndWait();

            } else {
                //在这里就说明:管理员身份是正确的但是还需要判断是否登录状态是否正常:
                if (masters.get(0).getLoginCode() != 1) {
                    //说明登录状态正常:
                    //更改登录状态:为1
                    master = Util_MasterLoginAndExit.LoginCodeChange(masters.get(0));
                    //关闭当前的窗口:
                    stage.close();

                    //展示新的页面:这里就到了管理员的主页面:
                    MasterMainController masterMainController = new MasterMainController();
                    masterMainController.master = master;
                    masterMainController.start(new Stage());
                } else {
                    //弹出一个弹窗:账户登录异常!请先退出上次登录!
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("账户登录警告");
                    alert.setHeaderText(null);
                    alert.setContentText("管理员账户登录异常!\n请确保单用户登录!");
                    ButtonType confirmButton = new ButtonType("确认");
                    alert.getButtonTypes().setAll(confirmButton);
                    alert.showAndWait();

                }
            }
        }
    }

    //在这里实现点击返回按钮时候的方法:
    public static  void ActionReturnButton(Stage PrimaryStage) {
        //这里实现返回按钮的逻辑:
        //这里就是关闭当前的窗口:
        PrimaryStage.close();
        //然后重新开启程序:
        indexPageController indexPageController = new indexPageController();
        indexPageController.start(new Stage());
    }
}
