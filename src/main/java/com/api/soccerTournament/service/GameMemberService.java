package com.api.soccerTournament.service;

import com.api.soccerTournament.model.GameMember;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.GameMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameMemberService implements IService {
    @Autowired
    private GameMemberRepository gameMemberRepository;

    public Response readAll() {
        Response response =  gameMemberRepository.readAll();
        return response;
    }

    public Response write(GameMember gameMember) {
        Response response =  gameMemberRepository.write(gameMember);
        return response;
    }

    public Response delete(Integer id) {
        Response response =  gameMemberRepository.delete(id);
        return response;
    }
}
