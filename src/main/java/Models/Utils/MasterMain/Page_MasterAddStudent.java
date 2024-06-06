package Models.Utils.MasterMain;

import Models.Entities.student;
import Models.Mappers.Main.MasterAddStudent;
import Models.Mappers.Main.MasterStudentQuery;
import Models.Utils.All.Util_ForStage;
import Models.Utils.All.Util_sqlSession;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.ibatis.session.SqlSession;


//管理员新建学生信息:
public class Page_MasterAddStudent extends Application {
    @Override
    public void start(Stage PrimaryStage) {
        PrimaryStage.initModality(Modality.APPLICATION_MODAL);//设置这个窗口不关闭,其他的窗口无法点击
        //这里是新建学生信息的界面:
        PrimaryStage.setWidth(400);
        PrimaryStage.setHeight(550);
        PrimaryStage.setTitle("学生信息录入页");
        PrimaryStage.setResizable(false);//设置不可拉伸
        PrimaryStage.getIcons().add(new Image("image/工学院校徽(1).jpg"));
        PrimaryStage.setMaximized(false);//设置不可最大化

        //创建一个form表单,用来填写学生信息:
        //1.姓名: 2.学号: 3.性别: 4.年龄: 5.班级信息: 6.初始化密码: 7.初始化登录状态;
        //8.确认按钮: 9.取消按钮:
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nameLabel = new Label("姓名:");
        TextField nameField = new TextField();
        nameField.setFont(new Font("Calibri", 15));
        nameField.setFont(new Font("Calibri", 15));

        Label idLabel = new Label("学号:");
        TextField idField = new TextField();
        idField.setFont(new Font("Calibri", 15));
        idLabel.setFont(new Font("Calibri", 15));

        Label genderLabel = new Label("性别:");
        TextField genderField = new TextField();
        genderField.setFont(new Font("Calibri", 15));
        genderLabel.setFont(new Font("Calibri", 15));

        Label ageLabel = new Label("年龄:");
        TextField ageField = new TextField();
        ageLabel.setFont(new Font("Calibri", 15));
        ageField.setFont(new Font("Calibri", 15));

        Label classLabel = new Label("班级信息:");
        TextField classField = new TextField();
        classLabel.setFont(new Font("Calibri", 15));
        classField.setFont(new Font("Calibri", 15));

        Label passwordLabel = new Label("初始化密码:");
        TextField passwordField = new TextField();
        passwordLabel.setFont(new Font("Calibri", 15));
        passwordField.setFont(new Font("Calibri", 15));

        Label loginStatusLabel = new Label("初始化登录:");
        CheckBox loginStatusCheckBox = new CheckBox();

        Button confirmButton = new Button("确认");
        confirmButton.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        Button cancelButton = new Button("取消");
        cancelButton.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(idLabel, 0, 1);
        gridPane.add(idField, 1, 1);
        gridPane.add(genderLabel, 0, 2);
        gridPane.add(genderField, 1, 2);
        gridPane.add(ageLabel, 0, 3);
        gridPane.add(ageField, 1, 3);
        gridPane.add(classLabel, 0, 4);
        gridPane.add(classField, 1, 4);
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(passwordField, 1, 5);
        gridPane.add(loginStatusLabel, 0, 6);
        gridPane.add(loginStatusCheckBox, 1, 6);
        gridPane.add(confirmButton, 0, 7);
        gridPane.add(cancelButton, 1, 7);
        //在中间靠下的位置放置一个提示栏:
        Label tipLabel = new Label();//用来提示用户输入的信息是否正确;
        //设置提示栏的样式:
        tipLabel.setStyle("-fx-text-fill: red;-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        gridPane.add(tipLabel, 0, 8, 2, 1);

        Scene scene = new Scene(gridPane);
        PrimaryStage.setScene(scene);
        PrimaryStage.show();

        //事件绑定:
        ActionOnConfirm(confirmButton, PrimaryStage, nameField, idField, genderField, ageField, classField, passwordField, tipLabel);//确认按钮.数据添加
        ActionOnReturn(cancelButton, PrimaryStage);//返回按钮,关闭当前窗口
    }

    //下面实现按钮的监听事件:
    //1.返回按钮:
    public static void ActionOnReturn(Button cancelButton, Stage primaryStage) {
        cancelButton.setOnAction(event -> {
            //在这里实现返回按钮的监听事件:
            //1.关闭当前窗口:
            primaryStage.close();
        });
    }

    //2.确认按钮:
    public static void ActionOnConfirm(Button confirmButton, Stage primaryStage, TextField nameField, TextField idField, TextField genderField, TextField ageField, TextField classField, TextField passwordField, Label tipLabel) {
        confirmButton.setOnAction(event -> {
            //在这里实现确认按钮的监听事件:

            //1.将学生信息添加到数据库中:

            if (DataCheck(nameField, idField, genderField, ageField, classField, passwordField, tipLabel)) {
                //首先要判断这个学生信息是否已经存在:如果存在就弹出弹窗提示,并且不进行添加;
                //在数据库中查询一下这个学生的学号是否已经存在:
                final SqlSession sqlSession = Util_sqlSession.getSQLSession();
                final MasterStudentQuery mapper = sqlSession.getMapper(MasterStudentQuery.class);
                student student = mapper.selectStudentById(idField.getText());
                sqlSession.close();

                if (student != null) {
                    //如果学生信息已经存在,就弹出一个提示框:
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("学生信息已经存在!");
                    alert.setHeaderText(null);
                    alert.setContentText("学生: " + nameField.getText() + " 学号 " + idField.getText() + "的学生信息已经存在!");
                    alert.showAndWait();
                    //在这里设置一个时间,在时间结束后关闭弹窗:
                    // 创建一个PauseTransition对象，设置持续时间为2秒,不使用自定义的工具类:
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                    // 设置延迟结束后的动作
                    pauseTransition.setOnFinished(event2 -> {
                        // 在时间结束后关闭警告弹窗
                        alert.close();
                    });
                } else {
                    //如果学生信息不存在,就进行数据的添加:
                    //才进行数据的添加:
                    AddStudentToDatabase(nameField, idField, genderField, ageField, classField, passwordField);
                    primaryStage.close();//在最后关闭当前窗口;
                    //提示添加成功:给出一个弹窗:
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("学生信息添加成功!");
                    alert.setHeaderText(null);
                    alert.setContentText("学生: " + nameField.getText() + " 学号 " + idField.getText() + "的学生信息添加成功!");
                    alert.showAndWait();

                }

            }
        });
    }

    //2.1将学生信息添加到数据库之前的数据校验:
    public static boolean DataCheck(TextField nameField, TextField idField, TextField genderField, TextField ageField, TextField classField, TextField passwordField, Label tipLabel) {
        //在这里实现数据校验:
        if (nameField.getText().isEmpty() || idField.getText().isEmpty() || genderField.getText().isEmpty() || ageField.getText().isEmpty() || classField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            tipLabel.setText("学生信息不完整!");
            Util_ForStage.setTime(tipLabel, 1.5);
            return false;
        } else {
            //进行数据内容真实有效性的校验:
            //1.学号:必须是数字,且长度为11位;
            if (idField.getText().length() != 11 || !idField.getText().matches("[0-9]+")) {
                tipLabel.setText("数据格式错误!");
                Util_ForStage.setTime(tipLabel, 1.5);
                return false;

            } else {
                //2.年龄:必须是数字,且长度为2位;
                if (ageField.getText().length() >= 3 || !ageField.getText().matches("[0-9]+")) {
                    tipLabel.setText("数据格式错误!");
                    Util_ForStage.setTime(tipLabel, 1.5);
                    return false;
                } else {
                    //3.性别:必须是男或女;
                    if (!genderField.getText().equals("1") && !genderField.getText().equals("0")) {
                        tipLabel.setText("数据格式错误!");
                        Util_ForStage.setTime(tipLabel, 1.5);
                        return false;
                    } else {
                        //4.班级信息:必须是数字,且长度为4位;
                        if (!classField.getText().equals("1") && !classField.getText().equals("2")) {
                            tipLabel.setText("数据格式错误!");
                            Util_ForStage.setTime(tipLabel, 1.5);
                            return false;
                        } else {
                            //5.密码:必须是数字,且长度为6位;
                            if (passwordField.getText().length() != 8 || !passwordField.getText().matches("[0-9]+")) {
                                tipLabel.setText("数据格式错误!");
                                Util_ForStage.setTime(tipLabel, 1.5);
                                return false;
                            } else {
                                return true;
                            }
                        }

                    }
                }
            }
        }
    }

    //3.将学生信息添加到数据库中的方法:
    public static void AddStudentToDatabase(TextField nameField, TextField idField, TextField genderField, TextField ageField, TextField classField, TextField passwordField) {

        final SqlSession sqlSession = Util_sqlSession.getSQLSession();
        //首先从数据库表中读取信息:判断该学生有没有被添加过了.
        final student student = sqlSession.getMapper(MasterStudentQuery.class).selectStudentById(idField.getText());
        if (student != null) {
            //说明有错误:
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("学生信息添加失败!");
            alert.setHeaderText(null);
            alert.setContentText("学号 " + idField.getText() + "的学生已经存在!");
            alert.showAndWait();
        } else {
            final MasterAddStudent mapper = sqlSession.getMapper(MasterAddStudent.class);
            mapper.addStudent(idField.getText(), nameField.getText(), genderField.getText(), ageField.getText(), classField.getText(), passwordField.getText(), "0");
            sqlSession.commit(); // 提交事务
        }

        sqlSession.close(); // 关闭会话
    }
}

