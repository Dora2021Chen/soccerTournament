package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements IService {
    @Autowired
    private TeamRepository teamRepository;

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
