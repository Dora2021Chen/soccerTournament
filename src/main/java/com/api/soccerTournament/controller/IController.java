package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Component;

@Component
public interface IController {
    Response<? extends Entity> readAll();

    Response<? extends Entity> readById(Integer id);

    Response delete(Integer id);
}
