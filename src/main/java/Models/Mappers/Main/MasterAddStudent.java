package Models.Mappers.Main;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface MasterAddStudent {
    //添加学生信息:
    /*school_id, name, gender, age, classMessage, passwords, login_code*/
    void addStudent(String  school_id, String name,String  gender,String  age,String classMessage,String passwords,String  login_code);
}
