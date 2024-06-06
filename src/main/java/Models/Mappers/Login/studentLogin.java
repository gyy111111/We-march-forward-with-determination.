package Models.Mappers.Login;
import Models.Entities.student;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface studentLogin {
    //这个方法主要用于数据库信息的校验:
    public ArrayList<student> LoginSelect(String school_id, String passwords);
   //这个方法用于将登录状态更新的用户信息在数据库中更新;
    public void updateStudent(String school_id);
}
