package tr.com.anser.offiicematrix.officematrixmanagement.model.holidayDaysOfYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("holiday")
public class Holiday {
    private String date;
    private String localName;
}
