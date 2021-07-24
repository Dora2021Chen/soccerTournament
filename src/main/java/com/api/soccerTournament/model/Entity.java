package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class Entity {
    public Integer id;

    public Entity() {

    }

    public Entity(int id) {
        this.id = id;
    }
}
