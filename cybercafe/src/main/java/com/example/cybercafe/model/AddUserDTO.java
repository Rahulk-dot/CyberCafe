package com.example.cybercafe.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AddUserDTO {
    private String user_name;
    private String user_ph_number;
    private Integer assigned_comp;

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

    public Integer getAssigned_comp() {
        return assigned_comp;
    }

    public void setAssigned_comp(Integer assigned_comp) {
        this.assigned_comp = assigned_comp;
    }
}

