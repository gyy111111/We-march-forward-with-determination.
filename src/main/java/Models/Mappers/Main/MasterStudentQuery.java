package Models.Mappers.Main;

import Models.Entities.student;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MasterStudentQuery {
    ArrayList<student> selectAllStudent();

    //查询单个学生信息,判断学生是否存在:
    student selectStudentById(String id);

    //查询一班的学生信息:
    ArrayList<student> selectClass1Student();

    //查询二班的学生信息:
    ArrayList<student> selectClass2Student();

    //根据集合查询学生信息:
    ArrayList<student> selectStudentByList(List<BigInteger> student_id);
}
