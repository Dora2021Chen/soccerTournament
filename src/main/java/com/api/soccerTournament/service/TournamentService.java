package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Tournament;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TournamentService implements IService {
    @Autowired
    private TournamentRepository tournamentRepository;

    public Response readAll() {
        Response response = tournamentRepository.readAll();
        return response;
    }

    public Response write(Tournament tournament) {
        Response response = tournamentRepository.write(tournament);
        return response;
    }

    public Response delete(Integer id) {
        Response response = tournamentRepository.delete(id);
        return response;
    }
}

