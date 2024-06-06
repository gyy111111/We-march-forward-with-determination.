package Models.Utils.All;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sun.glass.ui.Window.getWindows;
import static javafx.stage.Stage.*;

public class Util_ForStage {
    //对于一个界面中的东西进行计时显示:
    public static void setTime(Label warningLabel, double time) {
        warningLabel.setVisible(true);
        // 使用Platform.runLater()方法延迟执行任务
        Platform.runLater(() -> {
            // 创建一个PauseTransition对象，设置持续时间为2秒
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(time));

            // 设置延迟结束后的动作
            pauseTransition.setOnFinished(event -> {
                // 在时间结束后隐藏警告标签
                warningLabel.setVisible(false);
            });
            // 启动延迟
            pauseTransition.play();
        });
    }
}