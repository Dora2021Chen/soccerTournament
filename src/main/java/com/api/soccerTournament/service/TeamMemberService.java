package com.api.soccerTournament.service;

import com.api.soccerTournament.model.TeamMember;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService implements IService {
    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public Response<TeamMember> readAll() {
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
