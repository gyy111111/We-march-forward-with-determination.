package Models.Utils.StudentMain;

import Models.Entities.student;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class SceneFactory {
    //这个类是一个工具类:主要功能就是用来为主界面提供不同的场景;
    //这个类的作用就是为StudentMainController提供不同的场景;
    //1.对于初始化的界面,不进行场景的提供,因为这个默认界面需要绑定很多的事件,一旦抽取出来,就会导致事件的绑定出现问题;
    //2.对于查询的界面,我们需要提供一个查询的场景;
    public static void getQueryScene(Stage stage, student student) {
        //进行个体查询
    }

}
