package tr.com.anser.offiicematrix.officematrixmanagement.dto.week;

import tr.com.anser.offiicematrix.officematrixmanagement.enums.DAYS_OF_WEEK_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.model.Day.WorkingDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Hashtable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeekDataDTO {
    private String weekName;
    private List<String> daysOfWeek;
    private Hashtable<String,Hashtable<DAYS_OF_WEEK_ENUM, WorkingDay>> userDays = new Hashtable<>();
}
