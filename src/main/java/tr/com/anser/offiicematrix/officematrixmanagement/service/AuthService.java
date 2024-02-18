package tr.com.anser.offiicematrix.officematrixmanagement.service;

import tr.com.anser.offiicematrix.officematrixmanagement.enums.RESPONSE_TYPE_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.RME;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.response.ResponseDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.model.response.ResponseMessage;
import tr.com.anser.offiicematrix.officematrixmanagement.model.user.User;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.user.UserDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    @Autowired
    public AuthService(UserRepository userRepository,UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> register(User user){
        ResponseDTO response = new ResponseDTO();
        try {
            if(userRepository.findByUsername(user.getUsername()) == null){
                if(userRepository.findByEmail(user.getEmail()) == null){
                    if(userService.CreateUser(user)){
                        response.setResponseMessage(ResponseMessage.getRM(RME.SUCCESS_REGISTER), RESPONSE_TYPE_ENUM.SUCCESS);
                        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
                    }else{
                        response.setResponseMessage("Error! not createUser",RESPONSE_TYPE_ENUM.ERROR);
                        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }else{
                    response.setResponseMessage(ResponseMessage.getRM(RME.HAS_ALLREAY_EMAIL),RESPONSE_TYPE_ENUM.ERROR);
                    return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
                }
            }else{
                response.setResponseMessage(ResponseMessage.getRM(RME.HAS_ALLREADY_USERNAME),RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
            }
        }catch (Exception e){
            response.setResponseMessage(e.getMessage(),RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> logOut(String username){
        ResponseDTO response = new ResponseDTO();
        try{
            User user = userRepository.findByUsername(username);
            user.setActive(false);
            response.setResponseMessage(ResponseMessage.getRM(RME.SUCCESS_LOGOUT),RESPONSE_TYPE_ENUM.SUCCESS);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }
    }
    public ResponseEntity<?> login(String email, String password){
        ResponseDTO response = new ResponseDTO();
        try{
            User user = userRepository.findByEmail(email);
            if(user != null){
                if(user.getPassword().equals(password)){
                    UserDTO userDTO = new UserDTO(
                            user.getUsername(),
                            user.getName(),
                            user.getEmail(),
                            true,
                            user.isAdmin()
                    );
                   response.setResponseData(
                            userDTO,
                            ResponseMessage.getRM(RME.SUCCESS_LOGIN),
                            RESPONSE_TYPE_ENUM.SUCCESS);
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }else{
                    response.setResponseMessage(ResponseMessage.getRM(RME.NOT_MATCH_PASSWORD), RESPONSE_TYPE_ENUM.ERROR);
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }

            }else{
                response.setResponseMessage(ResponseMessage.getRM(RME.NOT_FOUND_EMAIL), RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
