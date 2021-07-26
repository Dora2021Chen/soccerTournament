package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class Person extends Participant {
    public String idDocNumber;
    public Integer teamId;
    public Integer role;

    public void setRole(Integer role) {
        this.role = role;
    }
}
