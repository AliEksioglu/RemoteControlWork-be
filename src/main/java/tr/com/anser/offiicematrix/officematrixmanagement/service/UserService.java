package tr.com.anser.offiicematrix.officematrixmanagement.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.RESPONSE_TYPE_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.response.ResponseDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.RME;
import tr.com.anser.offiicematrix.officematrixmanagement.model.response.ResponseMessage;
import tr.com.anser.offiicematrix.officematrixmanagement.model.user.User;
import tr.com.anser.offiicematrix.officematrixmanagement.dto.user.UserDTO;
import tr.com.anser.offiicematrix.officematrixmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean CreateUser(User user){
        try {
            User newUser = new User(
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    false,
                    user.isAdmin()
            );
            userRepository.save(newUser);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    public ResponseEntity<?> getUserList() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<UserDTO> userDTOArray = new ArrayList<UserDTO>();
            List<User> dbUserArray = userRepository.findAll();
            for(User user: dbUserArray){
                userDTOArray.add(new UserDTO(
                        user.getUsername(),
                        user.getName(),
                        user.getEmail(),
                        user.isActive(),
                        user.isAdmin()
                ));
            }
            response.setResponseData(userDTOArray,"", RESPONSE_TYPE_ENUM.SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> findByUsername(String username) {
        ResponseDTO response = new ResponseDTO();
        try {
            User user = userRepository.findByUsername(username);
            UserDTO userDTO = new UserDTO(
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.isActive(),
                    user.isAdmin()
            );
            response.setResponseData(userDTO,"", RESPONSE_TYPE_ENUM.SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteUser(String username){
        ResponseDTO response = new ResponseDTO();
        try{
            User user = userRepository.findByUsername(username);
            if(user != null){
                userRepository.deleteByUsername(username);
                response.setResponseMessage(ResponseMessage.getRM(RME.SUCCESS_DELETE_USER), RESPONSE_TYPE_ENUM.SUCCESS);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }else{
                response.setResponseMessage(ResponseMessage.getRM(RME.NOT_FOUND_USERNAME), RESPONSE_TYPE_ENUM.ERROR);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }

        }catch (Exception e){
            response.setResponseMessage(e.getMessage(), RESPONSE_TYPE_ENUM.ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
