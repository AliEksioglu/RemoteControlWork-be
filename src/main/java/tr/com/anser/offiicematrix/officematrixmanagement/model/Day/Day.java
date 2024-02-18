package tr.com.anser.offiicematrix.officematrixmanagement.model.Day;

import tr.com.anser.offiicematrix.officematrixmanagement.enums.DAYS_OF_WEEK_ENUM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    private DAYS_OF_WEEK_ENUM dayName;
    private String description;
    private String date;
}
