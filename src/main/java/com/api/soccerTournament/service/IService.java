package com.api.soccerTournament.service;

import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface IService {
    Response readAll();

    Response delete(Integer id);
}
