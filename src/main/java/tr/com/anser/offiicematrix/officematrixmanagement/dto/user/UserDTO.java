package tr.com.anser.offiicematrix.officematrixmanagement.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String name;
    private String email;
    private boolean isActive;
    private boolean isAdmin;
}
