package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.GameMember;
import com.api.soccerTournament.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameMemberRepository implements IRepository {
    @Autowired
    private DbApi dbApi;

    private static final String tableName = "game_member";
    private static final Class cls = GameMember.class;

    public Response readAll() {
        Response response = dbApi.readAll(tableName, cls);
        return response;
    }

    public Response write(GameMember gameMember) {
        Optional<GameMember> optionalGameMember = Optional.of(gameMember);
        Response response = dbApi.write(optionalGameMember, tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
