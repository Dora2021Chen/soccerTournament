package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class Participant extends Entity{
    public String name;
    public Integer countryId;
    public Integer tournamentId;
}
