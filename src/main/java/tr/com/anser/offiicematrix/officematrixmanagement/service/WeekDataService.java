package tr.com.anser.offiicematrix.officematrixmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.DAYS_OF_WEEK_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.RESPONSE_TYPE_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.RME;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.WORKING_TYPE_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.model.Day.WorkingDay;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.response.ResponseDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.model.holidayDaysOfYear.Holiday;
import tr.com.anser.offiicematrix.officematrixmanagement.model.response.ResponseMessage;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.week.NewWeekDataDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.week.WeekDataDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.model.week.WeekData;
import tr.com.anser.offiicematrix.officematrixmanagement.model.week.UpdateWeekUserData;
import tr.com.anser.offiicematrix.officematrixmanagement.repository.HolidayRepository;
import tr.com.anser.offiicematrix.officematrixmanagement.repository.WeekDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class WeekDataService {
    Logger logger = LoggerFactory.getLogger(WeekDataService.class);
    private final HolidayService holidayService;
    private final WeekDataRepository weekDataRepository;
    private WeekData findTable;

    public WeekDataService(WeekDataRepository tableRepository,HolidayService holidayService) {
        this.holidayService = holidayService;
        this.weekDataRepository = tableRepository;
        holidayService.getHolidaysForCountry("tr");
    }

    public ResponseEntity<?> createWeekData(NewWeekDataDTO weekData) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            WeekData findTable = weekDataRepository.findByWeekName(weekData.getWeekName());
            if (findTable == null) {
                setOrCreateWeekData(weekData, null);
                List<WeekDataDTO> tableDTOArray = getAllWeekDTOArray();
                responseDTO.setResponseData(
                        tableDTOArray,
                        ResponseMessage.getRM(RME.SUCCESS_CREATE_WEEKDATA),
                        RESPONSE_TYPE_ENUM.SUCCESS
                );
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setResponseMessage(ResponseMessage.getRM(RME.HAS_ALLREADY_WEEKNAME), RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseDTO.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateWeekData(NewWeekDataDTO userData, String oldWeekName) {
        ResponseDTO response = new ResponseDTO();
        try {
            WeekData tableData = weekDataRepository.findByWeekName(oldWeekName);
            if (tableData != null) {
                WeekData weekData = setOrCreateWeekData(userData, oldWeekName);
                response.setResponseData(convertWeekDataDTO(weekData), ResponseMessage.getRM(RME.SUCCESS_UPDATE), RESPONSE_TYPE_ENUM.SUCCESS);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(ResponseMessage.getRM(RME.NOT_FOUND_WEEKNAME), RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateWeekUserData(UpdateWeekUserData updateWeekData) {
        ResponseDTO response = new ResponseDTO();
        try {
            WeekData weekData = weekDataRepository.findByWeekName(updateWeekData.getWeekName());
            if (weekData != null) {
                WorkingDay workDay = weekData.getUserDays().get(updateWeekData.getUsername()).get(updateWeekData.getDayName());
                workDay.setWorkingType(updateWeekData.getWorkingType());
                weekData.getUserDays().get(updateWeekData.getUsername()).put(workDay.getDayName(), workDay);
                weekDataRepository.save(weekData);
                response.setResponseData(convertWeekDataDTO(weekData), ResponseMessage.getRM(RME.SUCCESS_UPDATE), RESPONSE_TYPE_ENUM.SUCCESS);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(ResponseMessage.getRM(RME.NOT_FOUND_WEEKNAME), RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllWeekData() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<WeekDataDTO> tableDTOArray = getAllWeekDTOArray();
            responseDTO.setResponseData(tableDTOArray, "", RESPONSE_TYPE_ENUM.SUCCESS);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            responseDTO.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteWeekData(String weekName) {
        ResponseDTO response = new ResponseDTO();
        try {
            WeekData weekData = weekDataRepository.findByWeekName(weekName);
            if (weekData != null) {
                weekDataRepository.deleteByWeekName(weekName);
                response.setResponseData(getAllWeekDTOArray(), ResponseMessage.getRM(RME.SUCCESS_DELETE_WEEKDATA), RESPONSE_TYPE_ENUM.SUCCESS);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(ResponseMessage.getRM(RME.NOT_FOUND_WEEKNAME), RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findByWeekName(String weekName) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            WeekData weekData = weekDataRepository.findByWeekName(weekName);

            if (weekData != null) {
                WeekDataDTO weekDataDTO = convertWeekDataDTO(weekData);
                responseDTO.setResponseData(
                        weekDataDTO,
                        "",
                        RESPONSE_TYPE_ENUM.SUCCESS);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                responseDTO.setResponseMessage(
                        ResponseMessage.getRM(RME.NOT_FOUND_WEEKNAME),
                        RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseDTO.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private WeekData setOrCreateWeekData(NewWeekDataDTO addWeekDTO, String oldWeekName) {

        List<String> weekDateList = addWeekDTO.getDaysOfWeek();
        int index = 0; //Bu index gelen tarih listesinin içinde dönebilmek için yapııldı
        Hashtable<String, Hashtable<DAYS_OF_WEEK_ENUM, WorkingDay>> newUserDays = new Hashtable<>();
        WeekData dbWeeklydata = null;
        if (oldWeekName != null) {
            dbWeeklydata = weekDataRepository.findByWeekName(oldWeekName);
        }
        for (String username : addWeekDTO.getUsers()) {
            newUserDays.put(username, new Hashtable<DAYS_OF_WEEK_ENUM, WorkingDay>());
            index = 0;
            for (DAYS_OF_WEEK_ENUM day : DAYS_OF_WEEK_ENUM.values()) {
                if (dbWeeklydata != null && dbWeeklydata.getUserDays().containsKey(username)) {
                    WorkingDay workDay = dbWeeklydata.getUserDays().get(username).get(day);
                    newUserDays.get(username).put(day, workDay);
                } else {
                    //todo API kullanilirsa burada tatil günleri cekilen date gore ayarlanicak
                    WorkingDay workDay = new WorkingDay();
                    String date = weekDateList.get(index);
                    Holiday holiday = holidayService.checkHolidayForDate(date);
                    if(holiday != null){
                        workDay.setWorkingType(WORKING_TYPE_ENUM.HOLIDAY);
                        workDay.setDayName(day);
                        workDay.setDate(date);
                        workDay.setDescription(holiday.getLocalName());
                        newUserDays.get(username).put(day, workDay);
                    }else{
                        if (day == DAYS_OF_WEEK_ENUM.SATURDAY || day == DAYS_OF_WEEK_ENUM.SUNDAY) {
                            workDay.setWorkingType(WORKING_TYPE_ENUM.HOLIDAY);
                            workDay.setDayName(day);
                            workDay.setDate(weekDateList.get(index));
                            workDay.setDescription("Hafta sonu");
                            newUserDays.get(username).put(day, workDay);
                        } else {
                            workDay.setWorkingType(WORKING_TYPE_ENUM.OFFICE);
                            workDay.setDayName(day);
                            workDay.setDate(date);
                            workDay.setDescription("İş günü");
                            newUserDays.get(username).put(day, workDay);
                        }
                    }
                }
                index++;   // Burada gelen tarih listesinde dönebilmek için index arttırılıyor
            }
        }
        if (dbWeeklydata == null) {
            dbWeeklydata = new WeekData(addWeekDTO.getWeekName(), newUserDays, addWeekDTO.getDaysOfWeek());
        } else {
            dbWeeklydata.setUserDays(newUserDays);
            if (!addWeekDTO.getWeekName().equals(oldWeekName)) dbWeeklydata.setWeekName(addWeekDTO.getWeekName());
        }
        weekDataRepository.save(dbWeeklydata);
        return dbWeeklydata;
    }

    private WeekDataDTO convertWeekDataDTO(WeekData data) {
        return new WeekDataDTO(
                data.getWeekName(),
                data.getDaysOfWeek(),
                data.getUserDays()
        );
    }

    private List<WeekDataDTO> getAllWeekDTOArray() {
        try {
            List<WeekData> tableArray = weekDataRepository.findAll();
            List<WeekDataDTO> tableDTOArray = new ArrayList<>();
            for (WeekData data : tableArray) {
                WeekDataDTO tableDTO = new WeekDataDTO(
                        data.getWeekName(),
                        data.getDaysOfWeek(),
                        data.getUserDays()
                );
                tableDTOArray.add(tableDTO);
            }
            return tableDTOArray;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }
}
