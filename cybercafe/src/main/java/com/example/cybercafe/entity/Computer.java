package com.example.cybercafe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
public class Computer {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comp_id;

    private String comp_name;
    private String comp_details;

    @Column
    private boolean is_available = true;

    private String comp_add;

    @Column
    private LocalDateTime created_at = LocalDateTime.now();


    public Computer(Long comp_id, String comp_name, String comp_details, String comp_add) {
        this.comp_id = comp_id;
        this.comp_name = comp_name;
        this.comp_details = comp_details;
        // Assuming new computers are available by default
        this.comp_add = comp_add;
        this.created_at = LocalDateTime.now();
    }

    public Computer() {

    }

    public Computer(String compName, String compDetails, String compAdd) {
        this.comp_name = compName;
        this.comp_details = compDetails;
        this.comp_add = compAdd;
    }

    public Long getComp_id() {
        return comp_id;
    }

    public void setComp_id(Long computerId) {
        this.comp_id = computerId;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String computerName) {
        this.comp_name = computerName;
    }

    public String getComp_details() {
        return comp_details;
    }

    public void setComp_details(String computerDetails) {
        this.comp_details = computerDetails;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public String getComp_add() {
        return comp_add;
    }

    public void setComp_add(String physicalAddress) {
        this.comp_add = physicalAddress;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }


}
