package com.example.cybercafe.service;

import com.example.cybercafe.entity.Computer;
import com.example.cybercafe.mapper.RemoteCacheMapper;
import com.example.cybercafe.model.AddUserDTO;
import com.example.cybercafe.model.StandardResponse;
import com.example.cybercafe.repository.ComputerRepository;
import com.example.cybercafe.repository.UserRepository;
import com.example.cybercafe.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ComputerRepository computerRepository;

    @Autowired
    public UserService(UserRepository userRepository, ComputerRepository computerRepository) {
        this.userRepository = userRepository;
        this.computerRepository = computerRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users addUser(AddUserDTO addUserDTO) throws IOException{
        validateUser(addUserDTO);
        Long comp_id = Long.valueOf(addUserDTO.getAssigned_comp());
        Computer computer = computerRepository.findById(comp_id).orElseThrow(() -> {
            return new IOException("Not found with id");
        });
        if(computer.isIs_available()){
            Users user = RemoteCacheMapper.toUsers(addUserDTO, computer);
            return userRepository.save(user);
        }
        else{
            return null;
        }

    }
    @Transactional
    public void assignComputerToUserAtBeginning(AddUserDTO addUserDTO){
        Long comp_id = Long.valueOf(addUserDTO.getAssigned_comp());
        int checkAssignedComputer = computerRepository.assignComputerToUser(comp_id);
    }

    @Transactional
    public ResponseEntity<StandardResponse<Users>> updateAssignedComputerToNull(Long user_id){
        Optional<Users> user =  userRepository.findById(user_id);
        Computer computer = user.get().getAssigned_comp();

        int updateComp = computerRepository.unassignedComputerFromUser(computer.getComp_id());
        int updateUser = userRepository.updateUnassignedComp(user_id);

        if(updateUser > 0 && updateComp > 0){
            return new ResponseEntity<>(new StandardResponse<>(user.get(),"User and Computer updated", HttpStatus.OK.value()),HttpStatus.OK);
        }

        return new ResponseEntity<>(new StandardResponse<>(null,"Not Updated", HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);


    }

    public Optional<Users> getUserById(Long user_id){
        Optional<Users> user = userRepository.findById(user_id);

        return user;
    }

    @Transactional
    public StandardResponse<Users> assignComputerToUser(Long user_id, Long comp_id){
        Optional<Users> user = userRepository.findById(user_id);
        if(user.get().getAssigned_comp() != null){
            throw new IllegalArgumentException("User is already assigned a computer");
        }

        Optional<Computer> computer = computerRepository.findById(comp_id);

        if(!computer.get().isIs_available()){
            throw new IllegalArgumentException("Computer already assigned to another User");
        }


        int checkAssignedComp = userRepository.updateAssignedComp(computer.get(),user_id);
        int checkAssignedComputerToUser = computerRepository.assignComputerToUser(comp_id);

        if(checkAssignedComp > 0 && checkAssignedComputerToUser > 0){
            return new StandardResponse<>(user.get(), "Successfully Assigned Computer to User",HttpStatus.OK.value());
        }

        return new StandardResponse<>(null,"Computer Not Assigned to User", HttpStatus.BAD_REQUEST.value());
    }

    @Transactional
    public boolean deleteUserById(Long user_id){
        Optional<Users> userOptional = userRepository.findById(user_id);

        if(userOptional.isPresent()){
            Users user = userOptional.get();
            userRepository.delete(user);
            return true;
        }

        return false;
    }


    public void validateUser(AddUserDTO user){
        if(StringUtils.isEmpty(user.getUser_name())){
            throw new IllegalArgumentException("Username is required");
        }

        if(!isValidUsername(user.getUser_name())){
            throw new IllegalArgumentException("Invalid username format. ");
        }

        if(StringUtils.isEmpty(user.getUser_ph_number())){
            throw new IllegalArgumentException("Phone number is required.");
        }

        if(!isValidPhoneNumber(user.getUser_ph_number())){
            throw new IllegalArgumentException("Invalid phone number");
        }


    }

    public boolean isValidUsername(String username){
        return username.matches("^[a-zA-Z0-9 ]+$");
    }

    public boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber.matches("^\\d{10,}$");
    }
}

