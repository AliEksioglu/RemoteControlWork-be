package tr.com.anser.offiicematrix.officematrixmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tr.com.anser.offiicematrix.officematrixmanagement.model.holidayDaysOfYear.Holiday;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import tr.com.anser.offiicematrix.officematrixmanagement.repository.HolidayRepository;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HolidayService {
    Logger logger = LoggerFactory.getLogger(HolidayService.class);
    private final RestTemplate restTemplate;

    private final HolidayRepository holidayRepository;

    @Value("${rapidapi.holiday.header.key}")
    private String rapidAPIHeaderKey = "X-Rapidapi-Key";
    @Value("${rapidapi.holiday.header.value}")
    private String rapidAPIHeaderValue = "1e781e3af1msha8ec69a13451384p1da07bjsn3dbb5774e742";
    @Value("${rapidapi.holiday.base-url}")
    private String rapidAPIHolidayBaseURL = "https://public-holiday.p.rapidapi.com/year/country";

    @Autowired
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public void getHolidaysForCountry(String country) {
        try {
            List<Holiday> listHoliday = holidayRepository.findAll();
            if((listHoliday.size() > 0)){
                getHolidayDates(country);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Holiday checkHolidayForDate(String date){
        try {
            return holidayRepository.findByDate(date);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }


    @Scheduled(cron = "0 0 0 1 1 ?") // Her yılın ilk günü saat 00:00'da çalışır
    private void getHolidayDates(String country) {
        holidayRepository.deleteAll();
        Year currentYear = Year.now();
        String url = rapidAPIHolidayBaseURL.replaceAll("year", currentYear.toString());
        url = url.replaceAll("country", country);
        HttpHeaders headers = new HttpHeaders();
        headers.set(rapidAPIHeaderKey, rapidAPIHeaderValue);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        ArrayList body = (ArrayList) response.getBody();
        System.out.println("#######################  Buraya geldi knk");
        body.forEach(element -> {
            Map<String,Object> holidayMap = (Map<String, Object>) element;
            String date = holidayMap.get("date").toString();
            date = date.replaceAll("-","/");
            holidayRepository.save(new Holiday(
                    date,
                    holidayMap.get("localName").toString()
            ));
        });
    }

}
