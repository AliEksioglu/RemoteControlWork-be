package tr.com.anser.offiicematrix.officematrixmanagement.model.week;

import tr.com.anser.offiicematrix.officematrixmanagement.enums.DAYS_OF_WEEK_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.WORKING_TYPE_ENUM;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateWeekUserData {
    private String weekName;
    private String username;
    private DAYS_OF_WEEK_ENUM dayName;
    private WORKING_TYPE_ENUM workingType;
}
