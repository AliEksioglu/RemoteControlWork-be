package tr.com.anser.offiicematrix.officematrixmanagement.controller;

import tr.com.anser.offiicematrix.officematrixmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService _userService) {
        userService = _userService;
    }

    @GetMapping("/userList")
    public ResponseEntity<?> getUsers() {
        return userService.getUserList();
    }

    @GetMapping("/userByUsername")
    public ResponseEntity<?> getUserByID(@RequestParam(value = "name") String name) {
        return userService.findByUsername(name);
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "username") String username) {
        return userService.findByUsername(username);
    }


}
