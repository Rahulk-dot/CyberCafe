package com.example.cybercafe.repository;

import com.example.cybercafe.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    @Query("select c from Computer c where c.is_available=true")
    Optional<List<Computer>> getComputerByIs_available();

    @Modifying
    @Query("update Computer c set c.is_available = true where c.comp_id = :comp_id")
    int unassignedComputerFromUser(Long comp_id);

    @Modifying
    @Query("update Computer c set c.is_available = false where c.comp_id = :comp_id")
    int assignComputerToUser(Long comp_id);

}

