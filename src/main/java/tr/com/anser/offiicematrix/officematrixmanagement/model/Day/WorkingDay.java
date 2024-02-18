package tr.com.anser.offiicematrix.officematrixmanagement.model.Day;

import tr.com.anser.offiicematrix.officematrixmanagement.enums.DAYS_OF_WEEK_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.WORKING_TYPE_ENUM;
import lombok.Data;

@Data
public class WorkingDay extends Day{
    private WORKING_TYPE_ENUM workingType;

    public WorkingDay(){

    }
    public WorkingDay(DAYS_OF_WEEK_ENUM dayName, String date, String description, WORKING_TYPE_ENUM workingType) {
        super(dayName, date, description);
        this.workingType = workingType;
    }
    public WORKING_TYPE_ENUM getWorkingType(){
        return workingType;
    }
    public void setWorkingType(WORKING_TYPE_ENUM workingType){
        this.workingType = workingType;
    }
}
