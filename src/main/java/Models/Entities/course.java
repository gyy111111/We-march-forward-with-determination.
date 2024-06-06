package Models.Entities;

import lombok.Data;

//这个类是用来存储课程信息的:
@Data
public class course {
    private Integer classId;
    private String className;
    private Integer classWeek;
    private Integer classTime;
}
