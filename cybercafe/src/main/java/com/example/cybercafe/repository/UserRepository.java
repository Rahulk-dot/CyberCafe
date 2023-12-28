package com.example.cybercafe.repository;

import com.example.cybercafe.entity.Computer;
import com.example.cybercafe.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Modifying
    @Query("update Users u set u.assigned_comp = null where u.user_id = :user_id")
    int updateUnassignedComp(Long user_id);

    @Modifying
    @Query("update Users u set u.assigned_comp = :comp_id where u.user_id = :user_id")
    int updateAssignedComp(Computer comp_id, Long user_id);
}

