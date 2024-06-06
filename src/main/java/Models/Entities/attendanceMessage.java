package Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class attendanceMessage {
    private BigInteger schoolId;
    private String studentName;
    private Integer classMessage;
    private String className;
    private String classTime;//这是每天上课的第几节课;
    private Integer classId;//这是课程的编号;
    private Integer codeType;//这是考勤的类型;
    private Integer classWeek;//这是星期几;
    private Date date;//这是日期;

}
