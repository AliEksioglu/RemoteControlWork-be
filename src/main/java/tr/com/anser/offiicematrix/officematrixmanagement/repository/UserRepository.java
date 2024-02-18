package tr.com.anser.offiicematrix.officematrixmanagement.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import tr.com.anser.offiicematrix.officematrixmanagement.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);
    User findByEmail(String email);
    @DeleteQuery
    boolean deleteByUsername(String username);
}
