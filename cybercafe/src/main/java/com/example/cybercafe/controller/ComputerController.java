package com.example.cybercafe.controller;

import com.example.cybercafe.entity.Computer;
import com.example.cybercafe.entity.Users;
import com.example.cybercafe.model.AddComputerDTO;
import com.example.cybercafe.model.AddUserDTO;
import com.example.cybercafe.model.StandardResponse;
import com.example.cybercafe.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/computer/api")
public class ComputerController {

    private final ComputerService computerService;

    @Autowired
    public ComputerController(ComputerService computerService){ this.computerService = computerService; }

    @GetMapping("/getAllComputers")
    public ResponseEntity<List<Computer>> getAllComputers(){
        List<Computer> computers = computerService.getAllComputers();
        return new ResponseEntity<>(computers, HttpStatus.OK);
    }

    @PostMapping("/addComputer")
    public ResponseEntity<StandardResponse<Computer>> addComputer(@RequestBody AddComputerDTO computerDTO) {
        try {
            Computer addedComputer = computerService.addComputer(computerDTO);
            return new ResponseEntity<>(new StandardResponse<>(addedComputer, "Computer added", HttpStatus.CREATED.value()), HttpStatus.CREATED);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponseEntity<>(new StandardResponse<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAvailableComputers")
    public ResponseEntity<StandardResponse<List<Computer>>> getAvailableComputers(){
        try{
            List<Computer> computerList = computerService.getComputerIfAvailable();
            return new ResponseEntity<>(new StandardResponse<>(computerList, "List of available computers", HttpStatus.OK.value()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new StandardResponse<>(null, e.getMessage(), HttpStatus.BAD_REQUEST.value())
                    , HttpStatus.BAD_REQUEST);
        }
    }
}
