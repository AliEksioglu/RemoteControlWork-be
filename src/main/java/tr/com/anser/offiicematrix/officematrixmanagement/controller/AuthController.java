package tr.com.anser.offiicematrix.officematrixmanagement.controller;

import tr.com.anser.offiicematrix.officematrixmanagement.dto.auth.AuthDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.model.user.User;
import tr.com.anser.offiicematrix.officematrixmanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/logOut")
    public ResponseEntity<?> LogOut(@RequestParam(value = "username") String username){
        return authService.logOut(username);
    }
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody User user){
        return authService.register(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody AuthDTO loginDTO){
        return authService.login(loginDTO.getEmail(),loginDTO.getPassword());
    }
}
