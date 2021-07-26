package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class Game extends Entity {
    public Integer roundNo;
    public Integer team1;
    public Integer team2;
    public Integer winner;
    public String team1Name;
    public String team2Name;
    public String winnerName;
}
