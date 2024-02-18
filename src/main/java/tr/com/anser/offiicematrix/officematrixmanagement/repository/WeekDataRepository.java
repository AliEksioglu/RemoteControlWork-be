package tr.com.anser.offiicematrix.officematrixmanagement.repository;

import tr.com.anser.offiicematrix.officematrixmanagement.model.week.WeekData;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDataRepository extends MongoRepository<WeekData, String>{
    WeekData findByWeekName(String name);
    @DeleteQuery
    void deleteByWeekName(String name);
}

