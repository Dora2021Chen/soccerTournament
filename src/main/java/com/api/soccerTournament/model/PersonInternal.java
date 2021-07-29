package com.api.soccerTournament.model;

public class PersonInternal extends Person {
    public Byte role;

    //used in DbApi
    public PersonInternal() {

    }

    public PersonInternal(Person person, Byte role) {
        this.id = person.id;
        this.name = person.name;
        this.idDocNumber = person.idDocNumber;
        this.teamId = person.teamId;
        this.role = role;
    }
}
