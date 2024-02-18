package tr.com.anser.offiicematrix.officematrixmanagement.controller;

import tr.com.anser.offiicematrix.officematrixmanagement.dto.week.NewWeekDataDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.model.week.UpdateWeekUserData;
import tr.com.anser.offiicematrix.officematrixmanagement.service.WeekDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/weeks")
public class WeekDataController{
    private final WeekDataService weekDataService;
    public WeekDataController(WeekDataService _weekDataService) {
        weekDataService = _weekDataService;
    }

    @GetMapping("/getAllWeekData")
    public ResponseEntity<?> getAllWeekData() {
        return weekDataService.getAllWeekData();
    }

    @GetMapping("/getWeekByName")
    public ResponseEntity<?> getWeekByName(@RequestParam(value = "weekName") String weekName) {
        return weekDataService.findByWeekName(weekName);
    }
    @PostMapping("/addWeek")
    public ResponseEntity<?> createWeekUserData(@RequestBody NewWeekDataDTO weekDataDTO) {
        return weekDataService.createWeekData(weekDataDTO);
    }
    @PostMapping("/updateWeekUserData")
    public ResponseEntity<?> updateWeekUserData(@RequestBody UpdateWeekUserData updateWeeklyUserData) {
        return weekDataService.updateWeekUserData(updateWeeklyUserData);
    }
    @PostMapping("/updateWeekData")
    public ResponseEntity<?> updateWeekUser(@RequestBody NewWeekDataDTO weekDataDTO,@RequestParam(value = "oldWeekName") String oldWeekName) {
        return weekDataService.updateWeekData(weekDataDTO, oldWeekName);
    }
    @DeleteMapping("/deleteWeek")
    public ResponseEntity<?> deleteWeekData(@RequestParam(value = "weekName")String weekName){
        return weekDataService.deleteWeekData(weekName);

    }
}
