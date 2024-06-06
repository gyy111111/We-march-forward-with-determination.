package Models.Utils.MasterMain;

import Models.Entities.attendanceMessage;
import Models.Entities.course;
import Models.Entities.student;
import Models.Mappers.Main.MasterInitStudentDailyMessage_SQL;
import Models.Mappers.Main.MasterQueryCourse;
import Models.Mappers.Main.MasterStudentQuery;
import Models.Mappers.Main.MasterUpdateAndDeleteStudent;
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
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

//主要用来实现对于管理员菜单上的数据管理:
public class Page_MasterDataManage {
    //1.学生信息添加:
    public static void Page_ActionOnStudentInfoAdd(MenuItem studentInfo, BorderPane rootSecond) {
        studentInfo.setOnAction(event -> { //首先要做的就是给主界面下面的地方添加一个新的场景:
            //首先清空rootSecond中的所有元素:
            rootSecond.getChildren().clear();
            //创建一个最顶层的HBox
            HBox hBoxForAddAll = new HBox();

            //创建一个用来放置按键的VBox,大小为250*600:
            VBox vBoxForAddButton = new VBox();
            vBoxForAddButton.setPrefSize(200, 600);


            //创建一个用来放置表格的VBox,大小为400*600:
            VBox vBoxForTable = new VBox();
            vBoxForTable.setPrefSize(560, 700);


            //在最左边设置两个按钮:
            Button addStudent = new Button("添加学生");
            Button deleteAndUpdateStudent = new Button("删改学生");
            //设置按钮上的字体颜色为红色:
            addStudent.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            deleteAndUpdateStudent.setStyle("-fx-text-fill: red;-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            Button updateStudent = new Button("信息更新");
            updateStudent.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            //设置按钮的大小:
            addStudent.setPrefSize(100, 50);
            deleteAndUpdateStudent.setPrefSize(100, 50);
            updateStudent.setPrefSize(100, 50);
            //将这三个按钮添加到vBoxForAddButton当中,并且设置间隔为50,并且设置居中;
            vBoxForAddButton.getChildren().addAll(addStudent, deleteAndUpdateStudent, updateStudent);
            vBoxForAddButton.setSpacing(50);
            //设置第一个按钮距离最上方的距离为150:
            vBoxForAddButton.setPadding(new Insets(50, 0, 0, 0));
            //设置按钮只是在x轴上面居中,而在y轴上面不进行居中:
            vBoxForAddButton.setAlignment(Pos.TOP_CENTER);


            TableView<student> studentTableView = new TableView<>();
            studentTableView.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            // 创建表格列
            TableColumn<student, String> nameColumn = new TableColumn<>("姓名");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


            TableColumn<student, String> idColumn = new TableColumn<>("学号");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("schoolId"));


            TableColumn<student, String> genderColumn = new TableColumn<>("性别");
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));


            TableColumn<student, String> ageColumn = new TableColumn<>("年龄");
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));


            TableColumn<student, String> classColumn = new TableColumn<>("班级");
            classColumn.setCellValueFactory(new PropertyValueFactory<>("classMessage"));


            // 添加表格列到表格视图
            studentTableView.getColumns().addAll(Arrays.asList(nameColumn, idColumn, genderColumn, ageColumn, classColumn));

            // 创建一个 ObservableList 来保存学生数据
            ObservableList<student> studentList = FXCollections.observableArrayList();
            // 添加一些学生数据到列表中:
            SqlSession sqlSession = Util_sqlSession.getSQLSession();
            MasterStudentQuery mapper = sqlSession.getMapper(MasterStudentQuery.class);
            studentList.addAll(mapper.selectAllStudent());//这里是从数据库中获取数据:
            studentTableView.setFixedCellSize(30); // 设置行高为30像素

            idColumn.setPrefWidth(190); // 设置学号列的宽度为100像素

            nameColumn.setPrefWidth(100); // 设置姓名列的宽度为100像素

            genderColumn.setPrefWidth(90); // 设置性别列的宽度为100像素

            classColumn.setPrefWidth(90); // 设置班级列的宽度为100像素

            ageColumn.setPrefWidth(90); // 设置年龄列的宽度为100像素


            // 设置 TableView 的数据源为 studentList
            studentTableView.setItems(studentList);
            studentTableView.setPrefSize(560, 700); // 设置表格的宽度为400像素，高度为300像素
            studentTableView.setEditable(false);//设置表格无法编辑:
            studentTableView.setFocusTraversable(false);//设置表格不可编辑:
            //将这个表格添加到vBoxForTable当中,并且设置居中:
            vBoxForTable.getChildren().addAll(studentTableView);
            //vBoxForTable.


            //将这两个VBox添加到hBoxForAddAll当中:
            hBoxForAddAll.getChildren().addAll(vBoxForAddButton, vBoxForTable);


            //进行按钮的监听事件:
            ActionOnAddStudentButton(addStudent);//添加学生信息的监听事件:
            ActionOnUpdatePageButton(updateStudent, studentList, studentTableView);//更新学生信息的监听事件:
            ActionOnStudentDeleteAndUpdateButton(studentTableView, deleteAndUpdateStudent);//删除学生信息的监听事件:
            rootSecond.setCenter(hBoxForAddAll);//将这个HBox添加到rootSecond中:
        });
    }

    //1.学生信息添加:带有防多开校验;
    public static void ActionOnAddStudentButton(Button addStudent) {
        addStudent.setOnAction(event -> {
            //在这里实现添加学生信息的监听事件:
            //1.首先要判断是否有其他的添加学生信息的窗口:
            Page_MasterAddStudent ActionOnAddStudent = new Page_MasterAddStudent();
            ActionOnAddStudent.start(new Stage());
            //设置只要有这个页面,就不允许进行除了这个页面以外的其他操作:

        });
    }

    //2.学生信息删除:
    public static void ActionOnStudentDeleteAndUpdateButton(TableView<student> studentTableView, Button deleteStudent) {
        deleteStudent.setOnAction(event -> {
            //1.首先要获取到选中的学生信息:
            //2.然后将这个学生信息从数据库中删除:
            //3.最后要确认和学生对象绑定约束的表里面的信息也要删除:
            //4.最后要更新学生信息:
            //5.给出一个弹窗,提示删除成功;
            //1.1首先开通界面的选中权限:
            //给出一个弹窗:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("用户选中开启");
            alert.setHeaderText(null);//
            alert.setContentText("防误触已关闭!\n单击选中!批量删除请按住Ctrl键!\n选择完成后右键操作");
            alert.showAndWait();

            //设置表格现在为可以支持选中:
            studentTableView.setFocusTraversable(true);//设置表格可编辑:
            //1.2设置多选:
            studentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//设置多选;
            //设置选中的颜色:
            studentTableView.setStyle("-fx-selection-bar: #4d90fe; -fx-selection-bar-text: white;");
            // 创建ContextMenu
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            MenuItem deleteMenuItem = new MenuItem("删除");
            MenuItem editMenuItem = new MenuItem("重置");
            // 向ContextMenu添加菜单项
            contextMenu.getItems().addAll(deleteMenuItem, editMenuItem);
            studentTableView.setContextMenu(contextMenu);//将ContextMenu与TableView关联

            //给delete绑定一个事件:
            deleteMenuItem.setOnAction(event2 -> {
                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert2.setTitle("操作警告!");
                        alert2.setHeaderText(null);
                        alert2.setContentText("该操作将对选中的用户所有信息进行删除!");

                        // 显示对话框并等待用户操作结果
                        Optional<ButtonType> result = alert2.showAndWait();
                        // 如果用户点击了确定按钮
                        //设置表格不可编辑:
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // 执行删除操作
                            //创建一个不允许重复集合:用来存储选中的学生信息,且不允许重复,不使用ArrayList:
                            final List<BigInteger> schoolIds = getBigIntegers(studentTableView);
                            System.out.println(schoolIds);
                            //进行数据库中学生信息的删除:
                            SqlSession sqlSession = Util_sqlSession.getSQLSession();
                            MasterUpdateAndDeleteStudent mapper = sqlSession.getMapper(MasterUpdateAndDeleteStudent.class);
                            //同时删除学生信息和考勤信息:
                            sqlSession.getMapper(MasterInitStudentDailyMessage_SQL.class).deleteStudentDailyMessage(schoolIds);
                            mapper.deleteStudent(schoolIds);//这里是从数据库中获取数据:
                            sqlSession.commit();
                            sqlSession.close(); // 关闭会话

                            //删除后更新学生信息,然后展示在窗口上面:
                            ObservableList<student> studentList2 = FXCollections.observableArrayList();
                            SqlSession sqlSession2 = Util_sqlSession.getSQLSession();
                            MasterStudentQuery mapper2 = sqlSession2.getMapper(MasterStudentQuery.class);
                            studentList2.addAll(mapper2.selectAllStudent());//这里是从数据库中获取数据:
                            studentTableView.setItems(studentList2);
                            //对表格的设置进行恢复:
                            studentTableView.setFocusTraversable(false);//设置表格不可编辑:
                            studentTableView.setPrefSize(560, 700); // 设置表格的宽度为400像素，高度为300像素
                            sqlSession2.close(); // 关闭会话
                            //弹出一个提示框:用户删除成功:
                            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                            alert3.setTitle("数据删除提示");
                            alert3.setHeaderText(null);
                            alert3.setContentText("数据删除成功!");
                            alert3.showAndWait();

                            //对表格的设置进行恢复:
                            //取消对ContextMenu的绑定:
                        } else {
                            // 用户点击了取消按钮或关闭了对话框，不执行任何操作
                            System.out.println("用户取消了删除操作或关闭了对话框。");
                            //将表格中被选中的数据的颜色修改为正常:
                            //对表格的设置进行恢复:
                            //取消对ContextMenu的绑定:
                        }
                        studentTableView.setFocusTraversable(false);//设置表格不可编辑:
                        studentTableView.setContextMenu(null);
                    }
            );

            //给edit绑定事件:
            editMenuItem.setOnAction(event1 -> {
                //编辑选定的项目:
                ObservableList<student> selectedItems = studentTableView.getSelectionModel().getSelectedItems();//获取选中的学生信息:
                List<BigInteger> schoolIds = new ArrayList<>();
                for (student selectedItem : selectedItems) {
                    schoolIds.add(selectedItem.getSchoolId());
                }
                //给出一个弹窗:账号密码初始化:
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("账号信息修改!");
                alert2.setHeaderText(null);
                alert2.setContentText("该操作将对所选账号的密码进行初始化!");

                // 显示对话框并等待用户操作结果
                Optional<ButtonType> result = alert2.showAndWait();
                //设置表格不可编辑:
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    //进行学生账户的信息修改:
                    SqlSession sqlSession = Util_sqlSession.getSQLSession();
                    MasterUpdateAndDeleteStudent mapper = sqlSession.getMapper(MasterUpdateAndDeleteStudent.class);
                    mapper.updateStudent(schoolIds);//这里是从数据库中获取数据:
                    sqlSession.commit();
                    sqlSession.close(); // 关闭会话

                    //弹出一个提示框:用户密码初始化成功:
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("数据更新提示");
                    alert3.setHeaderText(null);
                    alert3.setContentText("数据更新成功!");
                    alert3.showAndWait();

                    //删除后更新学生信息,然后展示在窗口上面:
                    ObservableList<student> studentList2 = FXCollections.observableArrayList();
                    SqlSession sqlSession2 = Util_sqlSession.getSQLSession();
                    MasterStudentQuery mapper2 = sqlSession2.getMapper(MasterStudentQuery.class);
                    studentList2.addAll(mapper2.selectAllStudent());//这里是从数据库中获取数据:
                    studentTableView.setItems(studentList2);
                    //对表格的设置进行恢复:
                    studentTableView.setFocusTraversable(false);//设置表格不可编辑:
                    studentTableView.setPrefSize(560, 700); // 设置表格的宽度为400像素，高度为300像素
                    sqlSession2.close(); // 关闭会话

                    //对表格的设置进行恢复:
                    //取消对ContextMenu的绑定:
                } else {
                    //不进行操作:给出弹窗:操作取消;
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("数据更新提示");
                    alert3.setHeaderText(null);
                    alert3.setContentText("数据更新取消!");
                    alert3.showAndWait();


                    //取消对ContextMenu的绑定:
                }
                studentTableView.setFocusTraversable(false);//设置表格不可编辑:
                studentTableView.setContextMenu(null);
            });

        });
    }

    private static List<BigInteger> getBigIntegers(TableView<student> studentTableView) {
        ObservableList<student> selectedItems = studentTableView.getSelectionModel().getSelectedItems();//获取选中的学生信息:
        //获取选中的学生信息的学号:
        List<BigInteger> schoolIds = new ArrayList<>();
        for (student selectedItem : selectedItems) {
            schoolIds.add(selectedItem.getSchoolId());
        }
        return schoolIds;
    }

    //3.更新学生信息:在这里重新获取数据库中的学生信息,然后重新展示在窗口上面:
    public static void ActionOnUpdatePageButton(Button updateStudent, ObservableList<student> studentList, TableView<student> studentTableView) {
        updateStudent.setOnAction(event -> {
            //首先将原先的BorderPane rootSecond里面的数据完全抹除:
            studentList.clear();
            // 添加一些学生数据到列表中:
            SqlSession sqlSession = Util_sqlSession.getSQLSession();
            MasterStudentQuery mapper = sqlSession.getMapper(MasterStudentQuery.class);
            studentList.addAll(mapper.selectAllStudent());//这里是从数据库中获取数据:
            studentTableView.setItems(studentList);//设置表格的数据源为studentList
            studentTableView.setPrefSize(560, 700); // 设置表格的宽度为400像素，高度为300像素
            sqlSession.close(); // 关闭会话
            //再给出一个弹窗:
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("数据更新提示");
            alert.setHeaderText(null);
            alert.setContentText("数据更新成功!");
            alert.showAndWait();
        });
    }

    //进行考勤信息的添加更新:
    public static void Page_ActionOnAttendanceInfoAndAdd(MenuItem attendanceInfo, BorderPane rootSecond, Stage stage) {
        attendanceInfo.setOnAction(event -> {
            //首先清空rootSecond中的所有元素:
            rootSecond.getChildren().clear();
            //在最上面设置一个班级展示的状态:0/1/2;
            AtomicReference<Integer> classShowCode = new AtomicReference<>(0);

            TableView<attendanceMessage> messageTableView = new TableView<>();

            //创建一个最顶层的HBox
            HBox hBoxAll = new HBox();
            VBox vBoxForButtonAndMenu = new VBox();//创建一个用来放置按键的VBox,大小为250*600:
            //在最左边设置两个按钮:
            Button dailyWork = new Button("每日更新");
            dailyWork.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            Button codeTypeReload = new Button("数据刷新");
            codeTypeReload.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            Button saveChange = new Button("保存修改");
            saveChange.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            //在最上边添加一个菜单栏:
            MenuBar menuBar = new MenuBar();
            // 创建菜单:班级选择;
            Menu classChoose = new Menu("选择班级");
            classChoose.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            MenuItem class1 = new MenuItem("一班");

            MenuItem class2 = new MenuItem("二班");

            classChoose.getItems().addAll(class1, class2);
            menuBar.getMenus().add(classChoose);
            //设置按钮的大小:
            codeTypeReload.setPrefSize(90, 50);
            saveChange.setPrefSize(90, 50);
            dailyWork.setPrefSize(90, 50);
            vBoxForButtonAndMenu.getChildren().addAll(menuBar, dailyWork, codeTypeReload, saveChange);
            //设置hBox当中的按键的间隔距离:
            vBoxForButtonAndMenu.setSpacing(60);
            vBoxForButtonAndMenu.setAlignment(Pos.TOP_CENTER);//设置按钮只是在x轴上面居中,而在y轴上面不进行居中:

            ContextMenu contextMenu = new ContextMenu();
            MenuItem codeEditMenuItem = new MenuItem("考勤修改");
            codeEditMenuItem.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");
            // 向ContextMenu添加菜单项
            contextMenu.getItems().addAll(codeEditMenuItem);

            messageTableView.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';-fx-font-size: 15");

            messageTableView.setContextMenu(contextMenu);//将ContextMenu与TableView关联

            // 创建表格列
            TableColumn<attendanceMessage, String> schoolIdColumn = new TableColumn<>("学号");
            schoolIdColumn.setCellValueFactory(new PropertyValueFactory<>("schoolId"));
            schoolIdColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> studentNameColumn = new TableColumn<>("姓名");
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            studentNameColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> classMessageColumn = new TableColumn<>("班级");
            classMessageColumn.setCellValueFactory(new PropertyValueFactory<>("classMessage"));
            classMessageColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> classNameColumn = new TableColumn<>("课程名");
            classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
            classNameColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> classTimeColumn = new TableColumn<>("课程节次");
            classTimeColumn.setCellValueFactory(new PropertyValueFactory<>("classTime"));
            classTimeColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> classIdColumn = new TableColumn<>("课程编号");
            classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
            classIdColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> codeTypeColumn = new TableColumn<>("考勤类型");
            codeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("codeType"));
            codeTypeColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> classWeekColumn = new TableColumn<>("星期数");
            classWeekColumn.setCellValueFactory(new PropertyValueFactory<>("classWeek"));
            classWeekColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            TableColumn<attendanceMessage, String> dateColumn = new TableColumn<>("课程日期");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            dateColumn.setStyle("-fx-font-family: 'Calibri';-fx-alignment: CENTER;");

            studentNameColumn.setPrefWidth(100); // 设置学号列的宽度为100像素
            //设置文本居中展示:
            studentNameColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            schoolIdColumn.setPrefWidth(100); // 设置姓名列的宽度为100像素
            schoolIdColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            classMessageColumn.setPrefWidth(50); // 设置性别列的宽度为100像素
            classMessageColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            classTimeColumn.setPrefWidth(80); // 设置班级列的宽度为100像素
            classTimeColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            classNameColumn.setPrefWidth(120); // 设置年龄列的宽度为100像素
            classNameColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            classIdColumn.setPrefWidth(80); // 设置年龄列的宽度为100像素
            classIdColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            codeTypeColumn.setPrefWidth(80); // 设置年龄列的宽度为100像素
            codeTypeColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            classWeekColumn.setPrefWidth(70); // 设置年龄列的宽度为100像素
            classWeekColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            dateColumn.setPrefWidth(100); // 设置年龄列的宽度为100像素
            dateColumn.setStyle("-fx-alignment: CENTER;-fx-font-family: 'Calibri';");
            //设置表格的高度为600像素,宽度为900像素:
            messageTableView.setPrefSize(780, 500);

            // 添加表格列到表格视图
            messageTableView.getColumns().addAll(Arrays.asList(schoolIdColumn, studentNameColumn, classMessageColumn, classNameColumn, classTimeColumn, classIdColumn, codeTypeColumn, classWeekColumn, dateColumn));
            // 创建一个 ObservableList 来保存学生数据
            ObservableList<attendanceMessage> messageList = FXCollections.observableArrayList();
            // 添加一些学生数据到列表中:
            SqlSession sqlSession = Util_sqlSession.getSQLSession();
            final ArrayList<attendanceMessage> attendanceMessages1 = sqlSession.getMapper(MasterInitStudentDailyMessage_SQL.class).selectALLStudentDailyMessage(Date.valueOf(LocalDate.now()));//这里是从数据库中获取数据:
            //判断数据源是否为空:
            if (attendanceMessages1.isEmpty()) {
                //就进行数据刷新:展示此前的信息;
                ArrayList<attendanceMessage> attendanceMessages2 = sqlSession.getMapper(MasterInitStudentDailyMessage_SQL.class).selectAllAttendanceMessage();//这里是从数据库中获取数据:
                messageList.addAll(attendanceMessages2);
            } else {
                messageList.addAll(attendanceMessages1);
            }

            sqlSession.close(); // 关闭会话
            // 设置 TableView 的数据源为messageList
            messageTableView.setItems(messageList);
            //设置表格无法编辑:
            messageTableView.setEditable(false);
            messageTableView.setFocusTraversable(false);//设置表格不可编辑:


            hBoxAll.getChildren().addAll(vBoxForButtonAndMenu, messageTableView);

            rootSecond.setCenter(hBoxAll);//将这个VBox添加到rootSecond中:

            //进行事件绑定:
            //0.每日基础更新:
            ActionOnDailyWork(dailyWork, messageList, messageTableView);

            //1.考勤修改:
            ActionOnCodeEdit(codeEditMenuItem, messageTableView, messageList,stage,classShowCode);

            //2.信息刷新:
            ActionOnCodeTypeReload(codeTypeReload, messageList, messageTableView, classShowCode);

            //保存修改:
            ActionOnSaveChange(saveChange);

            //4.班级选择:
            ActionOnClass1(class1, messageList, messageTableView, classShowCode);

            ActionOnClass2(class2, messageList, messageTableView, classShowCode);
        });
    }

    //对所有动作监听进行封装:
    //1.日常工作的更新:
    public static void ActionOnDailyWork(Button dailyWork, ObservableList<attendanceMessage> messageList, TableView<attendanceMessage> messageTableView) {
        dailyWork.setOnAction(event -> {
            final SqlSession sqlSession2 = Util_sqlSession.getSQLSession();
            ArrayList<attendanceMessage> attendanceMessages = sqlSession2.getMapper(MasterInitStudentDailyMessage_SQL.class).CheckStudentDailyMessage(Date.valueOf(LocalDate.now()));
            if (attendanceMessages.isEmpty()) {
                //首先判断今天用不用进行考勤信息的更新:
                //获取当前的星期数:
                int week = LocalDate.now().getDayOfWeek().getValue();
                if (week > 5) {
                    //展示
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("异常工作日期提示");
                    alert.setHeaderText(null);
                    alert.setContentText("      " + Date.valueOf(LocalDate.now()) + "为星期 " + week + "  \n     无可更新数据~" + "   \n将为你展示此前数据!");
                    alert.showAndWait();
                    //在列表中显示:今日无课程,无需初始化考勤信息;
                    final SqlSession sqlSession1 = Util_sqlSession.getSQLSession();
                    final ArrayList<attendanceMessage> attendanceMessages2 = sqlSession1.getMapper(MasterInitStudentDailyMessage_SQL.class).selectAllAttendanceMessage();//这里是从数据库中获取数据:
                    messageList.clear();
                    messageList.addAll(attendanceMessages2);
                    messageTableView.setItems(messageList);
                    //设置表格无法编辑:
                    messageTableView.setEditable(false);
                    messageTableView.setFocusTraversable(false);//设置表格不可编辑:

                } else {
                    //进行当天信息的初始化,并且展示在窗口上面:
                    MasterInitStudentDailyMessage_Util.initStudentDailyMessage();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("数据更新提示");
                    alert.setHeaderText(null);
                    alert.setContentText(Date.valueOf(LocalDate.now()) + "\n考勤信息已初始化完成!\n共初始化" + attendanceMessages.size() + "条数据!");

                    final SqlSession sqlSession3 = Util_sqlSession.getSQLSession();
                    ArrayList<attendanceMessage> attendanceMessages2 = sqlSession3.getMapper(MasterInitStudentDailyMessage_SQL.class).CheckStudentDailyMessage(Date.valueOf(LocalDate.now()));

                    messageList.clear();
                    messageList.addAll(attendanceMessages2);
                    messageTableView.setItems(messageList);
                    //设置表格无法编辑:
                    messageTableView.setEditable(false);
                    messageTableView.setFocusTraversable(false);//设置表格不可编辑:
                    alert.showAndWait();
                    sqlSession3.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("数据状态提示");
                alert.setHeaderText(null);
                alert.setContentText(Date.valueOf(LocalDate.now()) + "\n考勤信息已被初始化!\n无需重复初始化,正在检查是否有新添加的学生信息");
                alert.showAndWait();
                //这时候:数据库中已经有了今天的考勤信息,我们如果要对其进行人员添加方向的修改,就可以进行;
                //注意:每一次进入数据库都会进行数据更新校验;
                final SqlSession sqlSession1 = Util_sqlSession.getSQLSession();
                HashSet<BigInteger> messageStudent = new HashSet<>();
                for (attendanceMessage aMessage : attendanceMessages) {
                    messageStudent.add(aMessage.getSchoolId());
                }

                HashSet<BigInteger> studentSet = new HashSet<>();
                ArrayList<student> students = sqlSession1.getMapper(MasterStudentQuery.class).selectAllStudent();
                for (student student : students) {
                    studentSet.add(student.getSchoolId());
                }


                final ArrayList<BigInteger> differentElementsList = getBigIntegers(messageStudent, studentSet);
                //根据变化是否发生,进行数据库的更新:
                if (!differentElementsList.isEmpty()) {
                    System.out.println("对新添加的学生考勤初始化!");
                    //然后在Student表中进行查询;得到具体的学生信息,然后根据学生信息进行考勤信息的初始化:
                    ArrayList<student> students1 = sqlSession1.getMapper(MasterStudentQuery.class).selectStudentByList(differentElementsList);
                    //进行考勤信息的初始化:
                    LocalDate currentDate = LocalDate.now();
                    Date date = Date.valueOf(currentDate);
                    //获取当前的星期数:
                    int week = currentDate.getDayOfWeek().getValue();
                    //根据星期数获取当日的课程信息:
                    final ArrayList<course> class1Courses = sqlSession1.getMapper(MasterQueryCourse.class).selectAllCourse_class1(week);
                    final ArrayList<course> class2Courses = sqlSession1.getMapper(MasterQueryCourse.class).selectAllCourse_class2(week);
                    students1.forEach(student -> {
                        //针对每个学生:获取班级信息-->查询课程信息-->初始化考勤信息;
                        if (student.getClassMessage() == 1) {
                            class1Courses.forEach(course -> {
                                //初始化考勤信息:
                                sqlSession1.getMapper(MasterInitStudentDailyMessage_SQL.class).insertStudentDailyMessage(student.getSchoolId(), student.getName(), student.getClassMessage(), course.getClassName(), course.getClassTime(), course.getClassId(), 1, week, date);
                            });
                        } else {
                            class2Courses.forEach(course -> {
                                //初始化考勤信息:
                                sqlSession1.getMapper(MasterInitStudentDailyMessage_SQL.class).insertStudentDailyMessage(student.getSchoolId(), student.getName(), student.getClassMessage(), course.getClassName(), course.getClassTime(), course.getClassId(), 1, week, date);
                            });
                        }
                    });
                    sqlSession1.commit();
                    sqlSession1.close();
                    //现在有情况:
                    //数据库中的学生信息发生了添加:
                } else {
                    System.out.println("数据库中的学生信息没有发生变化!");
                }
            }

        });

    }

    private static ArrayList<BigInteger> getBigIntegers(HashSet<BigInteger> messageStudent, HashSet<BigInteger> studentSet) {
        HashSet<String> messageStudentStrings = new HashSet<>();
        for (BigInteger element : messageStudent) {
            messageStudentStrings.add(element.toString());
        }

        HashSet<String> studentSetStrings = new HashSet<>();
        for (BigInteger element : studentSet) {
            studentSetStrings.add(element.toString());
        }

        // 创建一个新的ArrayList来存储不同的元素
        ArrayList<BigInteger> differentElementsList = new ArrayList<>();

        // 找出字符串不同的元素，并将其转换为BigInteger类型加入到differentElementsList中
        for (String element : studentSetStrings) {
            if (!messageStudentStrings.contains(element)) {
                differentElementsList.add(new BigInteger(element));
            }
        }
        return differentElementsList;
    }

    //2.考勤信息的修改:
    public static void ActionOnCodeEdit(MenuItem codeEditMenuItem, TableView<attendanceMessage> messageTableView, ObservableList<attendanceMessage> messageList, Stage stage,AtomicReference<Integer> classShowCode) {
        codeEditMenuItem.setOnAction(e0 -> {
            Page_MasterEditCodeType p = new Page_MasterEditCodeType();
            p.start(new Stage());
            p.setAttendanceMessage(messageTableView.getSelectionModel().getSelectedItem());
            messageList.clear();
            SqlSession s = Util_sqlSession.getSQLSession();
            //最开始要获取当前正在展示的班级信息:0/1/2;
            if (classShowCode.get() == 0) {
                //展示全部信息:
                ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectALLStudentDailyMessage(Date.valueOf(LocalDate.now()));
                messageList.addAll(attendanceMessages2);
            } else if (classShowCode.get() == 1) {
                //展示一班信息:
                ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageByClass(1, Date.valueOf(LocalDate.now()));
                messageList.addAll(attendanceMessages2);
            } else if (classShowCode.get() == 2) {
                //展示二班信息:
                ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageByClass(2, Date.valueOf(LocalDate.now()));
                messageList.addAll(attendanceMessages2);
            }
            messageTableView.setItems(messageList);//设置表格的数据源为messageList
            messageTableView.setPrefSize(780, 500);
            s.close(); // 关闭会话
            stage.show();
        });
    }

    //3.信息刷新:
    public static void ActionOnCodeTypeReload(Button codeTypeReload, ObservableList<attendanceMessage> messageList, TableView<attendanceMessage> messageTableView, AtomicReference<Integer> classShowCode) {
        codeTypeReload.setOnAction(e1 -> {
            //判断今天是不是工作日:
            int week = LocalDate.now().getDayOfWeek().getValue();
            if (week > 5) {
                //在列表中显示:今日无课程,无需初始化考勤信息
                final SqlSession sqlSession1 = Util_sqlSession.getSQLSession();
                final ArrayList<attendanceMessage> attendanceMessages2 = sqlSession1.getMapper(MasterInitStudentDailyMessage_SQL.class).selectAllAttendanceMessage();//这里是从数据库中获取数据:
                messageList.clear();
                messageList.addAll(attendanceMessages2);
                messageTableView.setItems(messageList);
                //设置表格无法编辑:
                messageTableView.setEditable(false);
                messageTableView.setFocusTraversable(false);//设置表格不可编辑:
            } else {
                messageList.clear();
                SqlSession s = Util_sqlSession.getSQLSession();
                //最开始要获取当前正在展示的班级信息:0/1/2;
                if (classShowCode.get() == 0) {
                    //展示全部信息:
                    ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectALLStudentDailyMessage(Date.valueOf(LocalDate.now()));
                    messageList.addAll(attendanceMessages2);
                } else if (classShowCode.get() == 1) {
                    //展示一班信息:
                    ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageByClass(1, Date.valueOf(LocalDate.now()));
                    messageList.addAll(attendanceMessages2);
                } else if (classShowCode.get() == 2) {
                    //展示二班信息:
                    ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageByClass(2, Date.valueOf(LocalDate.now()));
                    messageList.addAll(attendanceMessages2);
                }
                messageTableView.setItems(messageList);//设置表格的数据源为messageList
                messageTableView.setPrefSize(780, 500);
                s.close(); // 关闭会话
            }


            //再给出一个弹窗:
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("数据更新提示");
            alert.setHeaderText(null);
            alert.setContentText("数据更新成功!");
            alert.showAndWait();
        });
    }

    //4.保存修改:
    public static void ActionOnSaveChange(Button saveChange) {
        saveChange.setOnAction(e2 -> {
            //再给出一个弹窗:
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("数据保存提示");
            alert.setHeaderText(null);
            alert.setContentText("数据保存成功!");
            alert.showAndWait();
        });
    }

    //5.1:选择一班:
    public static void ActionOnClass1(MenuItem class1, ObservableList<attendanceMessage> messageList, TableView<attendanceMessage> messageTableView, AtomicReference<Integer> classShowCode) {
        class1.setOnAction(e3 -> {
            //展示一班信息:
            //并将classShowCode设置为1;
            classShowCode.set(1);
            int week = LocalDate.now().getDayOfWeek().getValue();
            if (week > 5) {
                //进行过滤显示:
                ObservableList<attendanceMessage> messageList2 = FXCollections.observableArrayList();
                for (Models.Entities.attendanceMessage attendanceMessage : messageList) {
                    if (attendanceMessage.getClassMessage() == 1) {
                        messageList2.add(attendanceMessage);
                    }
                }
                messageTableView.setItems(messageList2);
                messageTableView.setPrefSize(780, 500);
            } else {
                messageList.clear();
                SqlSession s = Util_sqlSession.getSQLSession();
                ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageByClass(1, Date.valueOf(LocalDate.now()));
                messageList.addAll(attendanceMessages2);
                messageTableView.setItems(messageList);//设置表格的数据源为messageList
                messageTableView.setPrefSize(780, 500);
                s.close(); // 关闭会话
            }

        });
    }

    //5.2:选择二班:
    public static void ActionOnClass2(MenuItem class2, ObservableList<attendanceMessage> messageList, TableView<attendanceMessage> messageTableView, AtomicReference<Integer> classShowCode) {
        class2.setOnAction(e4 -> {
            //展示二班信息:
            //并将classShowCode设置为2;
            classShowCode.set(2);
            int week = LocalDate.now().getDayOfWeek().getValue();
            if (week > 5) {
                //进行过滤显示:
                ObservableList<attendanceMessage> messageList2 = FXCollections.observableArrayList();
                for (Models.Entities.attendanceMessage attendanceMessage : messageList) {
                    if (attendanceMessage.getClassMessage() == 2) {
                        messageList2.add(attendanceMessage);
                    }
                }
                messageTableView.setItems(messageList2);
                messageTableView.setPrefSize(780, 500);
            } else {
                messageList.clear();
                SqlSession s = Util_sqlSession.getSQLSession();
                ArrayList<attendanceMessage> attendanceMessages2 = s.getMapper(MasterInitStudentDailyMessage_SQL.class).selectStudentDailyMessageByClass(2, Date.valueOf(LocalDate.now()));
                messageList.addAll(attendanceMessages2);
                messageTableView.setItems(messageList);//设置表格的数据源为messageList
                messageTableView.setPrefSize(780, 500);
                s.close(); // 关闭会话
            }
        });
    }

}
