package com.example.cybercafe.mapper;

import com.example.cybercafe.entity.Computer;
import com.example.cybercafe.entity.Users;
import com.example.cybercafe.model.AddComputerDTO;
import com.example.cybercafe.model.AddUserDTO;

public class RemoteCacheMapper {

    public static Users toUsers(AddUserDTO addUserDTO, Computer computer){
        return new Users(addUserDTO.getUser_name(),addUserDTO.getUser_ph_number(),computer);
    }

    public static Computer toComputer(AddComputerDTO addComputerDTO){
        return new Computer(addComputerDTO.getComp_name(), addComputerDTO.getComp_details(), addComputerDTO.getComp_add());
    }
}

