package com.example.cybercafe.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
public class Users {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String user_name;
    private String user_ph_number;

    @ManyToOne
    @JoinColumn(name = "assigned_comp")
    private Computer assigned_comp;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime checkin_time;

    private LocalDateTime checkout_time;

    public  Users(String user_name, String user_ph_number, Computer assigned_comp) {
        this.user_name = user_name;
        this.user_ph_number = user_ph_number;
        this.assigned_comp = assigned_comp;
        this.checkin_time = LocalDateTime.now();
        // Assuming checkOutTime is initially null until set later
    }

    public Users() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_ph_number() {
        return user_ph_number;
    }

    public void setUser_ph_number(String user_ph_number) {
        this.user_ph_number = user_ph_number;
    }

    public Computer getAssigned_comp() {
        return assigned_comp;
    }

    public void setAssigned_comp(Computer assigned_comp) {
        this.assigned_comp = assigned_comp;
    }

    public LocalDateTime getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(LocalDateTime checkin_time) {
        this.checkin_time = checkin_time;
    }

    public LocalDateTime getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(LocalDateTime checkout_time) {
        this.checkout_time = checkout_time;
    }

}
