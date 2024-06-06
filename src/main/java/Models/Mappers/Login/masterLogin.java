package Models.Mappers.Login;

import Models.Entities.Master;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface masterLogin {
    ArrayList<Master> LoginSelect(String work_id, String passwords);
    void updateMaster(String work_id);

}
