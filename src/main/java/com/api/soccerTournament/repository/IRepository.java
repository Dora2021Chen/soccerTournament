package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Component;

@Component
public interface IRepository {
    Response readAll();
    Response delete(Integer id);
}
