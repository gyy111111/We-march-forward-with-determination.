package Models.Utils.MasterMain;

import Models.Entities.student;
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
import javafx.scene.text.Font;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class Page_MasterQueryStudent {
    //这个类用来实现管理员查询学生信息的界面:
    //这个类是一个静态类,不需要实例化.
    public static void Page_ActionOnQueryStudent(MenuItem studentInfo, BorderPane rootSecond) {
        studentInfo.setOnAction(e -> {
            //首先清空rootSecond中的所有元素:
            rootSecond.getChildren().clear();
            //查询所有的学生信息,作为数据源;
            SqlSession sqlSession = Util_sqlSession.getSQLSession();
            ArrayList<student> students = sqlSession.getMapper(MasterStudentQuery.class).selectAllStudent();
            ObservableList<student> dataList = FXCollections.observableArrayList();
            dataList.addAll(students);
            //-----------------------------------------------------------------------------------
            //创建一个最顶层的VBox
            VBox root = new VBox();

            //创建页面内的标题
            VBox title = new VBox();
            Label titleText = new Label("学生信息条件查询");
            titleText.setFont(new Font("Calibri", 24));
            //设置颜色:
            titleText.setStyle("-fx-text-fill: #FF0000");
            title.getChildren().add(titleText);
            title.setAlignment(Pos.TOP_CENTER);
            // 设置VBox的内边距为20
            title.setPadding(new Insets(20, 0, 0, 0)); // 上边距为20，其余方向为0

            //创建文本框和列表视图


            //创建一个HBox,用来放置显示字体框和文本框:
            HBox hBoxFirst = new HBox();
            //创建一个字体框:
            Label nameLabel = new Label("姓名:");
            nameLabel.setFont(new Font("Calibri", 15));
            //创建一个文本框:
            TextField nameText = new TextField();
            nameText.setFont(new Font("Calibri", 15));
            //创建一个字体框:
            Label idLabel = new Label("学号:");
            idLabel.setFont(new Font("Calibri", 15));
            //创建一个文本框:
            TextField idText = new TextField();
            idText.setFont(new Font("Calibri", 15));
            //创建一个字体框:
            Label classLabel = new Label("班级:");
            classLabel.setFont(new Font("Calibri", 15));
            //创建一个文本框:
            TextField classText = new TextField();
            classText.setFont(new Font("Calibri", 15));
            //将字体框和文本框添加到HBox中:
            hBoxFirst.getChildren().addAll(nameLabel, nameText, idLabel, idText, classLabel, classText);
            hBoxFirst.setAlignment(Pos.TOP_CENTER);
            hBoxFirst.setSpacing(10);
            // 设置HBox的内边距为20
            hBoxFirst.setPadding(new Insets(20, 0, 0, 0)); // 上边距为20，其余方向为0

            //创建列表视图的容器:
            VBox tableViewRoot = new VBox();

            //创建视图:
            TableView<student> studentTableView = new TableView<>();
            studentTableView.setMouseTransparent(true);
            studentTableView.setEditable(false);
            TableColumn<student, String> nameColumn = new TableColumn<>("姓名");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<student, String> idColumn = new TableColumn<>("学号");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("schoolId"));
            idColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER; ");

            TableColumn<student, String> genderColumn = new TableColumn<>("性别");
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            genderColumn.setStyle("-fx-font-family: 'Calibri'; -fx-alignment: CENTER;");
            /*-fx-font-size: 21;*/
            TableColumn<student, String> ageColumn = new TableColumn<>("年龄");
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
            ageColumn.setStyle("-fx-font-family: 'Calibri'; -fx-alignment: CENTER;");

            TableColumn<student, String> classColumn = new TableColumn<>("班级");
            classColumn.setCellValueFactory(new PropertyValueFactory<>("classMessage"));
            classColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            //将列添加到表格中:
            studentTableView.getColumns().addAll(nameColumn, idColumn, genderColumn, ageColumn, classColumn);
            //设置表格的数据源:
            studentTableView.setItems(dataList);
            //将表格添加到VBox中:
            tableViewRoot.getChildren().add(studentTableView);
            //设置放在底部:
            tableViewRoot.setAlignment(Pos.BOTTOM_CENTER);
            root.getChildren().addAll(title, hBoxFirst, tableViewRoot);
            root.setSpacing(20);//设置VBox的间距为20
            //将VBox添加到rootSecond中:
            rootSecond.setTop(root);


            //-----------------------------------------------------------------------------------
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
            classText.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    // 如果文本框为空，则显示全部数据
                    studentTableView.setItems(dataList);
                } else {
                    // 根据文本框内容过滤数据并显示
                    ObservableList<student> filteredList = FXCollections.observableArrayList();
                    for (student item : dataList) {
                        if (item.getClassMessage().toString().toLowerCase().contains(newValue.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    //设置如果表中没有合适数据,就在文本框内部上显示"没有找到合适的数据"
                    if (filteredList.isEmpty()) {
                        Label info = new Label("这里似乎空空如也...");
                        info.setFont(new Font("Calibri", 21));
                        studentTableView.setPlaceholder(info);
                    }
                    studentTableView.setItems(filteredList);
                }
            });
            //对文本框监听器进行改写:
            //1.实现效果:我们可以在文本框1里上输入查询信息,这时候会在下方显示符合条件的信息;
            //2.接着,我们在文本框2中输入查询信息,会再次在刚才已经查询出来的信息的基础上在对数据进行筛选,展示新的数据
            //3.如果没有相关数据,就显示所有数据,或是上次查询完成之后的数据;
            //代码实现:

        });
    }

}
