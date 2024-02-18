package tr.com.anser.offiicematrix.officematrixmanagement.model.week;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.DAYS_OF_WEEK_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.model.Day.WorkingDay;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Hashtable;
import java.util.List;
@NoArgsConstructor
@Data
@Document("weeksData")
public class WeekData {
    @Id
    private String id;
    private String weekName;
    private Hashtable<String,Hashtable<DAYS_OF_WEEK_ENUM,WorkingDay>> userDays = new Hashtable<>();
    private List<String> daysOfWeek;
    public WeekData(String weekName,Hashtable<String,Hashtable<DAYS_OF_WEEK_ENUM,WorkingDay>> userDays,List<String> daysOfWeek) {
        this.weekName = weekName;
        this.userDays = userDays;
        this.daysOfWeek = daysOfWeek;
    }
    public String getEndOfWeekDate(){
        return daysOfWeek.get(6);
    }
    public String getStartOfWeekDate(){
        return daysOfWeek.get(0);
    }
}
