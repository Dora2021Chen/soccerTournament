package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.TeamMember;
import com.api.soccerTournament.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class GameRepository implements IRepository {
    @Autowired
    private DbApi dbApi;

    private static final String tableName = "game";
    private static final Class cls = Game.class;

    public Response readAll() {
        Response response = dbApi.readAll(tableName, cls);
        return response;
    }

    public Response write(Game game) {
        Optional<Game> optionalGame = Optional.of(game);
        Response response = dbApi.write(optionalGame, tableName, cls);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
