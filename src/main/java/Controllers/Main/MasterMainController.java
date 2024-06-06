package Controllers.Main;

import Models.Entities.Master;
import Models.Entities.attendanceMessage;
import Models.Entities.course;
import Models.Entities.student;
import Models.Mappers.Main.MasterQueryCourse;
import Models.Mappers.Main.MasterStatisticalMessage;
import Models.Utils.All.Util_sqlSession;
import Models.Utils.MasterLoginAndExit.MasterExit;
import Models.Utils.MasterLoginAndExit.Util_MasterLoginAndExit;
import Models.Utils.MasterMain.Page_MasterDataManage;
import Models.Utils.MasterMain.Page_MasterQueryMessage;
import Models.Utils.MasterMain.Page_MasterQueryStudent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import static Models.Utils.MasterMain.Page_MasterQueryMessage.MethodForMessageStatisticalRight_Second;

//这个类用来实现管理员的主界面:
public class MasterMainController extends Application {
    //作为管理员界面的根节点:
    public static ObservableList<String> dataList = FXCollections.observableArrayList();
    public static BorderPane rootFirst;
    public static BorderPane rootSecond;
    public static Master master;
    public static void startPage(BorderPane rootSecond) {
        //创建好需要用到的场景容器:
        HBox AllHBox = new HBox();
        VBox Left = new VBox();
        VBox Right = new VBox();
        HBox finalRight_First_HBox = new HBox();
        HBox finalRight_Second_HBox = new HBox();
        HBox Right_Second_HBox1 = new HBox();
        HBox Right_Second_HBox2 = new HBox();
        //先进行数据的初始化展示:
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        ArrayList<course> courseClassAll = sqlSession.getMapper(MasterQueryCourse.class).selectAllCourseClass1();
        //实现两个HashMap集合,用来进行新的数据的不重复存储;
        LinkedHashSet<String> courseNameClassAll = new LinkedHashSet<>();
        ArrayList<String> classNumberData = new ArrayList<>();
        classNumberData.add("软件工程   一班");
        classNumberData.add("软件工程   二班");
        classNumberData.add("软件工程   全部");
        ObservableList<String> classNumberData2 = FXCollections.observableArrayList();
        classNumberData2.addAll(classNumberData);

        ArrayList<String> timeData = new ArrayList<>();
        timeData.add("最近一周");
        timeData.add("一个月内");
        timeData.add("学期内所有时段");
        ObservableList<String> timeData2 = FXCollections.observableArrayList();
        timeData2.addAll(timeData);

        for (course item : courseClassAll) {
            courseNameClassAll.add(item.getClassName());
        }
        dataList.addAll(courseNameClassAll);
        //2.对这些数据进行处理,统计各个课程的各种考勤状态的情况;

        //3.将这些数据转化为柱状统计图的形式;
        Label Label1 = new Label("范围统计:");
        Label1.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-text-fill: #FF0000;-fx-font-size:20");
        ComboBox<String> courseName = new ComboBox<>();
        courseName.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        ComboBox<String> classNumber = new ComboBox<>();
        classNumber.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        ComboBox<String> time = new ComboBox<>();
        time.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        //4.设置一个按钮:统计.
        Button finalButton = new Button("统计");
        finalButton.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size:17");
        finalButton.setPrefSize(80, 30);
        //下面是单体检索的组件:
        //设置一条分割线,颜色为浅灰色:
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #D3D3D3");
        Label Label2 = new Label("单体检索:");
        Label2.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-text-fill: #FF0000;-fx-font-size:20");
        //4.创建一个文本框:
        TextField studentText = new TextField();
        studentText.setFont(new Font("Calibri", 15));
        studentText.setPrefWidth(40);
        studentText.setPrefHeight(30);
        studentText.setPromptText("请输入学生姓名:");
        // 添加事件监听器，当获得焦点时清除默认文本
        studentText.setOnMouseClicked(e1 -> {
            if (studentText.getText().equals("请输入学生姓名:")) {
                studentText.setText("");
            }
        });
        // 添加事件监听器，当失去焦点时恢复默认文本
        studentText.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue && studentText.getText().isEmpty()) {
                studentText.setText("请输入学生姓名:");
            }
        });
        ComboBox<String> courseName2 = new ComboBox<>();
        courseName2.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        ComboBox<String> time2 = new ComboBox<>();
        time2.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        Button finalButton2 = new Button("检索");
        finalButton2.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size:17");
        finalButton2.setPrefSize(80, 30);
        courseName.setItems(dataList);
        classNumber.setItems(classNumberData2);
        time.setItems(timeData2);
        courseName2.setItems(dataList);
        time2.setItems(timeData2);
        classNumber.setValue("选择班级:");
        courseName.setValue("选择课程:");
        time.setValue("选择时间:");
        courseName2.setValue("选择课程:");
        time2.setValue("选择时间:");

        //放置组件:
        Left.getChildren().addAll(Label1, classNumber, courseName, time, finalButton, separator, Label2, studentText, courseName2, time2, finalButton2);
        Left.setSpacing(30);
        Left.setPrefWidth(150);
        Left.setPrefHeight(663);
        //设置组件的位置,并将组件放在顶端:
        Left.setAlignment(Pos.TOP_CENTER);
        //设置组件的第一个元素距离顶部的距离:
        Left.setPadding(new Insets(25, 0, 0, 0)); // 上边距为20，其余方向为0
        //设置组件的背景颜色,然后设置组件的边框为浅灰色;
        Left.setStyle(" -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: #F0F8FF; -fx-border-color: #D3D3D3;");

        //以TypeList作为数据源进行图标的展示:
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        NumberAxis yAxis = new NumberAxis();
        // 创建柱状图
        // 创建柱状图
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        barChart.setTitle(" 考勤详情 演示示例");

        // 添加数据
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("旷课");
        series1.getData().add(new XYChart.Data<>("旷课", 20));
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("正常");
        series2.getData().add(new XYChart.Data<>("正常", 60));
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("迟到");
        series3.getData().add(new XYChart.Data<>("迟到", 10));
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        series4.setName("早退");
        series4.getData().add(new XYChart.Data<>("早退", 5));
        XYChart.Series<String, Number> series5 = new XYChart.Series<>();
        series5.setName("请假");
        series5.getData().add(new XYChart.Data<>("请假", 5));
        // 将数据系列添加到图表中
        barChart.getData().addAll(series1, series2, series3, series4, series5);
        barChart.setLegendSide(Side.RIGHT); // 设置图例在右侧显示
        // 设置每个数据系列的柱状图颜色
        series1.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #F8F;")); // 红色
        series2.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #81FF00;")); // 绿色
        series3.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #FFFF00;")); // 黄色
        series4.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #0080FF;")); // 蓝色
        series5.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #808080;")); // 紫色

        //放到指定的展示区域内:
        //设置显示的指定区域大小:
        barChart.setPrefSize(375, 303.5); // 设置图表的宽度为800像素，高度为600像素
        barChart.setBarGap(-24); // 设置相邻柱子之间的间距
        // 创建饼状统计图
        PieChart pieChart = new PieChart();
        // 添加数据
        pieChart.getData().add(new PieChart.Data("旷课", 20));
        pieChart.getData().add(new PieChart.Data("正常", 60));
        pieChart.getData().add(new PieChart.Data("迟到", 10));
        pieChart.getData().add(new PieChart.Data("早退", 5));
        pieChart.getData().add(new PieChart.Data("请假", 5));
        pieChart.setTitle("占比分析示意");
        pieChart.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        //在扇形图上显示数据大小占比:
        finalRight_First_HBox.getChildren().addAll(barChart, pieChart);

        //以TypeList作为数据源进行图标的展示:
        VBox Right_Second_VBox1 = new VBox();
        Right_Second_HBox1.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 20");
        ListView<String> studentNameListView = new ListView<>();
        studentNameListView.setStyle("-fx-font-family: 'Calibri'; -fx-font-size:20;");
        //设置行高:
        studentNameListView.setFixedCellSize(30);
        //设置studentNameListView的大小:
        studentNameListView.setPrefSize(359.5, 500);
        studentNameListView.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        studentNameListView.getItems().add("学生姓名: " + "演示人员");
        studentNameListView.getItems().add("学生学号: " + "23030212016");
        studentNameListView.getItems().add("学生班级: " + "软件工程   二班");
        studentNameListView.getItems().add("课程名称: " + "Java程序设计");
        studentNameListView.getItems().add("课程总数: " + 2);
        //将数据放入ListView中:
        studentNameListView.getItems().add("旷课次数: " + 0);
        studentNameListView.getItems().add("正常次数: " + 1);
        studentNameListView.getItems().add("迟到次数: " + 0);
        studentNameListView.getItems().add("早退次数: " + 0);
        studentNameListView.getItems().add("请假次数: " + 1);
        studentNameListView.getItems().add("考勤异常次数: " + 1);
        //将Label和ListView放入VBox中:
        Right_Second_VBox1.getChildren().addAll(studentNameListView);
        Right_Second_HBox1.getChildren().add(Right_Second_VBox1);
        //绘画扇形图:
        // 创建饼状统计图

        VBox Right_Second_VBox2 = new VBox();
        PieChart pieChart2 = new PieChart();
        pieChart2.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
        pieChart2.setPrefSize(375, 359.5); // 设置图表的宽度为800像素，高度为600像素
        // 添加数据,判断数据是否为0,如果为0就不显示:
        pieChart2.getData().add(new PieChart.Data("旷课", 0));
        pieChart2.getData().add(new PieChart.Data("正常", 1));
        pieChart2.getData().add(new PieChart.Data("迟到", 0));
        pieChart2.getData().add(new PieChart.Data("早退", 0));
        pieChart2.getData().add(new PieChart.Data("请假", 1));
        pieChart2.setTitle("占比分析示意");
        //在扇形图上显示数据大小占比:
        pieChart2.getData().forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        data.getName(), " ", data.pieValueProperty(), "次"
                )
        ));
        Right_Second_HBox2.getChildren().addAll(Right_Second_VBox2, pieChart2);


        finalRight_Second_HBox.getChildren().addAll(Right_Second_HBox1, Right_Second_HBox2);


        finalRight_First_HBox.setPrefWidth(750);
        finalRight_First_HBox.setPrefHeight(303.5);
        finalRight_First_HBox.setStyle(" -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: #F0F4FF; -fx-border-color: #D3D3D3;");
        finalRight_Second_HBox.setPrefWidth(750);
        finalRight_Second_HBox.setPrefHeight(359.5);
        finalRight_Second_HBox.setStyle(" -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: #F0F4FF; -fx-border-color: #D3D3D3;");

        Right.getChildren().addAll(finalRight_First_HBox, finalRight_Second_HBox);
        AllHBox.getChildren().addAll(Left, Right);
        rootSecond.setTop(AllHBox);
        //主要是对按钮的事件监听器:
        //1.finalButton:
        finalButton.setOnAction(event1 -> {
            //应该在最开始判断Right_First中是否有数据,如果有数据,就清空数据;
            String classNumberData1 = classNumber.getSelectionModel().getSelectedItem();
            String courseNameData1 = courseName.getSelectionModel().getSelectedItem();
            String timeData1 = time.getSelectionModel().getSelectedItem();
            System.out.println(classNumberData1 + courseNameData1 + timeData1);
            //1.2进行数据的校验,判断是否有数据为空:
            if (classNumberData1.equals("选择班级:") || courseNameData1.equals("选择课程:") || timeData1.equals("选择时间:")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("操作警告!");
                alert.setHeaderText(null);
                alert.setContentText("条件查询需输入完整信息!");
                //设置确认按钮:
                ButtonType confirmButton = new ButtonType("确认");
                //设置取消按钮:
                alert.getButtonTypes().setAll(confirmButton);
                alert.showAndWait();//显示弹窗
            } else {
                finalRight_First_HBox.getChildren().clear();
                //对获取的数据进行处理,转化为合适的查询条件;
                //1.处理班级信息,默认只处理1,2,如果是全部的话,就不设置这个查询条件,像时间这个也是一样:
                //2.处理日期信息:
                ArrayList<attendanceMessage> attendanceMessages = new ArrayList<>();
                switch (classNumberData1) {
                    case "软件工程   一班":
                        classNumberData1 = "1";
                        switch (timeData1) {
                            case "最近一周":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByChooseWeek(classNumberData1, courseNameData1);
                                break;
                            case "一个月内":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByChooseMonth(classNumberData1, courseNameData1);
                                break;
                            case "学期内所有时段":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByChooseAll(classNumberData1, courseNameData1);
                                break;
                        }
                        break;
                    case "软件工程   二班":
                        classNumberData1 = "2";
                        switch (timeData1) {
                            case "最近一周":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByChooseWeek(classNumberData1, courseNameData1);
                                break;
                            case "一个月内":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByChooseMonth(classNumberData1, courseNameData1);
                                break;
                            case "学期内所有时段":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByChooseAll(classNumberData1, courseNameData1);
                                break;
                        }
                        break;
                    case "软件工程   全部":
                        switch (timeData1) {
                            case "最近一周":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectForAllStudentWeek(courseNameData1);
                                break;
                            case "一个月内":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectForAllStudentMonth(courseNameData1);
                                break;
                            case "学期内所有时段":
                                attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectForAllStudentAll(courseNameData1);
                                break;
                        }

                        break;
                }


                //3.处理获得的数据.
                //按照考勤状况进行分类处理:
                int[] codeTypeArr = new int[5];
                //对attendanceMessages进行遍历,获取其中课程的学生考情统计;
                for (attendanceMessage message : attendanceMessages) {
                    if (message.getCodeType() == 0) {
                        codeTypeArr[0]++;
                    } else if (message.getCodeType() == 1) {
                        codeTypeArr[1]++;
                    } else if (message.getCodeType() == 2) {
                        codeTypeArr[2]++;
                    } else if (message.getCodeType() == 3) {
                        codeTypeArr[3]++;
                    } else if (message.getCodeType() == 4) {
                        codeTypeArr[4]++;
                    }
                }
                double[] codeTypeList = new double[5];
                for (int i = 0; i < codeTypeArr.length; i++) {
                    codeTypeList[i] = ((codeTypeArr[i] * 1.0) / attendanceMessages.size()) * 100;
                }
                //----------------------------------------

                HBox Right_First_HBox = new HBox();
                //以TypeList作为数据源进行图标的展示:
                CategoryAxis xAxis1 = new CategoryAxis();
                xAxis1.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
                NumberAxis yAxis1 = new NumberAxis();
                yAxis1.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
                // 创建柱状图
                // 创建柱状图
                BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
                barChart1.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
                barChart1.setTitle(courseNameData1 + " 考勤详情");

                // 添加数据
                XYChart.Series<String, Number> Series0 = new XYChart.Series<>();
                Series0.setName("旷课");
                Series0.getData().add(new XYChart.Data<>("旷课", codeTypeList[0]));

                XYChart.Series<String, Number> Series1 = new XYChart.Series<>();
                Series1.setName("正常");
                Series1.getData().add(new XYChart.Data<>("正常", codeTypeList[1]));

                XYChart.Series<String, Number> Series2 = new XYChart.Series<>();
                Series2.setName("迟到");
                Series2.getData().add(new XYChart.Data<>("迟到", codeTypeList[2]));

                XYChart.Series<String, Number> Series3 = new XYChart.Series<>();
                Series3.setName("早退");
                Series3.getData().add(new XYChart.Data<>("早退", codeTypeList[3]));

                XYChart.Series<String, Number> Series4 = new XYChart.Series<>();
                Series4.setName("请假");
                Series4.getData().add(new XYChart.Data<>("请假", codeTypeList[4]));

                // 将数据系列添加到图表中
                barChart1.getData().addAll(Series0, Series1, Series2, Series3, Series4);
                barChart1.setLegendSide(Side.RIGHT); // 设置图例在右侧显示

                // 设置每个数据系列的柱状图颜色
                Series0.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #F8F;")); // 红色
                Series1.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #81FF00;")); // 绿色
                Series2.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #FFFF00;")); // 黄色
                Series3.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #0080FF;")); // 蓝色
                Series4.nodeProperty().addListener((observable, oldValue, newValue) -> newValue.setStyle("-fx-bar-fill: #808080;")); // 紫色

                //放到指定的展示区域内:
                //设置显示的指定区域大小:
                barChart1.setPrefSize(375, 303.5); // 设置图表的宽度为800像素，高度为600像素
                barChart1.setBarGap(-24); // 设置相邻柱子之间的间距

                // 创建饼状统计图
                PieChart PieChart = new PieChart();

                // 添加数据
                PieChart.getData().add(new PieChart.Data("旷课", codeTypeList[0]));
                PieChart.getData().add(new PieChart.Data("正常", codeTypeList[1]));
                PieChart.getData().add(new PieChart.Data("迟到", codeTypeList[2]));
                PieChart.getData().add(new PieChart.Data("早退", codeTypeList[3]));
                PieChart.getData().add(new PieChart.Data("请假", codeTypeList[4]));
                PieChart.setTitle("占比分析示意");
                //在扇形图上显示数据大小占比:

                Right_First_HBox.getChildren().addAll(barChart1, PieChart);


                finalRight_First_HBox.getChildren().addAll(Right_First_HBox);

            }
        });
        //2.finalButton2:
        finalButton2.setOnAction(e3 -> {
            //1.获取学生姓名,课程名,时间:
            String studentNameData = studentText.getText();
            String courseNameData2 = courseName2.getSelectionModel().getSelectedItem();
            String timeSecondData2 = time2.getSelectionModel().getSelectedItem();
            //2.进行数据的校验:
            if (studentNameData.equals("请输入学生姓名:") || courseNameData2.equals("选择课程:") || timeSecondData2.equals("选择时间:")) {
                System.out.println("信息不完整!");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("操作警告!");
                alert.setHeaderText(null);
                alert.setContentText("条件查询需输入完整信息!");
                //设置确认按钮:
                ButtonType confirmButton = new ButtonType("确认");
                alert.getButtonTypes().setAll(confirmButton);
                alert.showAndWait();//显示弹窗
            } else {
                finalRight_Second_HBox.getChildren().clear();//清空数据;
                // 首先对学生姓名进行判断,如果数据库中有这个人,就进行数据的查询和展示,
                // 如果没有就先在Right_Second上成一个场景,上面显示了可能要寻找的人,如果点击了这个人,就会接着对其进行查询,然后展示;
                //1.根据姓名进行查询:
                ArrayList<attendanceMessage> attendanceMessages = new ArrayList<>();
                switch (timeSecondData2) {
                    case "最近一周":
                        attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentExist_Week(studentNameData, courseNameData2);
                        break;
                    case "一个月内":
                        attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentExist_Month(studentNameData, courseNameData2);
                        break;
                    case "学期内所有时段":
                        attendanceMessages = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentExist_All(studentNameData, courseNameData2);
                        break;
                }
                //2.判断attendanceMessages大小,以此判断学生是否存在
                if (attendanceMessages.isEmpty()) {
                    //首先清除原先的场景:
                    Right_Second_HBox1.getChildren().clear();
                    //如果没有这个学生,就显示可能要寻找的人;
                    HBox Right_Second_HBox1_ForName = new HBox();
                    Right_Second_HBox1_ForName.setStyle("-fx-font-family: 'Calibri'; -fx-font-size: 20");
                    //创建一个VBox,用来存放可能要寻找的人;
                    VBox Right_Second_VBox = new VBox();
                    //创建一个Label,用来显示可能要寻找的人;
                    Label studentNameLabel = new Label("可能要寻找的人:");
                    studentNameLabel.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-text-fill: #FF8888;-fx-font-size:15");
                    //创建一个ListView,用来存放可能要寻找的人;
                    ListView<String> studentNameListView2 = new ListView<>();
                    //设置studentNameListView的大小:
                    studentNameListView2.setPrefSize(359.5, 400);
                    studentNameListView2.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
                    //获取可能的数据:
                    ArrayList<student> studentList;
                    studentList = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentPossible(studentNameData);
                    if (studentList.isEmpty()) {
                        studentNameListView2.getItems().add("没有找到可能的人!");
                    } else {
                        //将数据放入ListView中:
                        for (student item : studentList) {
                            studentNameListView2.getItems().add(item.getName() + "  " + item.getSchoolId());
                        }
                    }

                    //将Label和ListView放入VBox中:
                    Right_Second_VBox.getChildren().addAll(studentNameLabel, studentNameListView2);
                    Right_Second_HBox1_ForName.getChildren().add(Right_Second_VBox);
                    finalRight_Second_HBox.getChildren().add(Right_Second_HBox1_ForName);
                    studentNameListView2.setOnMouseClicked(e2 -> {
                        // 获取被点击的那一行的数据
                        String selectedItem = studentNameListView2.getSelectionModel().getSelectedItem();
                        if (selectedItem != null && !selectedItem.equals("没有找到可能的人!")) {
                            //获取了新的学生姓名,课程信息,查询时间范围;
                            final String[] s = selectedItem.split(" ");
                            String name = s[0];
                            ArrayList<attendanceMessage> attendanceMessages1 = new ArrayList<>();
                            switch (timeSecondData2) {
                                case "最近一周":
                                    attendanceMessages1 = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentExist_Week(name, courseNameData2);
                                    break;
                                case "一个月内":
                                    attendanceMessages1 = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentExist_Month(name, courseNameData2);
                                    break;
                                case "学期内所有时段":
                                    attendanceMessages1 = sqlSession.getMapper(MasterStatisticalMessage.class).selectByStudentExist_All(name, courseNameData2);
                                    break;
                            }
                            MethodForMessageStatisticalRight_Second(attendanceMessages1, finalRight_Second_HBox, name, courseNameData2);
                        } else if (selectedItem != null) {
                            studentNameListView2.setMouseTransparent(true);
                        }
                    });
                } else {
                    MethodForMessageStatisticalRight_Second(attendanceMessages, finalRight_Second_HBox, studentNameData, courseNameData2);
                }
            }
        });
    }
    @Override
    public void start(Stage stage) {
        //首先将舞台Stage的大小设置为800*625:
        stage.setWidth(900);
        stage.setHeight(725);
        // 设置舞台的标题和场景:
        stage.getIcons().add(new Image("image/工学院校徽(1).jpg"));
        stage.setTitle("管理员:  " + master.getName());//设置标题

        //创建一个专门用来放置菜单栏的场景容器
        // 创建菜单栏
        HBox hBoxTopAll = new HBox();
        HBox hBoxTopLeft = new HBox();
        HBox hBoxTopRight = new HBox();
        MenuBar menuBar = new MenuBar();

        //设置menuBar的大小:
        menuBar.setPrefWidth(650);
        menuBar.setPrefHeight(25);
        menuBar.setStyle(" -fx-background-color: #F0F4FF; -fx-border-color: #D3D3D3;-fx-font-family: 'Calibri'");

        // 创建菜单:查询.
        Menu DateManage = new Menu("数据管理");
        DateManage.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:15");
        MenuItem studentManage = new MenuItem("学生管理");
        MenuItem attendanceInfo = new MenuItem("考勤更新");
        DateManage.getItems().addAll(studentManage, attendanceInfo);
        //创建菜单:帮助.
        Menu DateQuery = new Menu("数据查询统计");
        DateQuery.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:15");
        MenuItem attendanceQuery = new MenuItem("考勤信息查询");
        MenuItem studentMessageQuery = new MenuItem("学生信息查询");
        MenuItem studentMessageStatistical = new MenuItem("考勤信息统计");
        DateQuery.getItems().addAll(attendanceQuery, studentMessageQuery, studentMessageStatistical);
        //创建菜单:退出.
        Menu exitMenu = new Menu("登录选项");
        exitMenu.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:15");
        MenuItem exitMyself = new MenuItem("退出系统");
        MenuItem exitAndLoginElse = new MenuItem("切换账号");
        exitMenu.getItems().addAll(exitMyself, exitAndLoginElse);
        // 将菜单添加到菜单栏中
        menuBar.getMenus().addAll(DateManage, DateQuery, exitMenu);

        //对右边的视图进行设计:一个图标,然后在后面加上教师信息,身份工号;
        //1.添加头像图标:
        ImageView imageView = new ImageView();
        Image image = new Image("image/管理员用户头像.png");
        imageView.setImage(image);
        //2.添加用户信息:
        Label text = new Label();
        text.setText("管理员: " + master.getName() + " 工号: " + master.getWorkId());
        //3.放进右侧:
        hBoxTopRight.getChildren().addAll(imageView, text);
        hBoxTopRight.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;-fx-font-size:15");
        hBoxTopRight.setAlignment(Pos.BOTTOM_CENTER);
        hBoxTopRight.setSpacing(8);
        hBoxTopRight.setPadding(new Insets(0, 0, 0, 30)); // 上边距为20，其余方向为0
        hBoxTopLeft.getChildren().add(menuBar);
        hBoxTopAll.getChildren().addAll(hBoxTopLeft, hBoxTopRight);
        hBoxTopAll.setStyle(" -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: #F0F4FF; -fx-border-color: #D3D3D3;");
        // 创建布局，并将菜单栏放置在顶部位置
        rootFirst = new BorderPane();
        rootFirst.setTop(hBoxTopAll);
        rootFirst.setPrefWidth(900);//设置宽度
        rootFirst.setPrefHeight(25);//设置高度
        //给root1设定位置:
        rootFirst.setLayoutX(0);
        rootFirst.setLayoutY(0);
        //接下来:创建新的场景,用来展示不同功能下的内容:
        // 创建一个新的场景容器:
        rootSecond = new BorderPane();
        rootSecond.setPrefWidth(900);
        rootSecond.setPrefHeight(700);
        rootSecond.setLayoutX(0);
        rootSecond.setLayoutY(25);
        //现在开始搭建主页面进入时候的布局:
        // 创建主布局:
        VBox mainLayout = new VBox(rootFirst, rootSecond);//将两个布局放置在不同区域,按照垂直方向排列
        // 创建主场景，并将主布局作为根节点.
        Scene mainScene = new Scene(mainLayout);
        stage.setScene(mainScene);
        //展示初始化场景:
        startPage(rootSecond);
        stage.show();

        //1.登录选项
        MasterExit.ActionOnExitMyself(exitMyself, master);//退出系统;
        MasterExit.ActionOnExitAndLoginElse(exitAndLoginElse, master, stage);//切换账号;
        //2.数据管理
        Page_MasterDataManage.Page_ActionOnStudentInfoAdd(studentManage, rootSecond);//学生信息管理;
        Page_MasterDataManage.Page_ActionOnAttendanceInfoAndAdd(attendanceInfo, rootSecond,stage);//考勤信息更新;
        //3.数据查询
        Page_MasterQueryStudent.Page_ActionOnQueryStudent(studentMessageQuery, rootSecond);//学生信息查询
        Page_MasterQueryMessage.Page_ActionOnQueryMessage(attendanceQuery, rootSecond);//考勤信息查询
        Page_MasterQueryMessage.StatisticalMessageStart_Page(studentMessageStatistical, rootSecond);//考勤信息统计
        //4.其他事件绑定:
        ActionCloseStage(stage);//非法关闭窗口的事件
    }

    //这里实现非法关闭窗口的事件:
    public void ActionCloseStage(Stage primaryStage) {
        primaryStage.setOnCloseRequest(e -> {
            // 执行扫尾操作（例如保存数据或清理资源）
            //更改用户登录状态:为0
            Util_MasterLoginAndExit.ExitCodeChange(master);
            // 关闭程序
            Platform.exit();
        });
    }
    //这里实现对界面展示代码的抽取:

}
