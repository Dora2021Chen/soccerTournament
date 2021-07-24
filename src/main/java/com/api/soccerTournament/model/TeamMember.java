package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

@Component
public class TeamMember extends Entity {
    public Integer id;
    public Integer teamId;
    public Integer personId;
}
