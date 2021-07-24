package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class GameMember extends Entity {
    public Integer gameId;
    public Integer teamId;
    public Boolean isWinner;
}
