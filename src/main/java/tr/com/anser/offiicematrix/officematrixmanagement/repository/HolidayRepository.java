package tr.com.anser.offiicematrix.officematrixmanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.com.anser.offiicematrix.officematrixmanagement.model.holidayDaysOfYear.Holiday;

@Repository
public interface HolidayRepository extends MongoRepository<Holiday, String>{
    Holiday findByDate(String name);

}

