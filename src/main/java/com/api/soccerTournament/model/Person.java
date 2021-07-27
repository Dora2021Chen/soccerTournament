package com.api.soccerTournament.model;

public class Person extends Participant {
    public String idDocNumber;
    public Integer teamId;
    public Byte role;

    public void setRole(Byte role) {
        this.role = role;
    }
}
