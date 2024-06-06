package Models.Mappers.Main;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MasterUpdateAndDeleteStudent {
    //删除学生信息
    void deleteStudent(List<BigInteger> schoolIds);

    //更新学生信息
    void updateStudent(List<BigInteger> schoolIds);
}
