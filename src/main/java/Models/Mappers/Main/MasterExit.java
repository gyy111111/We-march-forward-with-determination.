package Models.Mappers.Main;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterExit {
    void updateMaster(String work_id);
}
