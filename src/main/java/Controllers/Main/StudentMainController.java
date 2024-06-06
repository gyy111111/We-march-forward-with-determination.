package Controllers.Main;

import Models.Entities.course;
import Models.Entities.student;
import Models.Mappers.Main.MasterQueryCourse;
import Models.Mappers.Main.StudentExit;
import Models.Utils.All.Util_sqlSession;
import Models.Utils.StudentLoginAndExit.Util_StudentLoginAndExit;
import Models.Utils.StudentMain.StudentExit_Class;
import Models.Utils.StudentMain.StudentQuery;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentMainController extends Application {
    public static student student;
    public static BorderPane rootFirst;
    public static BorderPane rootSecond;

    @Override
    public void start(Stage stage) {
        stage.setWidth(620);
        stage.setHeight(625);
        stage.getIcons().add(new Image("image/图标_羽毛(1).png"));
        stage.setTitle("学生用户: " + student.getName() + student.getSchoolId());//设置标题
        //创建一个专门用来放置菜单栏的场景容器
        // 创建菜单栏
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle(" -fx-background-color: #F0F4FF; -fx-border-color: #D3D3D3;-fx-font-family: 'Calibri'");
        // 创建菜单:查询.
        Menu queryMenu = new Menu("学业查询");
        queryMenu.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:14");
        MenuItem queryCourse = new MenuItem("我的课程");
        MenuItem queryAttendanceMessage = new MenuItem("我的考勤");
        MenuItem classItem = new MenuItem("我的班级");
        queryMenu.getItems().addAll(queryCourse, queryAttendanceMessage, classItem);
        //--------------------------------------
        //创建菜单:帮助.
        Menu helpMenu = new Menu("帮助设置");
        helpMenu.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:14");
        MenuItem aboutItem = new MenuItem("系统关于");
        MenuItem setItem = new MenuItem("密码修改");
        helpMenu.getItems().addAll(aboutItem, setItem);
        //--------------------------------------
        //创建菜单:退出.
        Menu exitMenu = new Menu("登录选项");
        exitMenu.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:14");
        MenuItem exitMyself = new MenuItem("退出系统");
        MenuItem exitAndLoginElse = new MenuItem("切换账号");
        exitMenu.getItems().addAll(exitMyself, exitAndLoginElse);
        // 将菜单添加到菜单栏中
        menuBar.getMenus().addAll(queryMenu, helpMenu, exitMenu);
        menuBar.setPrefHeight(25);//设置高度

        ImageView imageView = new ImageView();
        Image image = new Image("image/管理员用户头像.png");
        imageView.setImage(image);
        //2.添加用户信息:
        Label text = new Label();
        text.setText("学生: " + student.getName() + " 学号: " + student.getSchoolId());
        HBox hBoxTopAll = new HBox();
        text.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:15");

        HBox hBoxTopRight = new HBox();
        hBoxTopRight.getChildren().addAll(imageView, text);
        hBoxTopRight.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:15");
        hBoxTopRight.setAlignment(Pos.BOTTOM_CENTER);
        hBoxTopRight.setSpacing(10);
        hBoxTopRight.setPadding(new Insets(0, 42, 0, 80));
        hBoxTopRight.setStyle(" -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: #F0F4FF; -fx-border-color: #D3D3D3;");


        HBox hBoxTopLeft = new HBox();
        hBoxTopLeft.getChildren().addAll(menuBar);
        hBoxTopLeft.setAlignment(Pos.CENTER);
        hBoxTopLeft.setSpacing(10);

        hBoxTopAll.getChildren().addAll(hBoxTopLeft, hBoxTopRight);
        // 创建布局，并将菜单栏放置在顶部位置
        rootFirst.setTop(hBoxTopAll);
        rootFirst.setPrefWidth(500);//设置宽度
        rootFirst.setPrefHeight(25);//设置高度
        rootFirst.setLayoutX(0);
        rootFirst.setLayoutY(0);
        //接下来:创建新的场景,用来展示不同功能下的内容:
        // 创建一个新的场景容器:

        rootSecond.setPrefWidth(500);
        rootSecond.setPrefHeight(600);
        rootSecond.setLayoutX(0);
        rootSecond.setLayoutY(25);
        VBox mainLayout = new VBox(rootFirst, rootSecond);//将两个布局放置在不同区域,按照垂直方向排列
        Scene mainScene = new Scene(mainLayout);
        stage.setScene(mainScene);


        rootSecond.getChildren().clear();//清空原有的布局;
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        ArrayList<course> courses = new ArrayList<>();
        MasterQueryCourse mapper = sqlSession.getMapper(MasterQueryCourse.class);
        if (student.getClassMessage() == 1) {
            courses = mapper.selectAllCourseClass1();
        } else if (student.getClassMessage() == 2) {
            courses = mapper.selectAllCourseClass2();
        }

        //创建一个HBox
        VBox VboxAll = new VBox();
        //创建表格,绑定数据源,展示数据;
        TableView<course> courseTableView = new TableView<>();// 创建表格列
        courseTableView.setMouseTransparent(true);
        courseTableView.setEditable(false);
        courseTableView.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size: 15");
        TableColumn<course, String> classIdColumn = new TableColumn<>("课程编号");
        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        classIdColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size: 15");
        classIdColumn.setPrefWidth(100);

        TableColumn<course, String> classNameColumn = new TableColumn<>("课程名称");
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        classNameColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size: 15");
        classNameColumn.setPrefWidth(125);

        TableColumn<course, String> classWeekColumn = new TableColumn<>("详细日期");
        classWeekColumn.setCellValueFactory(new PropertyValueFactory<>("classWeek"));
        classWeekColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size: 15");
        classWeekColumn.setPrefWidth(125);

        TableColumn<course, String> classTimeColumn = new TableColumn<>("日中排序");
        classTimeColumn.setCellValueFactory(new PropertyValueFactory<>("classTime"));
        classTimeColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size: 15");
        classTimeColumn.setPrefWidth(125);

        courseTableView.getColumns().addAll(Arrays.asList(classIdColumn, classNameColumn, classWeekColumn, classTimeColumn));
        ObservableList<course> courseList = FXCollections.observableArrayList();
        courseList.addAll(courses);
        courseTableView.setItems(courseList);
        courseTableView.setEditable(false);
        courseTableView.setFocusTraversable(false);//设置表格不可编辑:
        courseTableView.setPrefSize(495, 395);

        VBox v2 = new VBox();
        v2.setAlignment(Pos.CENTER);
        Label title = new Label("学生用户课程查询");
        title.setStyle("-fx-font-family: 'Calibri';-fx-font-size: 27px;");
        title.setTextFill(Color.web("#FF0000"));
        HBox h1 = new HBox();
        h1.setAlignment(Pos.CENTER);
        //创建一个Label:星期 TextFiled:星期
        Label label = new Label("星期:");
        label.setStyle("-fx-font-family: 'Calibri';-fx-font-size: 18px;");

        TextField textField = new TextField();
        textField.setPrefWidth(100);
        textField.setStyle("-fx-font-family: 'Calibri';-fx-font-size: 18px;");

        //使用courseList作为数据源,根据星期进行筛选:

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // 如果文本框为空，则显示全部数据
                courseTableView.setItems(courseList);
            } else {
                // 根据文本框内容过滤数据并显示
                ObservableList<course> filteredList = FXCollections.observableArrayList();
                for (course item : courseList) {
                    if (item.getClassWeek().toString().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                if (filteredList.isEmpty()) {
                    Label info = new Label("这里似乎空空如也...");
                    //设置字体样式,大小,颜色:
                    info.setStyle("-fx-font-family: 'Calibri';-fx-font-size: 20px;-fx-text-fill: #fccccc;");
                    courseTableView.setPlaceholder(info);
                }
                courseTableView.setItems(filteredList);
            }
        });
        v2.getChildren().add(title);
        h1.getChildren().addAll(label, textField);
        h1.setSpacing(10);
        VboxAll.getChildren().addAll(v2, h1, courseTableView);
        VboxAll.setSpacing(15);
        VboxAll.setAlignment(Pos.CENTER);

        rootSecond.setCenter(VboxAll);
        //扫尾代码;
        sqlSession.close();
        stage.show();


        //退出,菜单栏;无需更改1/1
        StudentExit_Class.ActionOnExitAndLoginElse(exitAndLoginElse, student, stage);//实现点击[退出-切换账号]要实现的事件;
        StudentExit_Class.ActionOnExitMyself(exitMyself, student);//实现点击[退出-退出登录]实现事件

        //查询,菜单栏;0/2
        StudentQuery.ActionOnQueryCourse(queryCourse, student, rootSecond);//实现点击[查询-我的课程]要实现的事件
        StudentQuery.ActionOnAttendanceMessage(queryAttendanceMessage, student, rootSecond);//实现点击[查询-我的考勤]要实现的绑定事件;
        StudentQuery.ActionOnClassItem(classItem, student, rootSecond);
        //帮助,菜单栏;1/2
        //
        ActionOnSetItem(setItem, student);//实现点击[帮助-设置]要实现的事件;
        ActionOnAboutItem(aboutItem);//实现点击[帮助-关于]要实现的事件;
        //其他事件绑定:1/1
        ActionCloseStage(stage);//监听非正常关闭窗口的事件,进行关闭操作;

    }
    //这里实现点击[关于]要实现的事件:
    public void ActionOnAboutItem(MenuItem aboutItem) {
        aboutItem.setOnAction(event -> {
            //创建一个新的界面:在上面显示想要展示的信息:
            // 创建AnchorPane
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(400.0, 400.0);

            // 创建ImageView
            ImageView imageView = new ImageView();
            imageView.setFitHeight(250.0);
            imageView.setFitWidth(250.0);
            imageView.setLayoutX(80.0);
            imageView.setLayoutY(60.0);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image("image/帮助照片.jpg"));

            // 创建第一个Label
            Label label1 = new Label("作者联系方式");
            label1.setAlignment(Pos.CENTER);
            label1.setLayoutX(125.0);
            label1.setLayoutY(15.0);
            label1.setPrefSize(149.0, 29.0);
            label1.setTextFill(Color.web("#842323"));
            label1.setFont(new Font("Calibri", 19.0));

            // 创建第二个Label
            Label label2 = new Label("GitHub地址:点击直达");
            label2.setAlignment(Pos.CENTER);
            label2.setLayoutX(95.0);
            label2.setLayoutY(340.0);
            label2.setPrefSize(179.0, 29.0);
            label2.setTextFill(Color.web("#842323"));
            label2.setFont(new Font("Calibri", 19.0));
            //给这个Label进行下划线,并且进行字体的改变,让其看起来像一个超链接:
            label2.setUnderline(true);
            //设置鼠标移动到上面的时候,字体颜色发生变化,让其看起来像一个超链接:,鼠标挪开的时候,颜色恢复:
            label2.setOnMouseEntered(event1 -> label2.setTextFill(Color.web("#FF0000")));
            label2.setOnMouseExited(event1 -> label2.setTextFill(Color.web("#842323")));
            //创建一个功能:在这里点击GitHub地址可以跳转到GitHub的地址:
            label2.setOnMouseClicked(event2 -> {
                //在这里实现跳转到GitHub的地址:
                getHostServices().showDocument("https://github.com/gyy111111/We-march-forward-with-determination.");//这里填写GitHub的地址
            });

            // 将ImageView和Label添加到AnchorPane;
            anchorPane.getChildren().addAll(imageView, label1, label2);
            final Scene scene = new Scene(anchorPane);
            Stage AboutStage = new Stage();
            AboutStage.setTitle("关于");
            AboutStage.getIcons().add(new Image("image/图标_羽毛(1).png"));
            AboutStage.setScene(scene);
            AboutStage.show();
        });
    }

    //这里实现监听非正常关闭窗口的事件:
    public static void ActionCloseStage(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            // 执行扫尾操作（例如保存数据或清理资源）
            //更改用户登录状态:为0
            Util_StudentLoginAndExit.ExitCodeChange(student);
            // 关闭程序
            Platform.exit();
        });
    }

    public static void ActionOnSetItem( MenuItem setItem, student student) {
        setItem.setOnAction(event -> {
            //创建一个新的Stage用来进行用户信息的展示和修改:
            Stage setStage = new Stage();
            setStage.initModality(Modality.APPLICATION_MODAL);
            setStage.setTitle("用户信息设置界面");
            setStage.setWidth(400);
            setStage.setHeight(500);
            //进行用户信息的展示:
            String gender = "";
            if (student.getGender() == 0) {
                gender = "女";
            } else if (student.getGender() == 1) {
                gender = "男";
            }
            VBox vBox = new VBox();
            //设置显示栏:
            ListView<String> studentNameListView1 = new ListView<>();
            studentNameListView1.setStyle("-fx-font-family: 'Calibri'; -fx-font-size:25;");
            //设置行高:
            studentNameListView1.setFixedCellSize(30);
            //设置studentNameListView的大小:
            studentNameListView1.setPrefSize(400, 250);
            studentNameListView1.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            studentNameListView1.getItems().add("姓名: " + student.getName());
            studentNameListView1.getItems().add("学号: " + student.getSchoolId().toString());
            studentNameListView1.getItems().add("班级: " + "软件工程 " + student.getClassMessage().toString() + "班");
            studentNameListView1.getItems().add("性别: " + gender);
            studentNameListView1.getItems().add("年龄: " + student.getAge());
            studentNameListView1.getItems().add("密码: " + student.getPasswords());

            HBox h = new HBox();
            Button Edit = new Button("修改密码");
            Edit.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-text-fill: #FF0000;");
            Edit.setPrefSize(100, 45);
            Button Sure = new Button("保存修改");
            Sure.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            Sure.setPrefSize(100, 45);
            h.setSpacing(30);
            HBox.setMargin(Edit, new Insets(0, 0, 0, 80));
            h.getChildren().addAll(Edit, Sure);

            HBox hTextField = new HBox();
            //在下面设置一个文本域,用来进行密码的修改,用户名不可修改:
            TextField textField = new TextField();
            textField.setPromptText("请输入8位字符新密码!");
            textField.setPrefSize(220, 30);
            textField.setStyle("-fx-font-family: 'Calibri';");
            //设置密码框的显示方式:
            textField.setPromptText("请输入8位字符新密码");
            textField.setStyle("-fx-font-family: 'Calibri';");
            //设置只有在点击修改密码的时候,才能看到文本框,否则不可见:
            textField.setVisible(false);

            Button exitButton = new Button("退出");
            exitButton.setLayoutX(480);
            exitButton.setLayoutY(260);
            exitButton.setOnAction(event3 -> setStage.close());
            hTextField.getChildren().addAll(textField, exitButton);
            HBox.setMargin(textField, new Insets(0, 0, 0, 40));
            hTextField.setSpacing(40);
            Edit.setOnAction(e -> textField.setVisible(true));
            Sure.setOnAction(e -> {
                //在这里进行密码的修改:
                //获取新密码:
                String newPassword = textField.getText();
                //判断新密码是否为空:
                if (newPassword.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("警告");
                    alert.setHeaderText(null);
                    alert.setContentText("新密码不能为空!");
                    alert.showAndWait();
                    return;
                } else {
                    //判断新密码是否符合规范:长度位8.
                    if (newPassword.length() != 8) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("警告");
                        alert.setHeaderText(null);
                        alert.setContentText("新密码长度必须为8位!");
                        alert.showAndWait();
                        return;
                    } else {
                        //将新密码进行更新:
                        student.setPasswords(newPassword);
                        //将新的密码进行更新到数据库中:
                        final SqlSession sqlSession = Util_sqlSession.getSQLSession();
                        final StudentExit mapper = sqlSession.getMapper(StudentExit.class);
                        mapper.UpdateStudentPasswords(student.getSchoolId().toString(), newPassword);
                        sqlSession.commit();
                        sqlSession.close();
                        //更新成功后,将文本框设置为不可见:
                        textField.setVisible(false);
                        //更新成功后,将新的密码展示到ListView中:
                        studentNameListView1.getItems().remove(5);
                        studentNameListView1.getItems().add("密码: " + newPassword);
                        //弹出一个提示框,提示用户修改成功:
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("密码修改成功");
                        alert.setHeaderText(null);
                        alert.setContentText("密码修改成功!请牢记新密码!");
                        alert.showAndWait();
                        //关闭当前的窗口:
                    }

                }

                setStage.close();
            });
            vBox.getChildren().addAll(studentNameListView1, h, hTextField);
            vBox.setSpacing(20);
            Scene scene = new Scene(vBox);
            setStage.setScene(scene);
            setStage.show();
        });

    }
}