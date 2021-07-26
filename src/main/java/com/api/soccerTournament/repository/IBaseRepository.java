package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

@Repository
public interface IBaseRepository {
    Response<? extends Entity> readAll();

    Response<? extends Entity> readById(Integer id);

    Response delete(Integer id);
}
