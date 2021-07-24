package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TeamRepository;
import com.api.soccerTournament.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeamService implements IService {
    @Autowired
    private TeamRepository teamRepository;

    public Response readAll() {
        Response response = teamRepository.readAll();
        return response;
    }

    public Response write(Team team) {
        Response response = teamRepository.write(team);
        return response;
    }

    public Response delete(Integer id) {
        Response response = teamRepository.delete(id);
        return response;
    }
}
