package com.example.cybercafe.service;

import com.example.cybercafe.entity.Computer;
import com.example.cybercafe.mapper.RemoteCacheMapper;
import com.example.cybercafe.model.AddComputerDTO;
import com.example.cybercafe.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class ComputerService {

    private final ComputerRepository computerRepository;

    @Autowired
    public ComputerService(ComputerRepository computerRepository){ this.computerRepository = computerRepository;}

    public List<Computer> getAllComputers() {return computerRepository.findAll();}

    public Computer addComputer(AddComputerDTO addComputerDTO) throws IOException{
        validateComputer(addComputerDTO);
        Computer computer = RemoteCacheMapper.toComputer(addComputerDTO);
        return computerRepository.save(computer);
    }

    private void validateComputer(AddComputerDTO computer) {
        if(StringUtils.isEmpty(computer.getComp_name())){
            throw new IllegalArgumentException("Computer Name is required");
        }

        if(StringUtils.isEmpty(computer.getComp_details())){
            throw new IllegalArgumentException("Computer Details is required");
        }

        if(StringUtils.isEmpty(computer.getComp_add())){
            throw new IllegalArgumentException("Computer MAC Address is required");
        }
    }

    public List<Computer> getComputerIfAvailable(){
        List<Computer> listOfComputer = computerRepository.getComputerByIs_available().get();
        return listOfComputer;
    }
}
