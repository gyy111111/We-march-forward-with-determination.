package Models.Utils.MasterMain;

import Models.Mappers.Main.MasterInitStudentDailyMessage_SQL;
import Models.Utils.All.Util_sqlSession;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Models.Entities.attendanceMessage;
import lombok.Setter;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Setter
public class Page_MasterEditCodeType extends Application {
    private attendanceMessage attendanceMessage;
    @Override
    public void start(Stage stage) {
        //设置这个窗口被放在最上面,不关闭这个页面或者完成这个页面就无法点击其他的页面;
        stage.initModality(Modality.APPLICATION_MODAL);//设置这个窗口不关闭,其他的窗口无法点击
        SqlSession sqlSession = Util_sqlSession.getSQLSession();
        // 创建复选框
        CheckBox absenteeismCheckBox = new CheckBox("旷课 :0");
        CheckBox normalCheckBox = new CheckBox("正常 :1");
        CheckBox lateCheckBox = new CheckBox("迟到 :2");
        CheckBox earlyLeaveCheckBox = new CheckBox("早退 :3");
        CheckBox leaveCheckBox = new CheckBox("请假 :4");
        //设置默认选中:正常
        normalCheckBox.setSelected(true);
        //设置只能是单选:
        absenteeismCheckBox.setOnAction(e -> {
            if (absenteeismCheckBox.isSelected()) {
                normalCheckBox.setSelected(false);
                lateCheckBox.setSelected(false);
                earlyLeaveCheckBox.setSelected(false);
                leaveCheckBox.setSelected(false);
            }
        });
        normalCheckBox.setOnAction(e -> {
            if (normalCheckBox.isSelected()) {
                absenteeismCheckBox.setSelected(false);
                lateCheckBox.setSelected(false);
                earlyLeaveCheckBox.setSelected(false);
                leaveCheckBox.setSelected(false);
            }
        });
        lateCheckBox.setOnAction(e -> {
            if (lateCheckBox.isSelected()) {
                normalCheckBox.setSelected(false);
                absenteeismCheckBox.setSelected(false);
                earlyLeaveCheckBox.setSelected(false);
                leaveCheckBox.setSelected(false);
            }
        });
        earlyLeaveCheckBox.setOnAction(e -> {
            if (earlyLeaveCheckBox.isSelected()) {
                normalCheckBox.setSelected(false);
                lateCheckBox.setSelected(false);
                absenteeismCheckBox.setSelected(false);
                leaveCheckBox.setSelected(false);
            }
        });
        leaveCheckBox.setOnAction(e -> {
            if (leaveCheckBox.isSelected()) {
                normalCheckBox.setSelected(false);
                lateCheckBox.setSelected(false);
                earlyLeaveCheckBox.setSelected(false);
                absenteeismCheckBox.setSelected(false);
            }
        });

        //创建一个确认按钮:
        Button confirmButton = new Button("确认");
        //设置按钮大小:
        confirmButton.setPrefSize(50, 20);
        confirmButton.setOnAction(e -> {
            if (absenteeismCheckBox.isSelected()) {
                System.out.println("旷课");
                attendanceMessage.setCodeType(0);
            } else if (normalCheckBox.isSelected()) {
                System.out.println("正常");
                attendanceMessage.setCodeType(1);
            } else if (lateCheckBox.isSelected()) {
                System.out.println("迟到");
                attendanceMessage.setCodeType(2);
            } else if (earlyLeaveCheckBox.isSelected()) {
                System.out.println("早退");
                attendanceMessage.setCodeType(3);
            } else if (leaveCheckBox.isSelected()) {
                System.out.println("请假");
                attendanceMessage.setCodeType(4);
            }
            //将数据更新到数据库中:

            sqlSession.getMapper(MasterInitStudentDailyMessage_SQL.class).updateStudentAttendanceMessage(attendanceMessage.getCodeType(), attendanceMessage.getSchoolId(), attendanceMessage.getClassId(), attendanceMessage.getDate());

            sqlSession.commit();
            sqlSession.close();
            stage.close();
            //重新更新数据源;
            //给出提示弹窗,并关闭当前窗口:
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("更新成功");
            alert.showAndWait();
            //在这里进行数据更新:

        });

        // 创建一个垂直布局
        VBox vbox = new VBox(10); // 设置垂直间距为10像素
        vbox.setAlignment(Pos.CENTER); // 设置对齐方式为居中
        vbox.getChildren().addAll(absenteeismCheckBox, normalCheckBox, lateCheckBox, earlyLeaveCheckBox, leaveCheckBox, confirmButton);

        // 设置场景并显示舞台
        Scene scene = new Scene(vbox, 200, 200);//设置大小为100*50
        stage.setScene(scene);
        stage.setTitle("选择");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

