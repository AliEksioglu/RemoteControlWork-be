package tr.com.anser.offiicematrix.officematrixmanagement.model.user;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("users")
public class User {
    @Id
    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private boolean isAdmin;

    public User(String username , String name , String email , String password,boolean isActive ,boolean isAdmin){
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

}
