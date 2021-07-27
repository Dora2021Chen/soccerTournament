package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TeamRepository;
import com.api.soccerTournament.utility.Utility;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements IService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Response<Team> readAll() {
        Response response = teamRepository.readAll();
        return response;
    }

    @Override
    public Response<? extends Entity> readById(Integer id) {
        Response response = teamRepository.readById(id);
        return response;
    }

    public Response write(Team team) {
        Response response = teamRepository.write(team);
        return response;
    }

    @Override
    public Response delete(Integer id) {
        Response response = teamRepository.delete(id);
        return response;
    }
}
