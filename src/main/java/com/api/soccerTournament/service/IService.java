package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface IService {
    Response<? extends Entity> readAll();

    Response<? extends Entity> readById(Integer id);

    Response delete(Integer id);
}
