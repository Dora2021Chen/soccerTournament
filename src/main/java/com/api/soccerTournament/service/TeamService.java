package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements IService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Response<Team> readAll() {
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
