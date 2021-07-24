package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface IService {
    Response readAll();

    Response delete(Integer id);
}
