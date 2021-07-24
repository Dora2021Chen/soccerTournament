package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class Game extends Entity {
    public Byte roundNo;
    public Integer team1;
    public Integer team2;
    public Integer winner;
}
