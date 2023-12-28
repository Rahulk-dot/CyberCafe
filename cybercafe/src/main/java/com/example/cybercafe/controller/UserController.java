package com.example.cybercafe.controller;

import com.example.cybercafe.entity.Users;
import com.example.cybercafe.model.AddUserDTO;
import com.example.cybercafe.model.ComputerIdAndUserIdDTO;
import com.example.cybercafe.model.StandardResponse;
import com.example.cybercafe.model.UserIdDTO;
import com.example.cybercafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<StandardResponse<Users>> addUser(@RequestBody AddUserDTO userDTO) {
        try {
            Users addedUser = userService.addUser(userDTO);
            userService.assignComputerToUserAtBeginning(userDTO);
            return new ResponseEntity<>(new StandardResponse<>(addedUser, "Users added", HttpStatus.CREATED.value()), HttpStatus.CREATED);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponseEntity<>(new StandardResponse<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateAssignedComp")
    public ResponseEntity<StandardResponse<Users>> updateAssignedComputer(@RequestBody UserIdDTO user){
        Long user_id = user.getUser_id();
        return userService.updateAssignedComputerToNull(user_id);
    }

    @GetMapping("/getUserById")
    public ResponseEntity<StandardResponse<Users>> getUserById(@RequestBody UserIdDTO userIdDTO){
        try{
            Optional<Users> user = userService.getUserById(userIdDTO.getUser_id());
            return new ResponseEntity<>(new StandardResponse<>(user.get(), "User Found", HttpStatus.OK.value()), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new StandardResponse<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/assignComputerToUser")
    public ResponseEntity<StandardResponse<Users>> assignComputerToUser(@RequestBody ComputerIdAndUserIdDTO computerIdAndUserIdDTO){
        try{
            Long user_id = computerIdAndUserIdDTO.getUser_id();
            Long comp_id = computerIdAndUserIdDTO.getComp_id();
            StandardResponse<Users> standardResponse = userService.assignComputerToUser(user_id,comp_id);
            return new ResponseEntity<>(standardResponse, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new StandardResponse<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteUserById/{user_id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long user_id){
        boolean isDeleted = userService.deleteUserById(user_id);

        if(isDeleted){
            return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
        }

        return new ResponseEntity<>("Not Deleted",HttpStatus.BAD_REQUEST);
    }


}

