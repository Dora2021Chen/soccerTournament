package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface IRepository {
    Response readAll();
    Response delete(Integer id);
}
