package Controllers.Index;

import Controllers.Login.MasterLoginController;
import Controllers.Login.StudentLoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class indexPageController extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //设置根目录:
        AnchorPane root = new AnchorPane();
        //设置首页图片:
        ImageView imageView = new ImageView();
        //设置标题:
        Label label = new Label("SAM高校考勤管理系统");
        Label message1 = new Label("开发人员");
        Label message2 = new Label("      --- 23级软件工程二班高远洋");
        //设置学生账户按钮:
        Button buttonStudent = new Button();
        //设置管理员账户按钮:
        Button buttonMaster = new Button();
        //设置退出按钮:
        Button exitButton = new Button();

        Show(primaryStage, root, label, buttonStudent, buttonMaster, exitButton, message1, message2);

    }

    //接下来实现其他的动作绑定:
    public static void ActionStudentButton(Stage primaryStage) {
        //点击学生登录:
        StudentLoginController studentLoginController = new StudentLoginController();
        studentLoginController.start(new Stage());
        primaryStage.close();
    }

    public static void ActionMasterButton(Stage primaryStage) {
        //点击管理员登录:
        MasterLoginController masterLoginController = new MasterLoginController();
        masterLoginController.start(new Stage());
        primaryStage.close();
    }

    //对界面构建的代码进行抽取:
    public static void Show(Stage primaryStage, AnchorPane root, Label label, Button buttonStudent, Button buttonMaster, Button exitButton, Label message, Label message2) {
        ImageView imageView = new ImageView(new Image("image/背景照片压缩.jpg"));
        imageView.setFitHeight(348);
        imageView.setFitWidth(200);
        label = new Label("SAM高校考勤管理系统");
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(230);
        label.setLayoutY(10);
        label.setPrefHeight(60);
        label.setPrefWidth(250);
        label.setTextFill(Color.web("#983030"));
        label.setWrapText(true);
        label.setFont(new Font("Calibri", 25));

        message.setFont(new Font("Calibri", 18));
        message.setLayoutX(250);
        message.setLayoutY(80);
        message.setTextFill(Color.web("Purple"));

        message2.setLayoutX(270);
        message2.setLayoutY(110);

        message2.setTextFill(Color.web("Purple"));
        Font italicFont = Font.font("Calibri", 18);
        message2.setFont(italicFont);

        buttonStudent = new Button("学生账户");
        buttonStudent.setLayoutX(234);
        buttonStudent.setLayoutY(167);
        buttonStudent.setPrefHeight(56);
        buttonStudent.setPrefWidth(107);
        buttonStudent.setFont(new Font("Calibri", 18));
        buttonStudent.setOnAction(event -> ActionStudentButton(primaryStage));
        buttonMaster = new Button("管理账户");
        buttonMaster.setLayoutX(388);
        buttonMaster.setLayoutY(167);
        buttonMaster.setPrefHeight(56);
        buttonMaster.setPrefWidth(107);
        buttonMaster.setFont(new Font("Calibri", 18));
        buttonMaster.setOnAction(event -> ActionMasterButton(primaryStage));
        exitButton = new Button("退出");
        exitButton.setLayoutX(480);
        exitButton.setLayoutY(260);
        exitButton.setOnAction(event -> primaryStage.close());
        //将组件放进根目录:
        root.getChildren().addAll(imageView, label, message, message2, buttonStudent, buttonMaster, exitButton);
        //添加场景:
        Scene scene = new Scene(root, 549, 290);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SAM高校考勤管理系统");
        //其他设置:
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("image/图标_羽毛(1).png"));
        primaryStage.show();
    }
}

