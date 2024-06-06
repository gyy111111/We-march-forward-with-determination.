package Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class student {
    private String name;
    private BigInteger schoolId;
    private Integer gender;
    private Short age;
    private Integer classMessage;
    private String passwords;
    private Integer loginCode;

}
