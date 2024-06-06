package Models.Utils.StudentMain;

import Models.Entities.attendanceMessage;
import Models.Entities.course;
import Models.Entities.student;
import Models.Mappers.Main.MasterInitStudentDailyMessage_SQL;
import Models.Mappers.Main.MasterQueryCourse;
import Models.Mappers.Main.MasterStudentQuery;
import Models.Utils.All.Util_sqlSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentQuery {
    //1.个体查询方法:
    public static void ActionOnQueryCourse(MenuItem queryCourse, student student, BorderPane rootSecond) {
        // 设置事件处理器
        queryCourse.setOnAction(event -> {
            //就是查询课表:
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

        });
    }

    //2.实现全局查询:
    public static void ActionOnAttendanceMessage(MenuItem queryMessage, student student, BorderPane rootSecond) {
        // 设置事件处理器
        queryMessage.setOnAction(event -> {
            // 在这里进行全局信息查询的业务:
            rootSecond.getChildren().clear();

            SqlSession sqlSession = Util_sqlSession.getSQLSession();
            ArrayList<attendanceMessage> attendanceMessages = sqlSession.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageById(student.getSchoolId());
            ObservableList<attendanceMessage> dataList = FXCollections.observableArrayList();
            dataList.addAll(attendanceMessages);
            //----------------------------------------------------

            //创建一个最顶层的VBox
            VBox root = new VBox();
            //----------------------------------------------------
            //创建页面内的标题:
            VBox title = new VBox();
            Label titleText = new Label("学生考勤记录查询");
            titleText.setFont(new Font("Calibri", 24));

            //设置颜色:
            titleText.setStyle("-fx-text-fill: #FF0000");
            title.getChildren().add(titleText);
            title.setAlignment(Pos.TOP_CENTER);
            // 设置VBox的内边距为20
            title.setPadding(new Insets(20, 0, 0, 0)); // 上边距为20，其余方向为0
            //创建文本栏和文本框;
            HBox text = new HBox();

            //1.创建一个字体框:
            Label dateLabel = new Label("日期:");
            dateLabel.setFont(new Font("Calibri", 15));
            //2.创建一个日期选择器:
            DatePicker datePicker = new DatePicker();
            datePicker.setPrefWidth(120);
            datePicker.setPrefHeight(30);
            //设置日期选择器里面的字体大小:
            datePicker.getEditor().setFont(new Font(15));

            //5.创建一个字体框:
            Label courseLabel = new Label("课程名:");
            courseLabel.setFont(new Font("Calibri", 15));
            //6.创建一个文本框:
            TextField courseText = new TextField();
            courseText.setFont(new Font("Calibri", 15));
            courseText.setPrefWidth(150);
            courseText.setPrefHeight(30);


            //将字体框和文本框添加到HBox中:
            text.getChildren().addAll(dateLabel, datePicker, courseLabel, courseText);
            text.setAlignment(Pos.TOP_CENTER);
            text.setSpacing(20);
            // 设置HBox的内边距为20
            text.setPadding(new Insets(20, 0, 0, 0)); // 上边距为20，其余方向为0
            //----------------------------------------------------
            //创建列表视图的容器:
            VBox tableViewRoot = new VBox();

            TableView<attendanceMessage> studentMessageTableView2 = new TableView<>();
            studentMessageTableView2.setMouseTransparent(true);
            studentMessageTableView2.setEditable(false);
            studentMessageTableView2.setItems(dataList);//设置数据源
            // 创建表格列
            TableColumn<attendanceMessage, String> schoolIdColumn = new TableColumn<>("学号");
            schoolIdColumn.setCellValueFactory(new PropertyValueFactory<>("schoolId"));
            schoolIdColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> studentNameColumn = new TableColumn<>("姓名");
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            studentNameColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> classMessageColumn = new TableColumn<>("班级");
            classMessageColumn.setCellValueFactory(new PropertyValueFactory<>("classMessage"));
            classMessageColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> classNameColumn = new TableColumn<>("课程名");
            classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
            classNameColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> classTimeColumn = new TableColumn<>("课程节次");
            classTimeColumn.setCellValueFactory(new PropertyValueFactory<>("classTime"));
            classTimeColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> classIdColumn = new TableColumn<>("课程编号");
            classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
            classIdColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> codeTypeColumn = new TableColumn<>("考勤类型");
            codeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("codeType"));
            codeTypeColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");


            TableColumn<attendanceMessage, String> classWeekColumn = new TableColumn<>("星期数");
            classWeekColumn.setCellValueFactory(new PropertyValueFactory<>("classWeek"));
            classWeekColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            TableColumn<attendanceMessage, String> dateColumn = new TableColumn<>("课程日期");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            dateColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");


            studentNameColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            schoolIdColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            classMessageColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            classTimeColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            classNameColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            classIdColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            codeTypeColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            classWeekColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");

            dateColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            // 将列添加到表格视图中
            studentMessageTableView2.getColumns().addAll(Arrays.asList(schoolIdColumn, studentNameColumn, classMessageColumn, classNameColumn, classTimeColumn, classIdColumn, codeTypeColumn, classWeekColumn, dateColumn));
            //设置表格的大小:
            studentMessageTableView2.setPrefWidth(800);
            studentMessageTableView2.setPrefHeight(450);


            tableViewRoot.getChildren().add(studentMessageTableView2);

            //----------------------------------------------------
            root.getChildren().addAll(title, text, tableViewRoot);
            root.setSpacing(10);
            //----------------------------------------------------
            rootSecond.setTop(root);
            //----------------------------------------------------


            //-----------------------------------------------------------------------------------
            // 添加文本框输入监听器,实现动态查询:
            //3.课程名文本框:
            courseText.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    // 如果文本框为空，则显示全部数据
                    studentMessageTableView2.setItems(dataList);
                } else {
                    // 根据文本框内容过滤数据并显示
                    ObservableList<attendanceMessage> filteredList = FXCollections.observableArrayList();
                    for (attendanceMessage item : dataList) {
                        if (item.getClassName().toLowerCase().contains(newValue.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    //设置如果表中没有合适数据,就在文本框内部上显示"没有找到合适的数据"
                    if (filteredList.isEmpty()) {
                        Label info = new Label("这里似乎空空如也...");
                        info.setFont(new Font("Calibri", 21));
                        studentMessageTableView2.setPlaceholder(info);
                    }
                    studentMessageTableView2.setItems(filteredList);
                }
            });
            //4.日期选择器:
            datePicker.setOnAction(e -> {
                // 当选择日期时更新文本框的值
                ObservableList<attendanceMessage> filteredList = FXCollections.observableArrayList();
                for (attendanceMessage item : dataList) {
                    if (item.getDate().toString().toLowerCase().contains(datePicker.getValue().toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                if (filteredList.isEmpty()) {
                    Label info = new Label("这里似乎空空如也...");
                    info.setFont(new Font("Calibri", 21));
                    studentMessageTableView2.setPlaceholder(info);
                }
                studentMessageTableView2.setItems(filteredList);
            });
            //-----------------------------------------------------------------------------------


        });
    }

    //3.实现我的班级的查询:
    public static void ActionOnClassItem(MenuItem classItem, student student, BorderPane rootSecond) {
        classItem.setOnAction(event -> {
            //展示我的班级里面的人员信息;
            rootSecond.getChildren().clear();
            SqlSession sqlSession = Util_sqlSession.getSQLSession();
            ArrayList<Models.Entities.student> students = new ArrayList<>();
            if (student.getClassMessage() == 1) {
                //查询一班的学生信息:
                students = sqlSession.getMapper(MasterStudentQuery.class).selectClass1Student();
            } else if (student.getClassMessage() == 2) {
                //查询二班的学生信息:
                students = sqlSession.getMapper(MasterStudentQuery.class).selectClass2Student();
            }
            sqlSession.close();
            ObservableList<student> dataList = FXCollections.observableArrayList();
            dataList.addAll(students);

            //创建一个顶层容器:
            VBox All = new VBox();
            HBox labelAndText = new HBox();
            VBox table = new VBox();

            Label name = new Label("姓名:");
            name.setStyle("-fx-font-family: 'Calibri';-fx-font-size: 15");
            TextField nameText = new TextField();
            nameText.setPrefWidth(100);
            Label id = new Label("学号:");
            id.setStyle("-fx-font-family: 'Calibri';-fx-font-size: 15");
            TextField idText = new TextField();
            idText.setPrefWidth(120);
            labelAndText.getChildren().addAll(name, nameText, id, idText);
            labelAndText.setSpacing(10);
            labelAndText.setAlignment(Pos.CENTER);


            TableView<student> studentTableView = new TableView<>();
            studentTableView.setMouseTransparent(true);
            studentTableView.setEditable(false);
            studentTableView.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size: 15");
            // 创建表格列
            TableColumn<student, String> nameColumn = new TableColumn<>("姓名");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");
            nameColumn.setPrefWidth(120);

            TableColumn<student, String> idColumn = new TableColumn<>("学号");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("schoolId"));
            idColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER; ");
            idColumn.setPrefWidth(150);

            TableColumn<student, String> genderColumn = new TableColumn<>("性别");
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            genderColumn.setStyle("-fx-font-family: 'Calibri'; -fx-alignment: CENTER;");
            genderColumn.setPrefWidth(80);

            TableColumn<student, String> ageColumn = new TableColumn<>("年龄");
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
            ageColumn.setStyle("-fx-font-family: 'Calibri'; -fx-alignment: CENTER;");
            ageColumn.setPrefWidth(100);

            TableColumn<student, String> classColumn = new TableColumn<>("班级");
            classColumn.setCellValueFactory(new PropertyValueFactory<>("classMessage"));
            classColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");
            classColumn.setPrefWidth(100);

            // 添加表格列到表格视图
            studentTableView.getColumns().addAll(Arrays.asList(nameColumn, idColumn, genderColumn, ageColumn, classColumn));
            // 设置 TableView 的数据源为 studentList
            studentTableView.setItems(dataList);
            studentTableView.setPrefSize(500, 400);
            studentTableView.setEditable(false);//设置表格无法编辑:
            studentTableView.setFocusTraversable(false);//设置表格不可编辑:
            table.getChildren().add(studentTableView);
            All.getChildren().addAll(labelAndText, table);
            All.setSpacing(10);
            All.setAlignment(Pos.CENTER);
            rootSecond.setCenter(All);
            // 添加文本框输入监听器
            nameText.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    // 如果文本框为空，则显示全部数据
                    studentTableView.setItems(dataList);
                } else {
                    // 根据文本框内容过滤数据并显示
                    ObservableList<student> filteredList = FXCollections.observableArrayList();
                    for (student item : dataList) {
                        if (item.getName().toLowerCase().contains(newValue.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Label info = new Label("这里似乎空空如也...");
                        info.setFont(new Font("Calibri", 21));
                        studentTableView.setPlaceholder(info);
                    }
                    studentTableView.setItems(filteredList);
                }
            });
            idText.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    // 如果文本框为空，则显示全部数据
                    studentTableView.setItems(dataList);
                } else {
                    // 根据文本框内容过滤数据并显示
                    ObservableList<student> filteredList = FXCollections.observableArrayList();
                    for (student item : dataList) {
                        if (item.getSchoolId().toString().toLowerCase().contains(newValue.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Label info = new Label("这里似乎空空如也...");
                        info.setFont(new Font("Calibri", 21));
                        studentTableView.setPlaceholder(info);
                    }
                    studentTableView.setItems(filteredList);
                }
            });
        });
    }

}
