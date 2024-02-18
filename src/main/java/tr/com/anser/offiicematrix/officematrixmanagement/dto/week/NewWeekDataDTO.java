package tr.com.anser.offiicematrix.officematrixmanagement.dto.week;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@AllArgsConstructor
@Data
public class NewWeekDataDTO {
    private String weekName;
    private List<String> daysOfWeek;
    private List<String> users;
}
