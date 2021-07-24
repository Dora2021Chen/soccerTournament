package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.TeamMember;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TeamMemberRepository;
import com.api.soccerTournament.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeamMemberService implements IService {
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public Response readAll() {
        Response response = teamMemberRepository.readAll();
        return response;
    }

    public Response write(TeamMember teamMember) {
        Response response = teamMemberRepository.write(teamMember);
        return response;
    }

    public Response delete(Integer id) {
        Response response = teamMemberRepository.delete(id);
        return response;
    }
}
