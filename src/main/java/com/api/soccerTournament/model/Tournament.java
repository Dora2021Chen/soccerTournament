package com.api.soccerTournament.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Tournament extends Entity {
    public String name;
    public Integer countryId;
    public Date startDate;
    public Date endDate;
}
