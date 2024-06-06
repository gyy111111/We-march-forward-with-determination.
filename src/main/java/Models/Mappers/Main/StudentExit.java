package Models.Mappers.Main;

import org.apache.ibatis.annotations.Mapper;
//进行接口标注:
@Mapper
public interface StudentExit {
    //创建一个用于用户登出的时候状态改变的方法:
     void updateStudent(String school_id);
     void UpdateStudentPasswords(String school_id, String password);
}
